package org.johnnei.ld26.miniharvest.item;

public class ItemPotato extends Item {

	public static final int ID = 5;
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "potato";
	}

	@Override
	public String getName() {
		return "Potato";
	}

}
