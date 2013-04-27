package org.johnnei.ld26.miniharvest.item;


public class ItemWheatSeed extends ItemSeed {

	public static final int ID = 2;
	
	@Override
	public int getId() {
		return ID;
	}
	
	@Override
	public String getTextureName() {
		return "wheat_seed";
	}
	
	@Override
	public String getName() {
		if(amount > 1) {
			return "Wheat Seeds";
		} else {
			return "Wheat Seed";
		}
	}
	
	@Override
	public Seed getSeed() {
		return new Seed(new ItemGoldCoin(1), 5000, 1, 5);
	}

}
