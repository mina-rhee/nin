package engine.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import template.FontMetrics;
import template.Vec2d;

/*
 * Each UIElement has a parent, list of children
 * and a relative and absolute position.
 * Default behavior is no response on events
 * onResize, onDraw, and onTick are left abstract
 */
public abstract class UIElement {

  private UIElement parent;
  protected List<UIElement> children = new ArrayList<>();

  protected Vec2d relPos; // relative to parenrt
  protected Vec2d absPos; // relative to the screen
  protected Vec2d size;
  protected Color color;

  public UIElement(Vec2d s, Vec2d ap, Vec2d rp, UIElement par) {
    relPos = rp;
    absPos = ap;
    size = s;
    parent = par;
  }
  
  public UIElement(Vec2d s, Vec2d ap, Vec2d rp) {
    relPos = rp;
    absPos = ap;
    size = s;
    parent = null;
  }

  public void addChild(UIElement e) {
    children.add(e);
  }

  public void addChild(int i, UIElement e) {
    children.add(i, e);
  }
  
  public UIElement getChild(int i) {
    return children.get(i);
  }
  
  public List<UIElement> getChildren() {
    return children;
  }

  public Vec2d getRelPosition() {
    return relPos;
  }

  public Vec2d getAbsPosition() {
    return absPos;
  }

  public void setSize(Vec2d newSize) {
    size = newSize;
  }
  
  public Vec2d getSize() {
    return size;
  }
  
  public void setRelPosition(Vec2d newPos) {
    relPos = newPos;
    absPos = getParent().getAbsPosition().plus(newPos);
  }
  
  public void setAbsPosition(Vec2d newPos) {
	  absPos = newPos;
  }

  public void setColor(Color c) {
    color = c;
  }
  
  public Color getColor() {
    return color;
  }

  // checks if a coordinate is in the space of the element
  public boolean inElement(Vec2d coord) {
    return coord.x >= absPos.x && coord.x <= absPos.x + size.x
        && coord.y >= absPos.y && coord.y <= absPos.y + size.y;
  }

  public abstract void onTick(long nanos);

  public abstract void onDraw(GraphicsContext g);
  
  public abstract void onResize(Vec2d newSize);

  protected void onKeyTyped(KeyEvent e) {}

  protected void onKeyPressed(KeyEvent e) {}

  protected void onKeyReleased(KeyEvent e) {}
  
  public void onMouseClicked(MouseEvent e) {}

  public void onMousePressed(MouseEvent e) {}

  public void onMouseReleased(MouseEvent e) {}
  
  public void onMouseDragged(MouseEvent e) {}

  public void onMouseMoved(MouseEvent e) {}

  public void onMouseWheelMoved(ScrollEvent e) {}

  protected void onFocusChanged(boolean newVal) {}

  public UIElement getParent() {
    return parent;
  }

  public void setParent(UIElement parent) {
    this.parent = parent;
  }
  
  public static Vec2d centerText(String text, Font f, Vec2d tl, Vec2d s) {
    FontMetrics fm = new FontMetrics(text, f);
    Vec2d textSize = new Vec2d(fm.width, fm.height);
    return tl.plus(s.minus(textSize).smult(.5));
  }

}
