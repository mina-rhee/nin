package engine.bt;

import java.util.ArrayList;

public abstract class Composite implements BTNode {
  
  private ArrayList<BTNode> children;
  private BTNode lastRunning;
  
  public Composite() {
    children = new ArrayList<>();
  }
  
  public void addChild(BTNode c) {
    children.add(c);
  }
  
  public ArrayList<BTNode> getChildren() {
    return children;
  }

  public abstract boolean update(float nanosecs);

  public abstract void reset();

  public BTNode getLastRunning() {
    return lastRunning;
  }

  public void setLastRunning(BTNode n) {
    lastRunning = n;
  }

}
