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
		return "Wheat Seed";
	}
	
	@Override
	public Seed getSeed() {
		return new Seed(new ItemWheat(), 30000, 1, 4);
	}

}
