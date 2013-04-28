package org.johnnei.ld26.miniharvest.item;

public class ItemCarrot extends Item {

	public static final int ID = 7;
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "carrot";
	}

	@Override
	public String getName() {
		return "Carrot";
	}

}
