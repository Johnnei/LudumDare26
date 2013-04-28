package org.johnnei.ld26.miniharvest.item;

import static org.johnnei.ld26.engine.render.RenderObject.VERTEX_TEXTURE;

import org.johnnei.ld26.engine.render.Renderable;
import org.johnnei.ld26.engine.render.VertexHelper;

public abstract class Item extends Renderable {
	
	public Item() {
		super(VERTEX_TEXTURE);
		renderObject.setTexture("/res/item/" + getTextureName() + ".png");
		renderObject.updateTexture();
	}

	public abstract int getId();
	public abstract String getTextureName();
	public abstract String getName();
	
	public void onTick(int deltaMs) {
	}
	
	public void onUse() {
	}
	
	public void setLocation(int x, float y, int width, int height) {
		renderObject.updateVertex(new VertexHelper(x, y, width, height));
	}
	
	@Override
	public boolean canDelete() {
		return false;
	}
	
}
