package engine;

import engine.ui.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import template.Vec2d;

/*
 * a screen. holds a container 
 * which should encompass the entire space of the screen
 */
public abstract class Screen {

  protected Container c;
  protected Vec2d size;

  public Screen(Vec2d s) {
    size = s;
  }

  protected void setContainer(Container con) {
    c = con;
  }

  protected Container getContainer() {
    return c;
  }

  protected abstract void onDraw(GraphicsContext g);

  protected abstract void onResize(Vec2d newSize);

  protected void onTick(long timeElapsed) {}

  protected void onKeyTyped(KeyEvent e) {}

  protected void onKeyPressed(KeyEvent e) {}

  protected void onKeyReleased(KeyEvent e) {}

  protected void onMouseClicked(MouseEvent e) {}

  protected void onMousePressed(MouseEvent e) {}

  protected void onMouseReleased(MouseEvent e) {}

  protected void onMouseDragged(MouseEvent e) {}

  protected void onMouseMoved(MouseEvent e) {}

  protected void onMouseWheelMoved(ScrollEvent e) {}

  protected void onFocusChanged(boolean newVal) {}

}
