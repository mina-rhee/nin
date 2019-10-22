package engine.bt;

public interface BTNode {
  
  boolean update(float nanosecs);
  
  void reset();
}
