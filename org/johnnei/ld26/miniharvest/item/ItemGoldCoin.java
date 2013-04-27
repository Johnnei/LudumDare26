package org.johnnei.ld26.miniharvest.item;


public class ItemGoldCoin extends Item {

	public ItemGoldCoin(int amount) {
		super(amount);
	}

	public static final int ID = 0;
	
	@Override
	public int getId() {
		return ID;
	}

}
