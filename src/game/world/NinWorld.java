package game.world;

import java.util.Set;
import java.util.TreeSet;

import debugger.collisions.PolygonShape;
import debugger.support.Vec2f;
import engine.GameWorld;
import game.NinViewport;
import game.object.BallObject;
import game.object.GameWall;
import game.object.NinObject;
import game.object.Player;
import game.object.PolyPlatform;
import game.object.RockPlatform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import template.Vec2d;

public class NinWorld extends GameWorld<NinObject> {
  
  
  public final static Image ASTRO = new Image("spritesheets/Astronaut1.png");
  public static final Vec2d ASTRO_POS = new Vec2d(3,0);
  public static final Vec2d ASTRO_SIZE = new Vec2d(9, 16);
  public final static double ASTRO_SCALE = 3.5;
  
  public final static Image MOONSURFACE = new Image("spritesheets/moonsurface.jpg");
  public final static Vec2d MOONSURFACE_SIZE = new Vec2d(1920, 1080);
  
  public static final double GRAVITY = 100;
  public static final double SIDE_SPEED = 50;
  public static final double TERMINAL_SPEED = 150;
  public static final double JUMP_IMP = 2000;
  
  public static final Image ROCK = new Image("spritesheets/rock.png");
  public static final Vec2d ROCK_LOC = new Vec2d(24);
  public static final Vec2d ROCK_SIZE = new Vec2d(280, 180);
  public static final double ROCK_SCALE = .3;
  
  public static final Image POLYPLAT = new Image("spritesheets/polyplatform.png");
  public static final Vec2d POLYPLATSIZE = new Vec2d(256, 218);
  public static final Vec2f[] POLYPLATSHAPE = {new Vec2f(75,0), new Vec2f(0,180), 
      new Vec2f(64,218), new Vec2f(195, 218), new Vec2f(256, 172), 
      new Vec2f(256, 125), new Vec2f(195, 0)};
  public static final double POLYPLATSCALE = .25;
  
  public static final Image BALL1 = new Image("spritesheets/ball1.png");
  public static final Vec2d BALL1_SIZE = new Vec2d(34);
  public static final int BALL1_FNUM = 2;
  public static final double BALL_1REST = .9;
  public static final double BALL1_SCALE = 1;
  public static final float BALL1_MASS = 2;
  
  public static final Image BALL2 = new Image("spritesheets/ball2.png");
  public static final Vec2d BALL2_SIZE = new Vec2d(52);
  public static final double BALL2_REST = .5;
  public static final double BALL2_SCALE = 1;
  public static final float BALL2_MASS = 3;
  
  public static final Image BALL3 = new Image("spritesheets/ball3.png");
  public static final Vec2d BALL3_SIZE = new Vec2d(67);
  public static final double BALL3_REST = .7;
  public static final double BALL3_SCALE = .7;
  public static final float BALL3_MASS = 2;
  public static final int BALL3_FNUM = 6;
  
  NinViewport view;
  
  public NinWorld(NinViewport viewport) {
    view = viewport;
    NinDraw ds = new NinDraw(this, viewport);
    
    Player p = new Player(new Vec2d(200));
    ds.addObject(p, 0);
    addSystem(ds);
    
    NinPhysics ps = new NinPhysics(this, p);
    //adding walls
    ps.addStaticObject(new GameWall(new Vec2d(0, 400), new Vec2d(1000, 100), 1));
    ps.addStaticObject(new GameWall(new Vec2d(0, -100), new Vec2d(2,540), .05));
    ps.addStaticObject(new GameWall(new Vec2d(960, -100), new Vec2d(2,540), .05));
    ps.addStaticObject(new GameWall(new Vec2d(0, -100), new Vec2d(960, 5), .05));
    addSystem(ps);
    
    
    //adding platforms
    Vec2d rockpos = new Vec2d(200, 270);
    RockPlatform rock = new RockPlatform(rockpos, 1);
    ds.addObject(rock, 0);
    ps.addStaticObject(rock);
    
    Vec2d rockpos2 = new Vec2d(600,200);
    RockPlatform rock1 = new RockPlatform(rockpos2, 1);
    ds.addObject(rock1, 0);
    ps.addStaticObject(rock1);
    
    Vec2d rockpos3 = new Vec2d(420, 330);
    PolygonShape platshape = toPolygon(POLYPLATSHAPE, rockpos3, POLYPLATSCALE);
    PolyPlatform rock3 = new PolyPlatform(rockpos3, platshape);
    ds.addObject(rock3, 0);
    ps.addStaticObject(rock3);
    
    //adding balls
    BallObject b1 = new BallObject(new Vec2d(100, 0), 1);
    ds.addObject(b1, 0);
    ps.addObject(b1);
    
    BallObject b2 = new BallObject(new Vec2d(780, 0), 2);
    ds.addObject(b2, 0);
    ps.addObject(b2);
    
    BallObject b3 = new BallObject(new Vec2d(520, 0), 3);
    ds.addObject(b3, 0);
    ps.addObject(b3);
    
    Set<KeyCode> inputKeys = new TreeSet<>();
    inputKeys.add(KeyCode.RIGHT);
    inputKeys.add(KeyCode.LEFT);
    inputKeys.add(KeyCode.UP);
    initializeKeyMap(inputKeys);
  }
  
  public PolygonShape toPolygon(Vec2f[] points, Vec2d topLeft, double scale) {
    Vec2f tl = topLeft.toVec2f();
    Vec2f[] polypoints = new Vec2f[points.length];
    for(int i = 0; i < points.length; i++) {
      polypoints[i] = points[i].smult((float) scale).plus(tl);
    }
    PolygonShape poly = new PolygonShape(polypoints);
    return poly;
  }

  @Override
  public void onTick(long nanos) {
    NinPhysics ps = (NinPhysics) getSystem("PHYSICS");
    ps.onTick(nanos);
    NinDraw nd = (NinDraw) getSystem("DRAW");
    nd.onTick(nanos);
  }

  @Override
  public void drawInBounds(GraphicsContext g, Vec2d topLeft2, Vec2d botRight) {
    NinDraw ds = (NinDraw) getSystem("DRAW");
    ds.drawInBounds(g, topLeft2, botRight);
  }

}
