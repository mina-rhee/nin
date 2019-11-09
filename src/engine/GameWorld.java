package engine;

import java.util.HashMap;
import java.util.Set;

import engine.objects.GameObject;
import engine.systems.GameSystem;
import engine.ui.Viewport;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import template.Vec2d;

public abstract class GameWorld<G extends GameObject> {
  
  private HashMap<String, GameSystem<?>> systems = new HashMap<>();
  protected Vec2d topLeft = new Vec2d(0);
  protected Viewport<?> view;
  private HashMap<KeyCode, Boolean> keyPressed = new HashMap<>();
  
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
  
  protected void initializeKeyMap(Set<KeyCode> codes) {
    keyPressed = new HashMap<>();
    for(KeyCode code : codes) {
      keyPressed.put(code, false);
    }
  }
  
  public void onKeyPressed(KeyEvent e) {
    if(keyPressed.containsKey(e.getCode())) {
      keyPressed.put(e.getCode(), true);
    }
  }
  
  public void onKeyReleased(KeyEvent e) {
    if(keyPressed.containsKey(e.getCode())) {
      keyPressed.put(e.getCode(), false);
    }
  }
  
  public boolean isKeyDown(KeyCode code) {
    if(keyPressed.containsKey(code)) {
      return keyPressed.get(code);
    } else {
      return false;
    }
  }
  
  public abstract void drawInBounds(GraphicsContext g, Vec2d topLeft2, Vec2d botRight);
  
}
