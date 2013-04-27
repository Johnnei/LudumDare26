package org.johnnei.ld26.miniharvest.entity;

import org.johnnei.ld26.engine.GameKeyboard;
import org.johnnei.ld26.engine.render.Texture;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.Map;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemGoldCoin;
import org.johnnei.ld26.miniharvest.item.ItemHoe;
import org.johnnei.ld26.miniharvest.item.ItemStack;
import org.johnnei.ld26.miniharvest.item.ItemWheatSeed;
import org.lwjgl.input.Keyboard;

public class Player extends Entity {
	
	private final float MOVE_SPEED = 0.03F;
	private final int MIN_ACTION_INTERVAL = 250;
	private final int MIN_WARP_INTERVAL = 5000;
	
	private Inventory inventory;
	/**
	 * The index of the the currently shown texture
	 */
	private int textureIndex;
	
	/**
	 * ms since last action
	 */
	private int lastActionAge;
	/**
	 * Ms since last warp
	 */
	private int lastWarpAge;
	
	public Player(Map map, float x, float y) {
		super(map, x, y);
		//Generate Texture Data
		Texture texture = new Texture("/res/player.png");
		texture.addSubTexture(0, 0, 16, 16);
		texture.addSubTexture(16, 0, 16, 16);
		texture.addSubTexture(32, 0, 16, 16);
		texture.addSubTexture(48, 0, 16, 16);
		texture.addSubTexture(64, 0, 16, 16);
		texture.addSubTexture(80, 0, 16, 16);
		texture.addSubTexture(96, 0, 16, 16);
		texture.addSubTexture(112, 0, 16, 16);
		//texture.addSubTexture(128, 0, 16, 16);
		renderObject.setTexture(texture);
		renderObject.resetBuffers();
		renderObject.updateTexture();
		renderObject.updateVertex(new VertexHelper(x, y, 16f, 16f));
		textureIndex = 3;
		//Construct Player Data
		inventory = new Inventory();
		addItem(new ItemGoldCoin(), 100);
		addItem(new ItemHoe(), 1);
		addItem(new ItemWheatSeed(), 1);
	}

	@Override
	public void onTick(int deltaMs) {
		onTickMovement(deltaMs);
		onTickActions(deltaMs);
		inventory.onTick(deltaMs);
		//Warp
		lastWarpAge += deltaMs;
	}
	
	
	
	private void onTickMovement(int deltaMs) {
		int textureIdx = 0;
		//Vertical Movement
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			y -= deltaMs * MOVE_SPEED;
			textureIdx += 3;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			y += deltaMs * MOVE_SPEED;
			textureIdx += 6;
		}
		//Horizontal Movement
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x -= deltaMs * MOVE_SPEED;
			textureIdx += 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x += deltaMs * MOVE_SPEED;
			textureIdx += 2;
		}
		//Check if update is needed
		if(textureIdx != 0) {
			textureIndex = textureIdx - 1;
			
			//Maintain within game field
			clipCoordinates();
			
			renderObject.updateVertex(new VertexHelper(x, y, 16f, 16f));
		}
	}
	
	private void onTickActions(int deltaMs) {
		lastActionAge += deltaMs;
		if(lastActionAge >= MIN_ACTION_INTERVAL) {
			if(GameKeyboard.getInstance().isKeyPressed(Keyboard.KEY_SPACE)) {
				map.getBlock(getBlockX(), getBlockY()).onPlayerInteraction(this, inventory.getSelectedItem());
			}
		}
	}
	
	@Override
	public void render() {
		final int offset = (2 * 4 * 4) + (textureIndex * 2 * 4 * 4);
		renderObject.render(offset);
		inventory.render();
	}
	
	/**
	 * Adds an item to the inventory of the player
	 * @param item
	 */
	public void addItem(Item item, int amount) {
		inventory.addItem(new ItemStack(item, amount));
	}
	
	public void addAmountToSelectedItem(int amount) {
		ItemStack item = inventory.getSelectedItemStack();
		if(item != null) {
			item.addToAmount(amount);
		}
	}
	
	public void warp(int x, int y) {
		lastWarpAge = 0;
		this.x = x;
		this.y = y;
		renderObject.updateVertex(new VertexHelper(x, y, 16f, 16f));
	}
	
	public int getGoldCount() {
		ItemStack item = inventory.getItem(ItemGoldCoin.ID);
		if(item != null) {
			return item.getAmount();
		} else {
			return 0;
		}
	}
	
	public void addAmountToItem(int id, int amount) {
		ItemStack item = inventory.getItem(id);
		if(item != null) {
			item.addToAmount(amount);
		}
	}
	
	public boolean canWarp() {
		return lastWarpAge >= MIN_WARP_INTERVAL;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

}
