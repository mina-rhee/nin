package engine.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import template.Vec2d;

public class SpriteComponent extends DrawComponent{
  
  private Image sheet;
  
  private Vec2d imgPosition;
  private Vec2d imgSize;
  private double imgScale;
  
  public SpriteComponent(Image i, Vec2d iPosition, Vec2d iSize, double iScale) {
    sheet = i;
    imgPosition = iPosition;
    imgSize = iSize;
    imgScale = iScale;
  }
  
  public void setImage(Image s) {
    sheet = s;
  }
  
  public void setImgPosition(Vec2d pos) {
    imgPosition = pos;
  }
  
  public Vec2d getImgSize() {
    return imgSize;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
  }

  @Override
  public void draw(GraphicsContext g) {
  }

  @Override
  public void drawAt(GraphicsContext g, Vec2d place, double scale) {
    g.drawImage(sheet, imgPosition.x, imgPosition.y, 
        imgSize.x, imgSize.y, place.x, place.y, 
        imgSize.x * imgScale * scale, imgSize.y * imgScale * scale);
  }
}
