package org.johnnei.ld26.miniharvest.world;

import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;

public class BlockBanner extends Block {
	
	public BlockBanner(Map map, int x, int y, int textureIndex, String bannerName, boolean isVertical) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		Texture texture = new Texture("/res/world/banner_" + bannerName + ".png");
		if(isVertical) {
			texture.addSubTexture(0, textureIndex * 16, 16, 16);
		} else {
			texture.addSubTexture(textureIndex * 16, 0, 16, 16);
		}
		renderObject.setTexture(texture);
		renderObject.updateTexture();
	}
	
	public BlockBanner(Map map, int x, int y, int textureIndex, String bannerName) {
		this(map, x, y, textureIndex, bannerName, false);
	}
	

	@Override
	public void onTick(int deltaMs) {
	}

}
