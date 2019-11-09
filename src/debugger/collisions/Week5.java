package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.interfaces.Week5Reqs;
import engine.ShapeUtil;

public final class Week5 extends Week5Reqs {

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

	@Override
	public Vec2f collision(AABShape s1, PolygonShape s2) {
	  
	  Vec2f mtv = null;
    float mtv2size = -1;
    
    Vec2f polymtv = oneSidePolyCollision(s2, rectToPoly(s1));
    if(polymtv == null)
      return null;
    mtv = polymtv.smult(-1);
    mtv2size = mtv.mag2();
	  
    //checking on this x axis
		Vec2f aaX = new Vec2f(s1.getMinX(), s1.getMaxX());
		Vec2f polyX = projectPolygonX(s2);
		if(overlap(aaX, polyX)) {
		  float thismtv = overlapMtv(aaX, polyX);
		  float this2size = thismtv * thismtv;
		  if(mtv2size < 0 || mtv2size > this2size) {
		    mtv = new Vec2f(thismtv, 0);
		    mtv2size = this2size;
		  }
		} else {
		  return null;
		}
		
		//checking on the y axis
		Vec2f aaY = new Vec2f(s1.getMinY(), s1.getMaxY());
		Vec2f polyY = projectPolygonY(s2);
		if(overlap(aaY, polyY)) {
		  float thismtv = overlapMtv(aaY, polyY);
		  float this2size = thismtv * thismtv;
		  if(mtv2size < 0 || mtv2size > this2size) {
        mtv = new Vec2f(0, thismtv);
        mtv2size = this2size;
      }
		} else {
		  return null;
		}
		
		return mtv;
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
    } else if (s1.center.x == s2.center.x && s1.center.y == s2.center.y) {
      if(s1.radius < s2.radius) {
        return new Vec2f(0, s1.radius + s2.radius);
      } else {
        return new Vec2f(0, - s1.radius - s2.radius);
      }
    }else {
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

	@Override
	public Vec2f collision(CircleShape s1, PolygonShape s2) {
	  
	  Vec2f mtv = null;
    float mtv2size = -1;
	  
	  //check on the circle axis
		Vec2f closest = s2.points[0];
		double closedist = closest.dist2(s1.center);
		for(int i = 1; i < s2.points.length; i++) {
		  double dist = s2.points[i].dist2(s1.center);
		  if(dist < closedist) {
		    closedist = dist;
		    closest = s2.points[i];
		  }
		}
		
		Vec2f cNormal = closest.minus(s1.center);
		
		Vec2f cInt = projectCircle(s1, cNormal);
		Vec2f pInt = projectPolygon(cNormal, s2);
		
		if(overlap(cInt, pInt)) {
		  float thismtvl = overlapMtv(cInt, pInt);
      if(cNormal.x == 0) {
        Vec2f thismtv = new Vec2f(0, thismtvl);
        float this2size = thismtvl * thismtvl;
        if(mtv2size < 0 || mtv2size > this2size) {
          mtv = thismtv;
          mtv2size = this2size;
        }
      } else {
        Vec2f thismtv = new Vec2f(thismtvl, 
            thismtvl * cNormal.y / cNormal.x);
        float this2size = thismtv.mag2();
        if(mtv2size < 0 || this2size < mtv2size) {
          mtv2size = this2size;
          mtv = thismtv;
        }
      }
		} else {
		  return null;
		}
    
    //checking on each axis for the polygon
    Vec2f e1 = s2.points[s2.points.length - 1];
    Vec2f e2;
    for(int i = 0; i < s2.points.length; i++) {
      e2 = s2.points[i];
      Vec2f edgeLine = e2.minus(e1);
      Vec2f edgeNormal = new Vec2f(edgeLine.y, - edgeLine.x);
      Vec2f circleint = projectCircle(s1, edgeNormal);
      Vec2f polyint = projectPolygon(edgeNormal, s2);
      if(overlap(circleint, polyint)) {
        float thismtvl = overlapMtv(circleint, polyint);
        if(edgeNormal.x == 0) {
          Vec2f thismtv = new Vec2f(0, thismtvl);
          float this2size = thismtvl * thismtvl;
          if(mtv2size < 0 || mtv2size > this2size) {
            mtv = thismtv;
            mtv2size = this2size;
          }
        } else {
          Vec2f thismtv = new Vec2f(thismtvl, 
              thismtvl * edgeNormal.y / edgeNormal.x);
          float this2size = thismtv.mag2();
          if(mtv2size < 0 || this2size < mtv2size) {
            mtv2size = this2size;
            mtv = thismtv;
          }
        }
      } else {
        return null;
      }
      e1 = e2;
    }
		
		return mtv;
	}
	
	// POLYGONS

	@Override
	public Vec2f collision(PolygonShape s1, AABShape s2) {
		Vec2f f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2f collision(PolygonShape s1, CircleShape s2) {
		Vec2f f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2f collision(PolygonShape s1, Vec2f s2) {
	  Vec2f e1 = s1.points[s1.points.length - 1];
	  Vec2f e2;
	  for(int i = 0; i < s1.points.length; i++) {
	    e2 = s1.points[i];
	    if (e2.minus(e1).cross(e2.minus(s2)) < 0) {
	      return null;
	    } 
	    e1 = e2;
	  }
		return new Vec2f(1);
	}

	@Override
	public Vec2f collision(PolygonShape s1, PolygonShape s2) {
		Vec2f mtv1 = oneSidePolyCollision(s1, s2);
		if(mtv1 == null)
		  return null;
		Vec2f mtv2 = oneSidePolyCollision(s2, s1).smult(-1);
		if(mtv2 == null) 
		  return null;
		if(mtv1.mag2() > mtv2.mag2())
		  return mtv2;
		else
		  return mtv1;
	}
	
	private boolean overlap(Vec2f i1, Vec2f i2) {
	  return i1.x <= i2.y && i2.x <= i1.y;
	}
	
	private Vec2f projectPolygon(Vec2f l1, PolygonShape s) {
	  Vec2f l2 = new Vec2f(0);
	  
	  if(l1.x == l2.x) {
	    return projectPolygonY(s);
	  }
	  
	  Vec2f p1 = s.points[s.points.length - 1].projectOntoLine(l1, l2);
	  float min = p1.x;
	  float max = p1.x;
	  for(int i = 0; i < s.points.length; i++) {
	    float xVal = s.points[i].projectOntoLine(l1, l2).x;
	    if(xVal < min) {
	      min = xVal;
	    } else if (xVal > max) {
	      max = xVal;
	    }
	  }
	  return new Vec2f(min, max);
	}
	
	private Vec2f projectPolygonY(PolygonShape s) {
	  float min = s.points[s.points.length - 1].y;
	  float max = min;
	  for(int i = 0; i < s.points.length; i++) {
	    float yval = s.points[i].y;
	    if(yval < min) {
	      min = yval;
	    } else if (yval > max) {
	      max = yval;
	    }
	  }
	  return new Vec2f(min, max);
	}
	
	private Vec2f projectPolygonX(PolygonShape s) {
    float min = s.points[s.points.length - 1].x;
    float max = min;
    for(int i = 1; i < s.points.length; i++) {
      float xval = s.points[i].x;
      if(xval < min) {
        min = xval;
      } else if (xval > max) {
        max = xval;
      }
    }
    return new Vec2f(min, max);
  }
	
	private PolygonShape rectToPoly(AABShape s) {
    return new PolygonShape(new Vec2f(s.getMinX(), s.getMinY()), 
        new Vec2f(s.getMaxX(), s.getMinY()),
        new Vec2f(s.getMaxX(), s.getMaxY()),
        new Vec2f(s.getMinX(), s.getMaxY()));
	}
	
	private Vec2f getMtv(Vec2f int1, Vec2f int2, Vec2f line) {
	  if(overlap(int1, int2)) {
	    float mtvl = overlapMtv(int1, int2);
	    if(line.x == 0) {
        return new Vec2f(0, mtvl);
      } else {
        return new Vec2f(mtvl, mtvl * line.y / line.x);
      }
	  } else {
	    return null;
	  }
	}
	
	private float overlapMtv(Vec2f i1, Vec2f i2) {
	  float left = i1.y - i2.x;
	  float right = i2.y - i1.x;
	  if(left < right) {
	    return -left;
	  } else {
	    return right;
	  }
	}
	
	private Vec2f projectCircle(CircleShape c, Vec2f l) {
	  Vec2f cc = c.center;
	  if(l.x == 0) {
	    return new Vec2f(cc.y - c.radius, cc.y + c.radius);
	  } else {
	    Vec2f change = l.normalize().smult(c.radius);
	    Vec2f lChange = cc.minus(change).projectOnto(l);
	    Vec2f rRight = cc.plus(change).projectOnto(l);
	    if(change.x < 0) {
	      return new Vec2f(rRight.x, lChange.x);
	    } else {
	      return new Vec2f(lChange.x, rRight.x);
	    }
	  }
	}
	
	private Vec2f oneSidePolyCollision(PolygonShape s1, PolygonShape s2) {
	  Vec2f mtv = null;
    float mtv2size = -1;
    
    
    //checking on each axis for the polygon
    Vec2f e1 = s1.points[s1.points.length - 1];
    Vec2f e2;
    for(int i = 0; i < s1.points.length; i++) {
      e2 = s1.points[i];
      
      Vec2f edgeLine = e2.minus(e1);
      Vec2f edgeNormal = new Vec2f(edgeLine.y, - edgeLine.x);
      Vec2f aabint = projectPolygon(edgeNormal, s1);
      Vec2f polyint = projectPolygon(edgeNormal, s2);
      Vec2f thismtv = getMtv(aabint, polyint, edgeNormal);
      if(thismtv == null)
        return null;
      float thismag2 = thismtv.mag2();
      if(mtv2size < 0 || mtv2size > thismag2) {
        mtv = thismtv;
        mtv2size = thismag2;
      }
      e1 = e2;
    }
	  return mtv;
	}
	
}
