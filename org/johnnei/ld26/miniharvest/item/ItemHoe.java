package org.johnnei.ld26.miniharvest.item;


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

}
