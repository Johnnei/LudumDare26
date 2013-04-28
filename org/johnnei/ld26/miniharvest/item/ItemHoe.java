package org.johnnei.ld26.miniharvest.item;

import org.johnnei.ld26.engine.sound.SoundManager;
import org.johnnei.ld26.miniharvest.MiniHarvest;


public class ItemHoe extends Item {

	public static final int ID = 1;
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "hoe";
	}
	
	@Override
	public String getName() {
		return "Hoe";
	}
	
	@Override
	public void onUse() {
		SoundManager.getInstance().playSound(MiniHarvest.SOUND_ITEM_HOE);
	}

}
