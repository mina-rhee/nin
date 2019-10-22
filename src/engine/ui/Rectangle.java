package engine.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

/**
 * Generic Rectangle, just draws itself on draw
 */
public class Rectangle extends UIElement {

  public Rectangle(Vec2d s, Vec2d ap, Vec2d rp, UIElement p) {
    super(s, ap, rp, p);
    setColor(Color.DARKSALMON);
  }

  @Override
  public void onTick(long nanos) {}

  @Override
  public void onDraw(GraphicsContext g) {
    g.setFill(color);
    g.fillRect(absPos.x, absPos.y, size.x, size.y);
  }

  @Override
  public void onResize(Vec2d newSize) {
    setSize(newSize);
  }

}
