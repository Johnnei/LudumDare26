package org.johnnei.ld26.miniharvest.item;

import java.util.Random;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Entity;

public class ItemPickupEntity extends Entity {
	
	public static final int MAX_LIFETIME = /*5 * 60000*/5000;
	/**
	 * The SQUARED pickup radius (This avoids the cpu cost of rooting :D)
	 */
	public static final int PICKUP_RADIUS = 16 * 16;
	
	private Item item;
	private int lifetime;
	
	private float motionX;
	private float motionY;
	
	public ItemPickupEntity(Item item, Map map, float x, float y) {
		super(map, x, y);
		this.item = item;
		
		Random rand = new Random();
		motionX = 0.001f * rand.nextInt(50);
		motionY = 0.001f * rand.nextInt(50);
		if(rand.nextBoolean())
			motionX = -motionX;
		if(rand.nextBoolean())
			motionY = -motionY;
		
		renderObject.setTexture("/res/item/" + item.getTextureName() + ".png");
		renderObject.updateTexture();
		renderObject.updateVertex(new VertexHelper(x, y, 16f, 16f));
	}

	@Override
	public void onTick(int deltaMs) {
		lifetime += deltaMs;
		x += motionX;
		y += motionY;
		clipCoordinates();
		renderObject.updateVertex(new VertexHelper(x, y, 16f, 16f));
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getLifetime() {
		return lifetime;
	}

}
