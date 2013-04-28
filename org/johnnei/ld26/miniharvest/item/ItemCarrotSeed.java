package org.johnnei.ld26.miniharvest.item;

public class ItemCarrotSeed extends ItemSeed {

	public static final int ID = 8;
	
	@Override
	public Seed getSeed() {
		return new Seed(new ItemCarrot(), 15000, 1, 1);
	}

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getTextureName() {
		return "carrot_seed";
	}

	@Override
	public String getName() {
		return "Carrot Seed";
	}

}
