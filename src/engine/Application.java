package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import template.FXFrontEnd;
import template.Vec2d;

/*
 * Generic Application, has a list of screens
 * that can switch focus, only draws, does ticks for,
 * etc for the focus screen
 */
public abstract class Application extends FXFrontEnd {

  private List<Screen> screens = new ArrayList<>();
  private Screen focusScreen;

  public Application(String title) {
    super(title);
  }

  protected void addScreen(Screen s) {
    screens.add(s);
  }

  protected void addScreen(int i, Screen s) {
    screens.add(i, s);
  }

  protected void changeFocus(int screenIndex) {
    if (screenIndex >= 0 && screenIndex < screens.size()) 
      focusScreen = screens.get(screenIndex);
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    focusScreen.onDraw(g);
  }

  @Override
  protected void onTick(long nanos) {
    focusScreen.onTick(nanos);
  }

  @Override
  protected void onResize(Vec2d newSize) {
    focusScreen.onResize(newSize);
  }

  @Override
  protected void onKeyTyped(KeyEvent e) {
    focusScreen.onKeyTyped(e);
  }

  @Override
  protected void onKeyPressed(KeyEvent e) {
    focusScreen.onKeyPressed(e);
  }

  @Override
  protected void onKeyReleased(KeyEvent e) {
    focusScreen.onKeyReleased(e);
  }

  @Override
  protected void onMouseClicked(MouseEvent e) {
    focusScreen.onMouseClicked(e);
  }

  @Override
  protected void onMousePressed(MouseEvent e) {
    focusScreen.onMousePressed(e);
  }

  @Override
  protected void onMouseReleased(MouseEvent e) {
    focusScreen.onMouseReleased(e);
  }

  @Override
  protected void onMouseDragged(MouseEvent e) {
    focusScreen.onMouseDragged(e);
  }

  @Override
  protected void onMouseMoved(MouseEvent e) {
    focusScreen.onMouseMoved(e);
  }

  @Override
  protected void onMouseWheelMoved(ScrollEvent e) {
    focusScreen.onMouseWheelMoved(e);
  }

  @Override
  protected void onFocusChanged(boolean newVal) {
    focusScreen.onFocusChanged(newVal);
  }

}
