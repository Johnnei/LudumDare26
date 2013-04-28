package org.johnnei.ld26.miniharvest.item;

import org.johnnei.ld26.engine.sound.SoundManager;
import org.johnnei.ld26.miniharvest.MiniHarvest;

public class ItemPlow extends Item {

	public static final int ID = 6;
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "plow";
	}

	@Override
	public String getName() {
		return "Spade";
	}
	
	@Override
	public void onUse() {
		SoundManager.getInstance().playSound(MiniHarvest.SOUND_ITEM_PLOW);
	}

}
