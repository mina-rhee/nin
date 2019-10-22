package engine;

import java.util.HashMap;

import engine.objects.GameObject;
import engine.systems.GameSystem;
import engine.ui.Viewport;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public abstract class GameWorld<G extends GameObject> {
  
  private HashMap<String, GameSystem<?>> systems = new HashMap<>();
  protected Vec2d topLeft = new Vec2d(0);
  protected Viewport<?> view;
  
  public GameSystem<?> getSystem(String tag) {
    return systems.get(tag);
  }
  
  public void removeSystem(String tag) {
    systems.remove(tag);
  }
  
  public void addSystem(GameSystem<?> sys) {
    systems.put(sys.getTag(), sys);
  }
  
  public abstract void onTick(long nanos);
  
  public Vec2d getTopLeft() {
	  return topLeft;
  }
  
  public void setTopLeft(Vec2d top) {
	  topLeft = top;
  }
  
  public abstract void drawInBounds(GraphicsContext g, Vec2d topLeft2, Vec2d botRight);
  
}
