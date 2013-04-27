package org.johnnei.ld26.miniharvest.entity;

import static org.johnnei.ld26.engine.render.RenderObject.VERTEX_TEXTURE;

import java.util.ArrayList;

import org.johnnei.ld26.engine.GameKeyboard;
import org.johnnei.ld26.engine.render.Renderable;
import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.engine.render.VertexHelper;
import org.johnnei.ld26.miniharvest.item.Item;
import org.lwjgl.input.Keyboard;

public class Inventory extends Renderable {
	
	private final int MOVE_DOWN_INTERVAL = 5000;
	
	private ArrayList<Item> items;
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
	public void addItem(Item item) {
		for(Item item2 : items) {
			if(item2.getId() == item.getId()) {
				item2.addToAmount(item.getAmount());
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
		lastActionAge += deltaMs;
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.onTick(deltaMs);
			if(item.getAmount() == 0) {
				items.remove(i);
				if(i == selectedItem) {
					selectedItem--;
				}
				i--;
			}
		}
		if(GameKeyboard.getInstance().isKeyPressed(Keyboard.KEY_LEFT)) {
			lastActionAge = 0;
			selectedItem--;
			if(selectedItem < 0) {
				selectedItem = items.size() - 1;
			}
		} else if(GameKeyboard.getInstance().isKeyPressed(Keyboard.KEY_RIGHT)) {
			selectedItem = (selectedItem + 1) % items.size();
			lastActionAge = 0;
		}
	}
	
	private void onRenderTick() {
		float moveY = 0.05f * (lastActionAge - MOVE_DOWN_INTERVAL);
		if(moveY < 0)
			moveY = 0;
		y = 568 + moveY;
		renderObject.updateVertex(new VertexHelper(304, y, 192, 32));
		int barIndex = 0;
		for(int i = -2; i < 2; i++) {
			Item item = items.get(getIndex(selectedItem - i));
			item.setLocation(320 + (barIndex * 32), y, 32, 32);
			barIndex++;
		}
	}
	
	private int getIndex(int i) {
		return Math.abs(i % items.size());
	}
	
	@Override
	public void render() {
		super.render();
		for(Item item : items) {
			item.render();
		}
		if(items.size() > 0) {
			Item item = items.get(selectedItem);
			if(item.getAmount() > 1) {
				TextRender.getInstance().drawCentered(400, y - 20, item.getAmount() + " " + item.getName(), null);
			} else {
				TextRender.getInstance().drawCentered(400, y - 20, item.getName(), null);
			}
		}
	}
	
	@Override
	public boolean canDelete() {
		return false;
	}

	public Item getSelectedItem() {
		if(items.size() == 0) {
			return null;
		} else {
			return items.get(selectedItem);
		}
	}

}
