package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;

public class BlockGrass extends Block {
	
	public BlockGrass(Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/grass.png");
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
		// TODO Auto-generated method stub

	}

}
