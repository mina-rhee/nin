package engine.components;

import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public abstract class DrawComponent implements Component {
	
	static final String TAG = "draw";
	
	
	public String getTag() {
		return TAG;
	}
	
	public abstract void drawAt(GraphicsContext g, Vec2d place, double scale);
}
