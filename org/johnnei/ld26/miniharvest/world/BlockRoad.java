package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;

public class BlockRoad extends Block {
	
	public BlockRoad(Map map, int x, int y, int textureIndex) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		Texture texture = new Texture("/res/world/road.png");
		texture.addSubTexture(textureIndex * 16, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
	}

}
