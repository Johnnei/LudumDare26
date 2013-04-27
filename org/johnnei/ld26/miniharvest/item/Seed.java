package org.johnnei.ld26.miniharvest.item;

import java.util.Random;


/**
 * @author Johnnei
 *
 */
public class Seed {
	
	/**
	 * The duration in ms before the seed will popout into items
	 */
	private int growDuration;
	/**
	 * The product which this seed produces
	 */
	private Item growProduct;
	/**
	 * The minimum amount of product dropped on grow completion
	 */
	private int minDropCount;
	/**
	 * The maximum amount of product dropped on grow completion
	 */
	private int maxDorpCount;
	
	public Seed(Item growProduct, int growDuration, int minDropCount, int maxDropCount) {
		this.growDuration = growDuration;
		this.growProduct = growProduct;
		this.minDropCount = minDropCount;
		this.maxDorpCount = maxDropCount;
	}
	
	public int getGrowDuration() {
		return growDuration;
	}
	
	public Item getGrowProduct() {
		return growProduct;
	}
	
	/**
	 * Gets a randomized dropcount between the min (inclusive) and max (exclusive)
	 * @return
	 */
	public int getDropCount() {
		return minDropCount + new Random().nextInt(maxDorpCount - minDropCount);
	}

}
