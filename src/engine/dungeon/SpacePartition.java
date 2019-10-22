package engine.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.dungeon.DungeonSpace.DIR;
import template.Vec2d;
import template.Vec2i;

public class SpacePartition {
  
  private static Random r; 
 
  //given, overlap < minDepth/2, hallWidth < overlap
  
  public static Dungeon createRandomDungeon(Vec2i size, int minDepth, int overlap,
      int hallWidth, long seed) {
    assert(overlap < (minDepth + 1)/2);
    assert(hallWidth < overlap);
    r = new Random(seed);
    Dungeon d = new Dungeon(new Vec2i(0,0), size, false);
    partition(d, minDepth, 0);
    roomFill(d, overlap);
    connect(d, hallWidth, minDepth);
    //ArrayList<Room> rooms = getRooms(d);
    return d;
  }

  private static void partition(Dungeon d, int minDepth, int dDepth) {
    if(dDepth > 2 && r.nextDouble() > .7) {
      return;
    } else {
      Vec2i size = d.getSize();
      Vec2i tl = d.getTopLeft();
      boolean dir = d.getSplit();
      if(dir) {
        if(size.y < minDepth * 2) {
          return;
        } else {
          int split = (int) (r.nextDouble() * (size.y - minDepth * 2) + minDepth);
          d.setLeft(new Dungeon(tl, new Vec2i(size.x, split), d, !dir));
          d.setRight(new Dungeon(new Vec2i(tl.x, tl.y + split), 
              new Vec2i(size.x, size.y - split), d, !dir));
          partition(d.getLeft(), minDepth, dDepth + 1);
          partition(d.getRight(), minDepth, dDepth + 1);
        }
      } else {
        if(size.x < minDepth * 2) {
          return;
        } else {
          int split = (int) (r.nextDouble() * (size.x - minDepth * 2) + minDepth);
          d.setLeft(new Dungeon(tl, new Vec2i(split, size.y), d, !dir));
          d.setRight(new Dungeon(new Vec2i(tl.x + split, tl.y), 
              new Vec2i(size.x - split, size.y), d, !dir));
          partition(d.getLeft(), minDepth, dDepth + 1);
          partition(d.getRight(), minDepth, dDepth + 1);
        }
      }
    }
  }
  
  private static void roomFill(Dungeon d, int overlap) {
    if(d.getLeft() == null) {
      Vec2i s = d.getSize();
      Vec2i tl = d.getTopLeft();
   
      //choose side length and location of the room
      //x 
      int xMin = (s.x + 1)/2 + overlap;
      int xSide = (int) (r.nextDouble() * (s.x - 2 - xMin) + xMin);
      int tlx = (int) ((r.nextDouble() * (s.x - 2 - xSide)) + tl.x + 1);
      
      //y
      int yMin = (s.y + 1)/2 + overlap;
      int ySide = (int) (r.nextDouble() * (s.y - 2 - yMin) + yMin);
      int tly = (int) ((r.nextDouble() * (s.y - 2 - ySide)) + tl.y + 1);
      
      d.setSpace(new Room(new Vec2f(tlx, tly), 
          new Vec2f(xSide, ySide)));
    } else {
      roomFill(d.getLeft(), overlap);
      roomFill(d.getRight(), overlap);
    }
  }
  
