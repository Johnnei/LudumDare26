package org.johnnei.ld26.miniharvest.item;

public class ItemStack {
	
	private Item item;
	private int amount;
	
	public ItemStack(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public void addToAmount(int amount) {
		this.amount += amount;
	}
	
	public int getId() {
		return item.getId();
	}
	
	public String getName() {
		return item.getName();
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getAmount() {
		return amount;
	}

}
