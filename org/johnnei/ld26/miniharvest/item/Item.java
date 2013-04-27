package org.johnnei.ld26.miniharvest.item;


public abstract class Item {

	private int amount;
	
	public Item(int amount) {
		this.amount = amount;
	}
	
	public Item() {
		this(1);
	}

	public abstract int getId();
	
	public void onTick(int deltaMs) {
	}
	
	public void addToAmount(int amount) {
		this.amount += amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
