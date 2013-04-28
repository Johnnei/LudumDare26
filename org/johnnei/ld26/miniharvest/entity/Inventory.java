package org.johnnei.ld26.miniharvest.entity;

import static org.johnnei.ld26.engine.render.RenderObject.VERTEX_TEXTURE;

import java.util.ArrayList;

import org.johnnei.ld26.engine.GameKeyboard;
import org.johnnei.ld26.engine.render.Renderable;
import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class Inventory extends Renderable {
	
	private final int MOVE_DOWN_INTERVAL = 5000;
	
	private ArrayList<ItemStack> items;
	private int selectedItem;
	private int lastActionAge;
	private float y;
	
	public Inventory() {
		super(VERTEX_TEXTURE);
		items = new ArrayList<>();
		renderObject.setTexture("/res/hotbar.png");
		renderObject.updateTexture();
		renderObject.updateVertex(new VertexHelper(352, 584, 96, 16));
	}
	
	/**
	 * Add item to the inventory.
	 * Use old stacks first
	 * @param item
	 */
	public void addItem(ItemStack item) {
		lastActionAge = 0;
		for(ItemStack itemStack : items) {
			if(itemStack.getId() == item.getId()) {
				itemStack.addToAmount(item.getAmount());
				return;
			}
		}
		items.add(item);
	}
	
	@Override
	public void onTick(int deltaMs) {
		onInventoryTick(deltaMs);
		onRenderTick();
	}
	
	private void onInventoryTick(int deltaMs) {
		//lastActionAge += deltaMs;
		for(int i = 0; i < items.size(); i++) {
			ItemStack itemStack = items.get(i);
			itemStack.getItem().onTick(deltaMs);
			if(itemStack.getAmount() == 0) {
				items.remove(i);
				if(i == selectedItem) {
					selectedItem = checkIndex(selectedItem - 1);
				}
				i--;
			}
		}
		if(GameKeyboard.getInstance().isKeyPressed(Keyboard.KEY_LEFT)) {
			selectedItem = checkIndex(selectedItem - 1);
			lastActionAge = 0;
		} else if(GameKeyboard.getInstance().isKeyPressed(Keyboard.KEY_RIGHT)) {
			selectedItem = checkIndex(selectedItem + 1);
			lastActionAge = 0;
		}
	}
	
	private int checkIndex(int newSelectedItem) {
		while(newSelectedItem < 0) {
			newSelectedItem += items.size();
		}
		while(newSelectedItem >= items.size()) {
			newSelectedItem -= items.size();
		}
		return newSelectedItem;
	}
	
	private void onRenderTick() {
		//Hotbar Position
		float moveY = 0.05f * (lastActionAge - MOVE_DOWN_INTERVAL);
		if(moveY < 0)
			moveY = 0;
		y = 568 + moveY;
		renderObject.updateVertex(new VertexHelper(304, y, 192, 32));
		//Hide all Items
		if(items.size() > 5) {
			for(int i = 0; i < items.size(); i++) {
				items.get(i).getItem().setLocation(-64, 0, 32, 32);
			}
		}
		int barIndex = 0;
		int minItemOffset = -2;
		if(items.size() == 3) {
			minItemOffset++;
		}
		//Show Item in hotbar if should be visible
		for(int itemOffset = 2; itemOffset >= minItemOffset; itemOffset--, barIndex++) {
			Item item = items.get(checkIndex(selectedItem - itemOffset)).getItem();
			item.setLocation(320 + (barIndex * 32), y, 32, 32);
		}
	}
	
	@Override
	public void render() {
		super.render();
		for(ItemStack itemStack : items) {
			itemStack.getItem().render();
		}
		if(items.size() > 0 && selectedItem < items.size() && selectedItem >= 0) {
			ItemStack itemStack = items.get(selectedItem);
			if(itemStack.getAmount() > 1) {
				TextRender.getInstance().drawCentered(400, y - 20, itemStack.getAmount() + " " + itemStack.getName(), null);
			} else {
				TextRender.getInstance().drawCentered(400, y - 20, itemStack.getName(), null);
			}
		}
	}
	

	public void resetLastAction() {
		lastActionAge = 0;
	}
	
	@Override
	public boolean canDelete() {
		return false;
	}
	
	/**
	 * Gets an item from the inventory
	 * @param id The id of the item to find
	 * @return an item with the same id or null if not found
	 */
	public ItemStack getItem(int id) {
		for(ItemStack itemStack : items) {
			if(itemStack.getId() == id) {
				return itemStack;
			}
		}
		return null;
	}

	public ItemStack getSelectedItemStack() {
		if(items.size() == 0) {
			return null;
		} else {
			return items.get(selectedItem);
		}
	}
	
	public Item getSelectedItem() {
		if(items.size() == 0) {
			return null;
		} else {
			return items.get(selectedItem).getItem();
		}
	}

}
