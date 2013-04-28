package org.johnnei.ld26.engine.render;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

import java.util.HashMap;
import java.util.Map.Entry;

public class TextureManager {
	
	private static TextureManager instance = new TextureManager();
	
	public static TextureManager getInstance() {
		return instance;
	}
	
	private HashMap<String, TextureInfo> textureMap;
	
	private TextureManager() {
		textureMap = new HashMap<>();
	}
	
	public boolean contains(String s) {
		return textureMap.containsKey(s);
	}
	
	public void put(String s, int id, int width, int height) {
		textureMap.put(s, new TextureInfo(id, width, height));
	}
	
	public TextureInfo get(String s) {
		return textureMap.get(s);
	}

	public void remove(int glTextureId) {
		String removeTarget = null;
		for(Entry<String, TextureInfo> entry : textureMap.entrySet()) {
			TextureInfo info = entry.getValue();
			if(info.getId() == glTextureId) {
				info.decreaseCount();
				if(info.getCount() == 0) {
					glDeleteTextures(glTextureId);
					removeTarget = entry.getKey();
					break;
				}
			}
		}
		if(removeTarget != null) {
			textureMap.remove(removeTarget);
		}
	}

}
