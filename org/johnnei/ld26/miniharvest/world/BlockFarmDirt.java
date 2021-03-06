package org.johnnei.ld26.miniharvest.world;

import java.util.Random;

import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.engine.sound.SoundManager;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.MiniHarvest;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemSeed;

public class BlockFarmDirt extends Block {
	
	private final long DECAY_TIME = 60000;
	
	private int lifetime;
	
	public BlockFarmDirt(Map map, int x, int y) {
		super(map, x, y);
		renderObject.updateVertex(new VertexHelper(x * 16, y * 16, 16, 16));
		renderObject.setTexture("/res/world/farmdirt.png");
		renderObject.updateTexture();
	}
	

	@Override
	public void onTick(int deltaMs) {
		lifetime += deltaMs;
		if(lifetime >= DECAY_TIME) {
			Random random = new Random();
			if(random.nextInt(100) < 10) {
				map.setBlock(x, y, new BlockDirt(random, map, x, y));
			} else {
				map.setBlock(x, y, new BlockGrass(random, map, x, y));
			}
			renderObject.delete();
		}
	}
	
	@Override
	public void onPlayerInteraction(Player player, Item item) {
		if(item instanceof ItemSeed) {
			ItemSeed seed = (ItemSeed)item;
			map.setBlock(x, y, new BlockFarmSeeded(seed.getSeed(), map, x, y));
			renderObject.delete();
			player.addAmountToSelectedItem(-1);
			SoundManager.getInstance().playSound(MiniHarvest.SOUND_PLANT_SEED);
		}
	}

}
