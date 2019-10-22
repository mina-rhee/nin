package engine.components;

import javafx.scene.canvas.GraphicsContext;

public interface Component {
  
  public String getTag();
  
  public void tick(long nanosSinceLastTick);
  
  public void draw(GraphicsContext g);
}
