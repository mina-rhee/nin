package game.world;

import java.util.Set;

import debugger.collisions.AABShape;
import engine.ShapeUtil;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.systems.DrawSystem;
import game.NinViewport;
import game.object.NinObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

public class NinDraw extends DrawSystem<NinObject> {
  
  NinViewport view;

  public NinDraw(NinWorld world, NinViewport viewport) {
    super(world);
    addLayer(0);
    view = viewport;
  }

  @Override
  public void drawInBounds(GraphicsContext g, Vec2d topLeft, Vec2d botRight) {
    drawBackground(g, topLeft, botRight);
    Set<NinObject> stuff = getLayer(0);
    AABShape box = new AABShape(topLeft.toVec2f(), 
        botRight.minus(topLeft).toVec2f());
    for(NinObject obj : stuff) {
      CollisionComponent objcc = obj.getCollisionComponent();
      if(ShapeUtil.isColliding(box, objcc.getShape())) {
        DrawComponent objdraw = obj.getDrawComponent();
        Vec2d pos = obj.getCollisionComponent().getCoord();
        objdraw.drawAt(g, view.gameToScreen(pos), 1);
      }
    }
  }
  
  private void drawBackground(GraphicsContext g,Vec2d topLeft,Vec2d botRight) {
    g.setFill(Color.BLACK);
    g.fillRect(topLeft.x, topLeft.y, botRight.x, botRight.y);
    g.drawImage(NinWorld.MOONSURFACE, 0, 35);
  }

  @Override
  public void onDraw(GraphicsContext g) {
  }

  @Override
  public void onTick(long nanos) {
    Set<NinObject> stuff = getLayer(0);
    for(NinObject obj: stuff) {
      obj.getDrawComponent().tick(nanos);
    }
  }

}
