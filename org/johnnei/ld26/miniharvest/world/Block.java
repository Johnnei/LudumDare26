package org.johnnei.ld26.miniharvest.world;

import static org.johnnei.ld26.engine.render.RenderObject.VERTEX_TEXTURE;

import org.johnnei.ld26.engine.render.Renderable;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;

public abstract class Block extends Renderable {
	
	protected Map map;
	protected int x;
	protected int y;
	
	public Block(Map map, int x, int y) {
		super(VERTEX_TEXTURE);
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public abstract void onTick(int deltaMs);
	
	public void onPlayerInteraction(Player player, Item item) {
	}
	
	public void renderOnPlayerStandOn(Player player) {
	}
	
	@Override
	public boolean canDelete() {
		return false;
	}

}
