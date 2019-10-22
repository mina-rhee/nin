package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.interfaces.Week3Reqs;
import engine.ShapeUtil;

public final class Week3 extends Week3Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2f collision(AABShape s1, AABShape s2) {
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

	@Override
	public Vec2f collision(AABShape s1, CircleShape s2) {
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

	@Override
	public Vec2f collision(AABShape s1, Vec2f s2) {
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

	@Override
	public Vec2f collision(CircleShape s1, AABShape s2) {
		Vec2f f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2f collision(CircleShape s1, CircleShape s2) {
	  if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      return s1.getCenter().minus(s2.getCenter()).normalize()
          .smult((s1.getRadius() + s2.getRadius() - 
              s1.getCenter().dist(s2.getCenter())));
    }
	}

	@Override
	public Vec2f collision(CircleShape s1, Vec2f s2) {
	  if(!ShapeUtil.isColliding(s1, s2)) {
      return null;
    } else {
      return s2.minus(s1.getCenter()).normalize().smult(s1.getRadius());
    }
	}

}
