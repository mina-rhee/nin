package engine.components;

import javafx.scene.image.Image;
import template.Vec2d;

public abstract class AnimatedSprite extends SpriteComponent {
  
  private static final double DEFAULTRATE = 1000000000.0 / 12.00;
  
  //number of nanoseconds for each frame change
  private double animationRate = DEFAULTRATE;
  private int frameNum;
  private long sinceLastChange = 0;
  private int curFrame = 0;

  public AnimatedSprite(Image i, Vec2d iPosition, Vec2d iSize, int fNum, double iScale) {
    super(i, iPosition, iSize, iScale);
    frameNum = fNum;
  }

  public double getAnimationRate() {
    return animationRate;
  }
  
  public int getCurFrame() {
    return curFrame;
  }

  public void setAnimationRate(double rate) {
    animationRate = rate;
  }
  
  @Override
  public void tick(long sinceLastTick) {
    sinceLastChange += sinceLastTick;
    if(sinceLastChange > animationRate) {
      sinceLastChange = 0;
      curFrame = (curFrame + 1) % frameNum;
      incrementPosition();
    }
  }
  
  public abstract void incrementPosition();

}
