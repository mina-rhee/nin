package engine.dungeon;

import template.Vec2i;

public class Door {
  
  private boolean xAxis;
  private int axisPos;
  private Vec2i bound;
  
  public Door(boolean x, int pos, Vec2i b) {
    assert(b.x < b.y);
    xAxis = x;
    axisPos = pos;
    bound = b;
  }
  
  public boolean getXAxis() {
    return xAxis;
  }
  
  public int getAxisPos() {
    return axisPos;
  }
  
  public Vec2i getBound() {
    return bound;
  }
  
  public int getMin() {
    return bound.x;
  }
  
  public int getMax() {
    return bound.y;
  }
}
