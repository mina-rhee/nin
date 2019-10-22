package engine.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

/* on default, container draws a beige rectangle and 
 * then draws all of its children, does nothing on tick
 * and only resizes itself onResize
 */
public class Container extends UIElement {

  public Container(Vec2d s, Vec2d ap, Vec2d rp, UIElement p) {
    super(s, ap, rp, p);
    setColor(Color.FLORALWHITE);
  }

  @Override
  public void onTick(long nanos) {}

  @Override
  public void onDraw(GraphicsContext g) {
    g.setFill(color);
    g.fillRect(absPos.x, absPos.y, size.x, size.y);
    for(UIElement c : children) {
      c.onDraw(g);
    }
  }

  @Override
  public void onResize(Vec2d newSize) {
    setSize(newSize);
  }

}
