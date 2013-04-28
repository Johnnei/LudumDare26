package org.johnnei.ld26.miniharvest.item;

public class ItemPotatoSeed extends ItemSeed {

	public static final int ID = 4;
	
	@Override
	public Seed getSeed() {
		return new Seed(new ItemPotato(), 40000, 1, 2);
	}

	@Override
	public int getId() {
		return 4;
	}

	@Override
	public String getTextureName() {
		return "potato_seed";
	}

	@Override
	public String getName() {
		return "Potato Seed";
	}

}
