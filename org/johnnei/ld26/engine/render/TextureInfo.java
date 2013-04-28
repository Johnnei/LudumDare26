package org.johnnei.ld26.engine.render;

public class TextureInfo {
	
	private int id;
	private int width;
	private int height;
	private int count;
	
	public TextureInfo(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.count = 1;
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void addToCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}

	public void decreaseCount() {
		count--;
	}

}
