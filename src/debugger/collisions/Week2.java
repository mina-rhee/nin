package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.interfaces.Week2Reqs;

public final class Week2 extends Week2Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
	  //x interval overlap
	  return s1.getMinX() <= s2.getMaxX() && s2.getMinX() <= s1.getMaxX()
	      && s1.getMinY() <= s2.getMaxY() && s2.getMinY() <= s1.getMaxY();
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
	  float clampX = clamp(s2.center.x, s1.getMinX(), s1.getMaxX());
	  float clampY = clamp(s2.center.y, s1.getMinY(), s1.getMaxY());
	  return isColliding(s2, new Vec2f(clampX, clampY));
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2f s2) {
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
	
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		return isColliding(s2, s1);
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
	  float radiSum = s1.radius + s2.radius;
		return s1.center.dist2(s2.center) <= radiSum * radiSum;
	}

	@Override
	public boolean isColliding(CircleShape s1, Vec2f s2) {
		return s1.center.dist2(s2) <= s1.radius * s1.radius;
	}

	
}
