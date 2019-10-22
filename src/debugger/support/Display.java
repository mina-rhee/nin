package debugger.support;

import java.util.Calendar;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Display extends Application {
	
	private static Stage _stage = null;
	public static float getStageWidth() {
		return _stage == null ? 0 : (float) _stage.getWidth();
	}
	public static float getStageHeight() {
		return _stage == null ? 0 : (float) _stage.getHeight() - 20;
	}
	public static void setResizable(boolean b) {
		if(_stage != null) _stage.setResizable(b);
	}
	public static void setTitle(String title) {
		if(_stage != null) _stage.setTitle("Collision Debugger: " + title);
	}
	public static int getDefaultWeek() {
		return week;
	}
	
	// week 3
	private static final int month3 = 10;
	private static final int day3 = 1;
	
	// week 5
	private static final int month5 = 10;
	private static final int day5 = 22;
	
	// week 6
	private static final int month6 = 10;
	private static final int day6 = 29;

	// defined in main
	private static int week = 2;
		
	public static void main(String[] argv) {
		
		Calendar c = Calendar.getInstance();
		Date today = c.getTime();
		
		c.set(Calendar.MONTH, month3);
		c.set(Calendar.DAY_OF_MONTH, day3);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 30);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date week3 = c.getTime();

		c.set(Calendar.MONTH, month5);
		c.set(Calendar.DAY_OF_MONTH, day5);
		Date week5 = c.getTime();
		
		c.set(Calendar.MONTH, month6);
		c.set(Calendar.DAY_OF_MONTH, day6);
		Date week6 = c.getTime();
		
		if(week6.before(today)) {
			week = 6;
		} else if(week5.before(today)) {
			week = 5;
		} else if(week3.before(today)) {
			week = 3;
		} else {
			week = 2;
		}
		
		week = 3;
		
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		_stage = stage;
		
		String title = "Collision Debugger: Week " + Integer.toString(week);
		stage.setTitle(title);
		
		
		stage.setWidth(UIConstants.STAGE_WIDTH);
		stage.setHeight(UIConstants.STAGE_HEIGHT);
		Pane pane = new Pane();
		Scene scene = new Scene(pane, UIConstants.STAGE_WIDTH, UIConstants.STAGE_HEIGHT);
		stage.setScene(scene);
				
		CollisionCanvas canvas = new CollisionCanvas();	
		scene.setOnKeyPressed(e -> {
			canvas.onKeyPressed(e);
		});
		pane.getChildren().add(canvas.getRoot());
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			canvas.rebind();
		});
		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			canvas.rebind();
		});
		
		stage.show();
		
	}

}
