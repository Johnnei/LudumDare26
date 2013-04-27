package org.johnnei.ld26.miniharvest.entity;

import static org.johnnei.ld26.engine.render.RenderObject.VERTEX_TEXTURE;

import org.johnnei.ld26.engine.math.Point;
import org.johnnei.ld26.engine.render.Renderable;
import org.johnnei.ld26.miniharvest.Map;

public abstract class Entity extends Renderable {
	
	protected Map map;
	protected float x;
	protected float y;
	
	public Entity(Map map, float x, float y) {
		super(VERTEX_TEXTURE);
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public abstract void onTick(int deltaMs);
	
	@Override
	public boolean canDelete() {
		return false;
	}
	
	protected void clipCoordinates() {
		if(x < 0)
			x = 0;
		if(x > 784)
			x = 784;
		if(y < 0)
			y = 0;
		if(y > 584)
			y = 584;
	}
	
	/**
	 * The entity x-coordinate
	 * @return
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * The entity y-coordinate
	 * @return
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * The entity x-coordinate mapped to Blocks
	 * @return
	 */
	public int getBlockX() {
		return (int)x >> 4;
	}
	
	/**
	 * The entity y-coordinate mapped to Blocks
	 * @return
	 */
	public int getBlockY() {
		return (int)y >> 4;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Point getPoint() {
		return new Point(x, y);
	}

}
