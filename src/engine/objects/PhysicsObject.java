package engine.objects;

import engine.components.PhysicsComponent;

public interface PhysicsObject extends CollidibleObject {
  
  public PhysicsComponent getPhysicsComponent();
}
