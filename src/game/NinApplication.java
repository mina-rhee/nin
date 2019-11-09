package game;

import engine.Application;
import game.world.NinWorld;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import template.Vec2d;

public class NinApplication extends Application {

  public NinApplication() {
    super("ninja");
  }

  @Override
  protected void onShutdown() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void onStartup() {
    NinViewport view = new NinViewport(new Vec2d(0), new Vec2d(0), currentStageSize, 1);
    NinWorld world = new NinWorld(view);
    view.setGameworld(world);
    GameScreen game = new GameScreen(currentStageSize, world, view);
    addScreen(0, game);
    changeFocus(0);
  }
  
  @Override
  protected void onKeyPressed(KeyEvent e) {
    if(e.getCode() == KeyCode.SPACE) {
      onStartup();
    } else {
      focusScreen.onKeyPressed(e);
    }
  }

}
