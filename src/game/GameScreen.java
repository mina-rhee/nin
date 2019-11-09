package game;

import engine.Screen;
import game.world.NinWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import template.Vec2d;

public class GameScreen extends Screen {
  
  private NinWorld world;
  private NinViewport view;

  public GameScreen(Vec2d s, NinWorld w, NinViewport v) {
    super(s);
    world = w;
    view = v;
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    view.onDraw(g);
  }

  @Override
  protected void onResize(Vec2d newSize) {
    view.onResize(newSize);
  }
  
  @Override
  protected void onTick(long nanos) {
    world.onTick(nanos);
  }
  
  @Override
  public void onKeyPressed(KeyEvent e) {
    world.onKeyPressed(e);
  }
  
  @Override
  protected void onKeyReleased(KeyEvent e) {
    world.onKeyReleased(e);
  }

}
