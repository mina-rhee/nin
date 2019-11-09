package game.object;

import engine.components.AnimatedSprite;
import javafx.scene.image.Image;
import template.Vec2d;

public class BallSprite extends AnimatedSprite {

  public BallSprite(Image i, Vec2d iPosition, Vec2d iSize, int fNum,
      double iScale) {
    super(i, iPosition, iSize, fNum, iScale);
  }

  @Override
  public void incrementPosition() {
    setImgPosition(new Vec2d(getImgSize().x * getCurFrame(), 0));
  }

}
