package engine.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import template.Vec2d;

/*
 * generic text box, holds text in a rectangular space
 */
public class TextBox extends UIElement {
  
  private String t = "";
  private Font f = new Font("Verdana", 20);
  
  public TextBox(Vec2d s, Vec2d ap, Vec2d rp, UIElement par) {
    super(s, ap, rp, par);
    setColor(Color.DIMGRAY);
  }
  
  public String getString() {
    return t;
  }
  
  public void setString(String txt) {
    t = txt;
  }
  
  public void setFont(Font newFont) {
    f = newFont;
  }
  
  public Font getFont() {
    return f;
  }

  @Override
  public void onTick(long nanos) {}

  @Override
  public void onDraw(GraphicsContext g) {
    g.setFont(f);
    g.setFill(color);
    g.fillText(t, absPos.x, absPos.y + size.y, size.x);
  }

  @Override
  public void onResize(Vec2d newSize) {
    setSize(newSize);
  }

}
