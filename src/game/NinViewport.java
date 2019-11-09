package game;

import engine.ui.Viewport;
import game.world.NinWorld;
import template.Vec2d;

public class NinViewport extends Viewport<NinWorld> {

  public NinViewport(Vec2d position, Vec2d top, Vec2d size, double s) {
    super(position, top, size, s);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onTick(long nanos) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onResize(Vec2d newSize) {
    // TODO Auto-generated method stub
    
  }

}
