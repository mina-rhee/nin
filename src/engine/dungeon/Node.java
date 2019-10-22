package engine.dungeon;

import java.util.ArrayList;

import template.Vec2d;

public class Node {
  
  ArrayList<Node> neighbors;
  
  Vec2d pos;
  
  public Node(Vec2d p) {
    pos = p;
    neighbors = new ArrayList<>();
  }
  
  public void addNeighbor(Node n) {
    neighbors.add(n);
  }
  
  public  ArrayList<Node> getNeighbors() {
    return neighbors;
  }
  
}