  private static void connect(Dungeon d, int width, int minDepth) {
    if(d.getLeft() != null) {
      ArrayList<Dungeon> lDungeons = getRoomDungeons(d.getLeft());
      ArrayList<Dungeon> rDungeons = getRoomDungeons(d.getRight());
      
      if(d.getSplit()) {
        
        //filter out rooms to only those on the edge of the split
        int splitLine = d.getRight().getTopLeft().y;
        lDungeons.removeIf(r -> r.getTopLeft().y + r.getSize().y != splitLine);
        rDungeons.removeIf(r -> r.getTopLeft().y != splitLine);
        
        ArrayList<Room> lRooms = new ArrayList<>();
        ArrayList<Room> rRooms = new ArrayList<>();
        for(Dungeon dun : lDungeons) {
          lRooms.add((Room) dun.getSpace());
        }
        for(Dungeon dun : rDungeons) {
          rRooms.add((Room) dun.getSpace());
        }
        
        Hallway h = null;
        //start searching for a place to put a hallway,
        //starting from the middle
        Vec2i bound = new Vec2i(d.getTopLeft().x, d.getSize().x + d.getTopLeft().x);
    
        searchloop:
        for(int a = 0; a < bound.y - width - bound.x; a++) {
          int i = a + (bound.y + bound.x)/2;
          if(i >= bound.y - width) {
            i = i - bound.y + width + bound.x;
          }
          for(Room lRoom : lRooms) {
            int ltl = (int) lRoom.getShape().getTopLeft().x;
            if(ltl <= i && ltl + lRoom.getShape().getSize().x >= i + width) {
              for(Room rRoom : rRooms) {
                int rtl = (int) rRoom.getShape().getTopLeft().x;
                if(rtl <= i && rtl + rRoom.getShape().getSize().x >= i + width) {
                  Vec2i tl = new Vec2i(i, (int) (lRoom.getShape().getTopLeft().y + 
                      lRoom.getShape().getSize().y));
                  Vec2i size = new Vec2i(width, 
                      (int) rRoom.getShape().getTopLeft().y - tl.y);
                  h = new Hallway(tl, size, lRoom, rRoom, false);
                  lRoom.addHallway(h);
                  rRoom.addHallway(h);
                  break searchloop;
                }
              }
            }
          }
        }
        assert(h != null);
        if(h == null) {
          System.out.println("bad");
        }
        d.setSpace(h);
      } else {
      //filter out rooms to only those on the edge of the split
        int splitLine = d.getRight().getTopLeft().x;
        
        lDungeons.removeIf(r -> r.getTopLeft().x + r.getSize().x != splitLine);
        rDungeons.removeIf(r -> r.getTopLeft().x != splitLine);
        
        ArrayList<Room> lRooms = new ArrayList<>();
        ArrayList<Room> rRooms = new ArrayList<>();
        for(Dungeon dun : lDungeons) {
          lRooms.add((Room) dun.getSpace());
        }
        for(Dungeon dun : rDungeons) {
          rRooms.add((Room) dun.getSpace());
        }
        
        Hallway h = null;
        //start searching for a place to put a hallway,
        //starting from the middle
        Vec2i bound = new Vec2i(d.getTopLeft().y, d.getSize().y + d.getTopLeft().y);
        
        searchloop:
          for(int a = 0; a < bound.y - width - bound.x; a++) {
          int i = a + (bound.y + bound.x)/2;
          if(i >= bound.y - width) {
            i = i - bound.y + width + bound.x;
          }
          for(Room lRoom : lRooms) {
            int ltl = (int) lRoom.getShape().getTopLeft().y;
            if(ltl <= i && ltl + lRoom.getShape().getSize().y >= i + width) {
              for(Room rRoom : rRooms) {
                int rtl = (int) rRoom.getShape().getTopLeft().y;
                if(rtl <= i && rtl + rRoom.getShape().getSize().y >= i + width) {
                  Vec2i tl = new Vec2i((int) (lRoom.getShape().getTopLeft().x + 
                      lRoom.getShape().getSize().x), i);
                  Vec2i size = new Vec2i((int) rRoom.getShape().getTopLeft().x 
                      - tl.x, width);
                  h = new Hallway(tl, size, lRoom, rRoom, true);
                  lRoom.addHallway(h);
                  rRoom.addHallway(h);
                  break searchloop;
                }
              }
            }
          }
        }
        assert(h != null);
        if(h == null) {
          System.out.println("bad");
        }
        d.setSpace(h);
      }
      connect(d.getLeft(), width, minDepth);
      connect(d.getRight(), width, minDepth);
    }
  }
  
  private static ArrayList<Dungeon> getRoomDungeons(Dungeon d) {
    if(d.getLeft() == null) {
      ArrayList<Dungeon> r = new ArrayList<>();
      r.add(d);
      return r;
    } else {
      ArrayList<Dungeon> left = getRoomDungeons(d.getLeft());
      ArrayList<Dungeon> right = getRoomDungeons(d.getRight());
      left.addAll(right);
      return left;
    }
  }
  
