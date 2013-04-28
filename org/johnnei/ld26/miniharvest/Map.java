package org.johnnei.ld26.miniharvest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.johnnei.ld26.engine.sound.SoundManager;
import org.johnnei.ld26.miniharvest.entity.Entity;
import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.item.Item;
import org.johnnei.ld26.miniharvest.item.ItemPickupEntity;
import org.johnnei.ld26.miniharvest.item.ItemPlow;
import org.johnnei.ld26.miniharvest.item.ItemPotatoSeed;
import org.johnnei.ld26.miniharvest.item.ItemStack;
import org.johnnei.ld26.miniharvest.item.ItemWheatSeed;
import org.johnnei.ld26.miniharvest.shop.Shop;
import org.johnnei.ld26.miniharvest.shop.ShopItem;
import org.johnnei.ld26.miniharvest.world.Block;
import org.johnnei.ld26.miniharvest.world.BlockBanner;
import org.johnnei.ld26.miniharvest.world.BlockDirt;
import org.johnnei.ld26.miniharvest.world.BlockGrass;
import org.johnnei.ld26.miniharvest.world.BlockRoad;
import org.johnnei.ld26.miniharvest.world.BlockSellShop;
import org.johnnei.ld26.miniharvest.world.BlockShop;

public class Map {
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 38;
	/**
	 * All tiles in the map
	 */
	private Block[] mapData;
	/**
	 * All shop info on the map (Max 10 shops)
	 */
	private Shop[] shops; 
	private int shopIdx;
	/**
	 * If the player is allowed to spade on gras tiles
	 */
	private boolean canSpade;
	/**
	 * Points on the map which shift to another place
	 */
	private ArrayList<ExitPoint> exitPoints;
	private ArrayList<ItemPickupEntity> itemPickupEntities;
	
	public Map() {
		mapData = new Block[WIDTH * HEIGHT];
		exitPoints = new ArrayList<>();
		itemPickupEntities = new ArrayList<>();
		shops = new Shop[10];
		shopIdx = 0;
		canSpade = false;
	}
	
	public void load(String name) {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(Map.class.getResourceAsStream("/res/maps/" + name + ".mh")));
			
