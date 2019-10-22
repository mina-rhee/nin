package engine.ui;

import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public abstract class Viewport<G extends GameWorld<?>> extends UIElement {
  
  protected G gameworld;
  protected Vec2d topLeft;
  //SCALE * SCREEN UNIT = 1 GAME UNIT
  protected double scale;
  
  
  public Viewport(Vec2d position, Vec2d top, Vec2d size, double s) {
	  super(size, position, position, null);
	  topLeft = top;
	  scale = s;
  }
  
  public G getGameworld() {
    return gameworld;
  }


  public void setGameworld(G world) {
    gameworld = world;
  }

  public Viewport(Vec2d s, Vec2d ap, Vec2d rp, G g) {
    super(s, ap, rp);
    gameworld = g;
  }
  
  public void setBounds(Vec2d top, double s) {
    topLeft = top;
    scale = s;
  }
  
  public Vec2d getTopLeft() {
    return topLeft;
  }  
  
  public void setTopLeft(Vec2d v) {
    topLeft = v;
  }
  
  public double getScale() {
    return scale;
  }

  public abstract void onTick(long nanos);

  public void onDraw(GraphicsContext g) {
	  gameworld.drawInBounds(g, topLeft, topLeft.plus(size.pmult(new Vec2d(scale))));
  }
  
  public abstract void onResize(Vec2d newSize);
  
  public Vec2d screenToGame(Vec2d p) {
	  return topLeft.plus(p.smult(scale));
  }
  
  public Vec2d gameToScreen(Vec2d p) {
    return p.minus(topLeft).sdiv((float) scale);
  }
  
  public boolean inView(Vec2d position) {
    return position.x >= topLeft.x && position.x <= topLeft.x + (size.x * scale)
        && position.y >= topLeft.y && position.y <= topLeft.y + (size.y * scale);
  }
  
  public void zoom(double factor, Vec2d pos) {
    Vec2d gamePos = screenToGame(pos);
    scale *= factor;
    topLeft = gamePos.minus(pos.smult(scale));
  }

}
