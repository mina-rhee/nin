package engine.objects;

import java.util.HashMap;

import engine.components.Component;
import engine.components.TransformComponent;

public abstract class GameObject {
  
  private HashMap<String, Component> components = new HashMap<>();
  
  public void addComponent(Component c) {
    components.put(c.getTag(), c);
  }
  
  public void removeComponent(Component c) {
    components.remove(c.getTag());
  }
  
  public Component getComponent(String tag) {
    return components.get(tag);
  }
  
  public abstract TransformComponent getTransformComponent();
}