  public static void fillArray(Dungeon d, int[][] grid) {
    AABShape s = d.getSpace().getShape();
    for(int x = (int) s.getMinX(); x < s.getMaxX(); x++) {
      for(int y = (int) s.getMinY(); y < s.getMaxY(); y++) {
        grid[x][y] = 1;
      }
    }
    if(d.getLeft() != null) {
      fillArray(d.getLeft(), grid);
      fillArray(d.getRight(), grid);
    }
  }
  
  public static Vec2d getSpawnPos(Dungeon d) {
    if(d.getLeft() == null) {
      AABShape b = d.getSpace().getShape();
      Vec2d tl = b.getTopLeft().toVec2d();
      Vec2d s = b.getSize().toVec2d();
      return tl.plus(s.smult(.1));
    } else {
      return getSpawnPos(d.getLeft());
    }
  }
  
  public static Vec2d getDoorPos(Dungeon d) {
    if(d.getRight() == null) {
      AABShape b = d.getSpace().getShape();
      Vec2d tl = b.getTopLeft().toVec2d();
      Vec2d s = b.getSize().toVec2d();;
      return tl.plus(s.smult(.1));
    } else {
      if(r.nextBoolean()) {
        return getDoorPos(d.getRight());
      } else {
        return getDoorPos(d.getLeft());
      }
    }
  }
  
  public static HashMap<String, Node> getGraph(int[][] d) {
    HashMap<String, Node> graph = new HashMap<>();
    
    for(int x = 0; x < d.length; x++) {
      for(int y = 0; y < d[0].length; y++) {
        if(d[x][y] == 1) {
          graph.put(x + " " + y, new Node(new Vec2d(x, y)));
        }
      }
    }
    
    for(int x = 0; x < d.length; x++) {
      for(int y = 0; y < d[0].length; y++) {
        if(d[x][y] == 1) {
          Node n = graph.get(x + " " + y);
          for(int a = -1; a < 2; a++) {
            for(int b = -1; b < 2; b++) {
              if(!(a == 0 && b == 0) ) {
                int nx = x + a;
                int ny = y + b;
                Node neigh = graph.get(nx + " " + ny);
                if(neigh != null) {
                  n.addNeighbor(neigh);
                }
              }
            }
          }
        }
      }
    }
    return graph;
  }
  
  public static DIR aStar(HashMap<String, Node> graph, Node start, Node end) {
    if(start == end) {
      return null;
    }
    APath current;
    
    Set<Node> visited = new TreeSet<>((n1, n2) -> 
    n1.pos.toString().compareTo(n2.pos.toString()));
    PriorityQueue<APath> q = new PriorityQueue<>((p1, p2) -> 
    Double.compare(p1.getDist(), p2.getDist()));
    
    current = new APath().addPath(start, 0, end);
    
    while(current != null) {
      if(current.getLast() == end) {
        return getDirection(start, current.path.get(1));
      }
      //add the neighbors of the current node to the queue
      visited.add(current.getLast());
      for(Node n : current.getLast().getNeighbors()) {
        double d = end.pos.dist2(n.pos);
        APath newPath = current.addPath(n, d, end);
        q.add(newPath);
      }
      current = nextPath(visited, q);
    }
    return null;
  }
  
//finds next path to take based on returning first element of priority queue
 // that is contained in the unvisited set. if there is no such path, returns
 // null.
 private static APath nextPath(Set<Node> visited,PriorityQueue<APath> paths) {
   APath nextPath = null;
   while (paths.peek() != null) {
     APath qHead = paths.poll();
     if (!visited.contains(qHead.getLast())) {
       nextPath = qHead;
       break;
     }
   }
   return nextPath;
 }
 
 private static DIR getDirection(Node n1, Node n2) {
   if(n1.pos.x > n2.pos.x) {
     return DIR.LEFT;
   } else if (n1.pos.x < n2.pos.x) {
     return DIR.RIGHT;
   } else if (n1.pos.y > n2.pos.y) {
     return DIR.UP;
   } else {
     return DIR.DOWN;
   }
 }
  
}
