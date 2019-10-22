package engine.systems;

import engine.GameWorld;
import engine.objects.CollidibleObject;

public abstract class CollisionSystem<E extends CollidibleObject> extends GameSystem<E>{
  
  private static final String TAG = "COLLISION";

  public CollisionSystem(GameWorld<?> world) {
    super(world);
  }
  
  @Override
  public String getTag() {
    return TAG;
  }
  
  public abstract void checkCollisions();

}