			//Load Map Tiles
			for(int y = 0; y < HEIGHT; y++) {
				String[] mapLine = input.readLine().split(" ");
				for(int x = 0; x < WIDTH; x++) {
					int spot = Integer.parseInt(mapLine[x]);
					//System.out.println(mapLine[x]);
					
					Block block = null;
					switch(spot) {
					case 0:
						block = new BlockGrass(this, x, y);
						break;
						
					case 1:
						block = new BlockDirt(this, x, y);
						break;
						
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						block = new BlockRoad(this, x, y, spot - 2);
						break;
						
					case 10:
					case 11:
						Shop shop = new Shop();
						shops[shopIdx++] = shop;
						block = new BlockShop(this, x, y, spot - 10, shop);
						break;
						
					case 14: //Wheat Seed Stall Banner
						//Custom Block Setting
						spawnHorizontalBanner(x, y, "wheat");
						break;
						
					case 15: //Potato Seed Stall Banner
						//Custom Block Setting
						spawnVerticalBanner(x, y, "potato");
						break;
						
					case 16: //
						spawnHorizontalBanner(x, y, "sell");
						break;
						
					case 17:
						block = new BlockSellShop(this, x, y);
						break;
						
					case 18: //Plow Stall
						spawnHorizontalBanner(x, y, "plow");
						break;
						
					case 19: //Town Banner
						spawnHorizontalBanner(x, y, "town");
						break;
					}
					
					if(block != null) {
						setBlock(x, y, block);
					}
				}
			}
			//Reset Index Pointers
			shopIdx = 0;
			//Load Extra Info about map
			String dataLine = null;
			while((dataLine = input.readLine()) != null) {
				String[] data = dataLine.split("\\|");
				String[] subData = data[1].split(",");
				switch(data[0].toLowerCase()) {
				case "exitpoint":
					int x = Integer.parseInt(subData[0]);
					int y = Integer.parseInt(subData[1]);
					int x2 = Integer.parseInt(subData[2]);
					int y2 = Integer.parseInt(subData[3]);
					String mapName = subData[4];
					exitPoints.add(new ExitPoint(x, y, x2, y2, mapName));
					break;
					
				case "shop":
					int itemId = Integer.parseInt(subData[0]);
					int cost = Integer.parseInt(subData[1]);
					int rechargeRate = Integer.parseInt(subData[2]);
					Item item = getShopItem(itemId);
					shops[shopIdx++].setShop(new ShopItem(new ItemStack(item, 0), cost), rechargeRate);
					break;
					
				case "canspade":
					canSpade = true;
					break;
					
					default:
						System.err.println("Unknown extra info: " + data[0].toLowerCase());
						for(String s : data) {
							System.err.println("data[]: " + s);
						}
						break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				if(input != null) {
					input.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	private void spawnHorizontalBanner(int x, int y, String bannerName) {
		setBlock(x - 2, y - 1, new BlockBanner(this, x - 2, y - 1, 0, bannerName));
		setBlock(x - 1, y - 1, new BlockBanner(this, x - 1, y - 1, 1, bannerName));
		setBlock(x    , y - 1, new BlockBanner(this, x    , y - 1, 2, bannerName));
		setBlock(x - 2, y    , new BlockBanner(this, x - 2, y    , 3, bannerName));
		setBlock(x - 1, y    , new BlockBanner(this, x - 1, y    , 4, bannerName));
		setBlock(x    , y    , new BlockBanner(this, x    , y    , 5, bannerName));
	}
	
	private void spawnVerticalBanner(int x, int y, String bannerName) {
		setBlock(x - 1, y - 2, new BlockBanner(this, x - 1, y - 2, 0, bannerName, true));
		setBlock(x - 1, y - 1, new BlockBanner(this, x - 1, y - 1, 1, bannerName, true));
		setBlock(x - 1, y    , new BlockBanner(this, x - 1, y    , 2, bannerName, true));
		setBlock(x    , y - 2, new BlockBanner(this, x    , y - 2, 3, bannerName, true));
		setBlock(x    , y - 1, new BlockBanner(this, x    , y - 1, 4, bannerName, true));
		setBlock(x    , y    , new BlockBanner(this, x    , y    , 5, bannerName, true));
	}
	
	private Item getShopItem(int id) {
		switch(id) {
		
		case ItemWheatSeed.ID:
			return new ItemWheatSeed();
			
		case ItemPotatoSeed.ID:
			return new ItemPotatoSeed();
			
		case ItemPlow.ID:
			return new ItemPlow();
			
			default:
				throw new IllegalArgumentException("Unsupported Shop Item");
		}
	}
	
	public void setBlock(int x, int y, Block block) {
		mapData[y * WIDTH + x] = block;
	}
	
	public boolean canSpade() {
		return canSpade;
	}
	
	public Block getBlock(int x, int y) {
		return mapData[y * WIDTH + x];
	}
	
	/**
	 * Warps the player to a different map if needed
	 * @param player The current player
	 */
	public void checkForExit(MiniHarvest world, Player player) {
		if(player.canWarp()) {
			for(ExitPoint exit : exitPoints) {
				Point exitPoint = exit.getExitPoint();
				if(player.getBlockX() == exitPoint.getX() && player.getBlockY() == exitPoint.getY()) {
					Point entryPoint = exit.getEntryPoint();
					world.switchMap(exit.getMapName());
					player.warp(entryPoint.getX() * 16, entryPoint.getY() * 16);
				}
			}
		}
	}
	
	public void addPickupEntity(ItemPickupEntity itemPickup) {
		itemPickupEntities.add(itemPickup);
	}
	
	public void onTick(int deltaMs, Player player) {
		for(Block block : mapData) {
			block.onTick(deltaMs);
		}
		for(int i = 0; i < itemPickupEntities.size(); i++) {
			//Normal onTick
			ItemPickupEntity entity = itemPickupEntities.get(i);
			entity.onTick(deltaMs);
			//Check Deletion
			if(entity.getLifetime() >= ItemPickupEntity.MAX_LIFETIME) {
				entity.onDelete();
				itemPickupEntities.remove(i);
				i--;
				continue;
			}
			//Check Pickup
			if(player.getPoint().getSquaredDistanceTo(entity.getPoint()) <= ItemPickupEntity.PICKUP_RADIUS) {
				entity.onDelete();
				itemPickupEntities.remove(i);
				i--;
				player.addItem(entity.getItem(), 1);
				SoundManager.getInstance().playSound(MiniHarvest.SOUND_ENTITY_PICKUP);
			}
		}
	}
	
	/**
	 * Map updates on which the player is not currently available
	 * @param deltaMs
	 */
	public void onQuickTick(int deltaMs) {
		for(Block block : mapData) {
			block.onTick(deltaMs);
		}
		for(int i = 0; i < itemPickupEntities.size(); i++) {
			//Normal onTick
			ItemPickupEntity entity = itemPickupEntities.get(i);
			entity.onTick(deltaMs);
			//Check Deletion
			if(entity.getLifetime() >= ItemPickupEntity.MAX_LIFETIME) {
				entity.onDelete();
				itemPickupEntities.remove(i);
				i--;
				continue;
			}
		}
	}
	
	public void render(Player player) {
		for(Block block : mapData) {
			block.render();
		}
		for(Entity entity : itemPickupEntities) {
			entity.render();
		}
		getBlock(player.getBlockX(), player.getBlockY()).renderOnPlayerStandOn(player);
	}

	public void onDelete() {
		for(Block block : mapData) {
			block.onDelete();
		}
		for(Entity entity : itemPickupEntities) {
			entity.onDelete();
		}
	}

}
