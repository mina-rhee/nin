package engine.bt;

public class Selector extends Composite {

  @Override
  public boolean update(float nanosecs) {
    for(BTNode child: getChildren()) {
      if(child.update(nanosecs)) {
        
      }
    }
    return false;
  }

  @Override
  public void reset() {
  }

}
