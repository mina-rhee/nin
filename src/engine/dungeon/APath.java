package engine.dungeon;

import java.util.ArrayList;

public class APath {
  
  public ArrayList<Node> path;
  public double hDist;
  
  public APath() {
    path = new ArrayList<>();
  }
  
  public double getDist() {
    return hDist + path.size() * path.size();
  }
  
  public APath addPath(Node n, double d, Node goal) {
    APath newPath = new APath();
    
    for(Node node: path) {
      newPath.path.add(node);
    }
    newPath.path.add(n);
    newPath.hDist = n.pos.dist2(goal.pos);
    
    return newPath;
  }
  
  public Node getLast() {
    return path.get(path.size() - 1);
  }
  
}
