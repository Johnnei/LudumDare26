package org.johnnei.ld26.miniharvest.item;


public class ItemGoldCoin extends Item {

	public static final int ID = 0;
	
	@Override
	public int getId() {
		return ID;
	}
	
	@Override
	public String getTextureName() {
		return "gold";
	}
	
	@Override
	public String getName() {
		return "Gold Coin";
	}

}
