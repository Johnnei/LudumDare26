package org.johnnei.ld26.miniharvest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.johnnei.ld26.miniharvest.entity.Player;
import org.johnnei.ld26.miniharvest.world.Block;
import org.johnnei.ld26.miniharvest.world.BlockDirt;
import org.johnnei.ld26.miniharvest.world.BlockGrass;
import org.johnnei.ld26.miniharvest.world.BlockRoad;

public class Map {
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 38;
	/**
	 * All tiles in the map
	 */
	private Block[] mapData;
	/**
	 * Points on the map which shift to another place
	 */
	private ArrayList<ExitPoint> exitPoints;
	
	public Map() {
		mapData = new Block[WIDTH * HEIGHT];
		exitPoints = new ArrayList<>();
	}
	
	public void load(String name) {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader("bin/res/maps/" + name + ".mh"));
			
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
						block = new BlockRoad(this, x, y, spot - 2);
						break;
						
					}
					
					if(block != null) {
						setBlock(x, y, block);
					}
				}
			}
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
	
	public void setBlock(int x, int y, Block block) {
		mapData[y * WIDTH + x] = block;
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
	
	public void onTick(int deltaMs) {
		for(Block block : mapData) {
			block.onTick(deltaMs);
		}
	}
	
	public void render() {
		for(Block block : mapData) {
			block.render();
		}
	}

}
