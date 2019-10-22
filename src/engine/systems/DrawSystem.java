package engine.systems;

import engine.GameWorld;
import engine.objects.DrawableObject;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public abstract class DrawSystem<E extends DrawableObject> extends GameSystem<E>{
  
  private static final String TAG = "DRAW";

  public DrawSystem(GameWorld<?> world) {
    super(world);
  }
  
  @Override
  public String getTag() {
    return TAG;
  }
  
  public abstract void drawInBounds(GraphicsContext g, Vec2d topLeft, Vec2d botRight);

}
