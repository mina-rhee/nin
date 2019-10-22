package engine.systems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameSystem<G> {
  
  private ArrayList<Set<G>> layers = new ArrayList<>();
  private GameWorld<?> gameworld;
  
  public GameSystem(GameWorld<?> world) {
    setGameworld(world);
  }

  public GameWorld<?> getGameworld() {
    return gameworld;
  }

  public void setGameworld(GameWorld<?> world) {
    gameworld = world;
  }

  public ArrayList<Set<G>> getLayers() {
    return layers;
  }
  
  public Set<G> getLayer(int layer) {
    return layers.get(layer);
  }
  
  public void addLayer(int layer) {
    if(layer == layers.size()) {
      layers.add(new HashSet<G>());
    }
  }
  
  public void addObject(G obj, int layer) {
    if(layer < layers.size()) {
      layers.get(layer).add(obj);
    }
  }
  
  public void deleteObject(G obj, int layer) {
    if(layer < layers.size()) {
      layers.get(layer).remove(obj);
    }
  }
  
  public abstract String getTag();
  
  public abstract void onDraw(GraphicsContext g);
  public abstract void onTick(long nanos);
  
}
