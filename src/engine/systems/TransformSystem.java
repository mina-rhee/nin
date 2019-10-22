package engine.systems;

import engine.GameWorld;
import engine.objects.GameObject;

public abstract class TransformSystem<O extends GameObject> extends GameSystem<O>{
  
  public static final String TAG = "TRANSFORM";
  
  public TransformSystem(GameWorld<?> world) {
    super(world);
  }
  
  public String getTag() {
    return TAG;
  }

}
