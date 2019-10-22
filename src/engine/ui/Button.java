package engine.ui;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import template.Vec2d;

/*
 * Generic Button, can have text, keeps track of 
 * if it is hovered, but as implemented does not do anything on hover,
 * but hoverDraw can be overridden.
 * has an event handler field that is called when the button is clicked on.
 */
public class Button extends UIElement {
  
  public Button(Vec2d s, Vec2d ap, Vec2d rp, UIElement p) {
    super(s, ap, rp, p);
    setColor(Color.DARKSEAGREEN);
  }

  protected EventHandler<MouseEvent> clickHandler;
  public boolean hovered = false;
  private String buttonText;
  private Font f;
  private Color textColor;

  @Override
  public void onMouseClicked(MouseEvent e) {
    Vec2d clickPos = new Vec2d(e.getSceneX(), e.getSceneY());
    if (inElement(clickPos))
      clickHandler.handle(e);
  }

  public void setClickHandler(EventHandler<MouseEvent> e) {
    clickHandler = e;
  }
  
  @Override
  public void onMouseMoved(MouseEvent e) {
    Vec2d mousePos = new Vec2d(e.getSceneX(), e.getSceneY());
    hovered = inElement(mousePos);
  }

  public void setText(String s) {
    buttonText = s;
  }

  public String getText() {
    return buttonText;
  }
  
  public void setFont(Font font) {
    f = font;
  }
  
  public void setTextColor(Color c) {
    textColor = c;
  }

  @Override
  public void onTick(long nanos) {}

  @Override
  public void onDraw(GraphicsContext g) {
    g.setFill(color);
    g.fillRoundRect(absPos.x, absPos.y, size.x, size.y, 5, 5);
    if(textColor == null)
      g.setFill(color.invert()); 
    else
      g.setFill(textColor);
    
    if(f != null) {
      g.setFont(f);
    }
    
    Vec2d textPos = UIElement.centerText(buttonText, f, absPos, size);
    g.fillText(buttonText, textPos.x, textPos.y + size.y * .4);
    if(hovered)
      hoverDraw(g);
  }
  
  public void hoverDraw(GraphicsContext g) {
    g.setStroke(Color.WHITE);
    g.strokeRoundRect(absPos.x, absPos.y, size.x, size.y, 5, 5);
  }

  @Override
  public void onResize(Vec2d newSize) {
    setSize(newSize);
  }
}
