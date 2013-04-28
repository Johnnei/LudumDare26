package org.johnnei.ld26.miniharvest.item;

public class ItemWheat extends Item {

	public static final int ID = 3;
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "wheat";
	}

	@Override
	public String getName() {
		return "Wheat";
	}

}
