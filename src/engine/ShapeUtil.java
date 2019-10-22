package engine;

import debugger.collisions.AABShape;
import debugger.collisions.CircleShape;
import debugger.collisions.Week2;
import debugger.support.Vec2f;

public final class ShapeUtil {
  
  public static boolean isColliding(AABShape s1, AABShape s2) {
    //x interval overlap
    return s1.getMinX() <= s2.getMaxX() && s2.getMinX() <= s1.getMaxX()
        && s1.getMinY() <= s2.getMaxY() && s2.getMinY() <= s1.getMaxY();
  }

  public static boolean isColliding(AABShape s1, CircleShape s2) {
    float clampX = clamp(s2.getCenter().x, s1.getMinX(), s1.getMaxX());
    float clampY = clamp(s2.getCenter().y, s1.getMinY(), s1.getMaxY());
    return isColliding(s2, new Vec2f(clampX, clampY));
  }

  public static boolean isColliding(AABShape s1, Vec2f s2) {
    return s2.x >= s1.getMinX() && s2.x <= s1.getMaxX()
        && s2.y >= s1.getMinY() && s2.y <= s1.getMaxY();
  }
  
  public static float clamp(float a, float min, float max) {
    assert(min <= max);
    if(a < min)
      return min;
    if(a > max)
      return max;
    else
      return a;
  }

  // CIRCLES
  public static boolean isColliding(CircleShape s1, AABShape s2) {
    return isColliding(s2, s1);
  }

  public static boolean isColliding(CircleShape s1, CircleShape s2) {
    float radiSum = s1.getRadius() + s2.getRadius();
    return s1.getCenter().dist2(s2.getCenter()) <= radiSum * radiSum;
  }

  public static boolean isColliding(CircleShape s1, Vec2f s2) {
    return s1.getCenter().dist2(s2) <= s1.getRadius() * s1.getRadius();
  }
  
  
  public static Vec2f collision(AABShape s1, AABShape s2) {
    if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    }
    float up = s1.getMaxY() - s2.getMinY();
    float down = s2.getMaxY() - s1.getMinY();
    float left = s1.getMaxX() - s2.getMinX();
    float right = s2.getMaxX() - s1.getMinX();
    if(up < down && up < left && up < right) {
      return new Vec2f(0, -up);
    } else if (down < left && down < right) {
      return new Vec2f(0, down);
    } else if (left < right) {
      return new Vec2f(-left, 0);
    } else {
      return new Vec2f(right, 0);
    }
  }

  public static Vec2f collision(AABShape s1, CircleShape s2) {
    if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      Vec2f c = s2.getCenter();
      float r = s2.getRadius();
      if(ShapeUtil.isColliding(s1, c)) {
        float up = s1.getMaxY() - c.y;
        float down = c.y - s1.getMinY();
        float left = s1.getMaxX() - c.x;
        float right = c.x - s1.getMinX();
        if(up < down && up < left && up < right) {
          return new Vec2f(0, -up - r);
        } else if (down < left && down < right) {
          return new Vec2f(0, down + r);
        } else if (left < right) {
          return new Vec2f(-left - r, 0);
        } else {
          return new Vec2f(right + r, 0);
        }
      } else {
        //TODO: make this more efficient - i can check direction w clamp
        float xClamp = Week2.clamp(c.x, s1.getMinX(), s1.getMaxX());
        float yClamp = Week2.clamp(c.y, s1.getMinY(), s1.getMaxY());
        Vec2f clamp = new Vec2f(xClamp, yClamp);
        return clamp.minus(c).normalize().smult(r - clamp.dist(c));
      }
    }
  }

  public static Vec2f collision(AABShape s1, Vec2f s2) {
    if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      float up = s1.getMaxY() - s2.y;
      float down = s2.y - s1.getMinY();
      float left = s1.getMaxX() - s2.x;
      float right = s2.x - s1.getMinX();
      if(up < down && up < left && up < right) {
        return new Vec2f(0, -up);
      } else if (down < left && down < right) {
        return new Vec2f(0, down);
      } else if (left < right) {
        return new Vec2f(-left, 0);
      } else {
        return new Vec2f(right, 0);
      }
    }
  }
  
  // CIRCLES

  public static Vec2f collision(CircleShape s1, AABShape s2) {
    Vec2f f = collision(s2, s1);
    return f == null ? null : f.reflect();
  }

  
  public static Vec2f collision(CircleShape s1, CircleShape s2) {
    if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      return s1.getCenter().minus(s2.getCenter()).normalize()
          .smult((s1.getRadius() + s2.getRadius() - 
              s1.getCenter().dist(s2.getCenter())));
    }
  }

  
  public static Vec2f collision(CircleShape s1, Vec2f s2) {
    if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      return s2.minus(s1.getCenter()).normalize().smult(s1.getRadius());
    }
  }
}
