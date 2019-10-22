package engine.components;

import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class TransformComponent implements Component {
	
	static final String TAG = "transform";
	
	protected Vec2d coord;
	
	public TransformComponent(Vec2d coordinate) {
		coord = coordinate;
	}
	
	public Vec2d getCoord() {
		return coord;
	}
	
	public void setCoord(Vec2d c) {
		coord = c;
	}
	
	public void incrementCoord(Vec2d c) {
		coord = coord.plus(c);
	}
	
	public String getTag() {
		return TAG;
	}

  @Override
  public void tick(long nanosSinceLastTick) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void draw(GraphicsContext g) {
  }
}
