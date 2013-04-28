package org.johnnei.ld26.engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.johnnei.ld26.engine.render.TextRender;
import org.johnnei.ld26.engine.sound.SoundManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	
	private IGame game;
	
	/**
	 * Creates a new instance of the Game Engine
	 */
	public Game(IGame game) {
		this.game = game;
	}

	/**
	 * Runs the entire game until close will be requested or crashes
	 */
	public void run() {
		initOpenGL();
		initOpenAL();
		TextRender.getInstance();
		game.init();
		long lastTick = getCurrentMillis();
		long lastFps = getCurrentMillis();
		int fps = 0;

		while (!Display.isCloseRequested()) {

			// Game Logic Section
			if(getCurrentMillis() - lastTick > 0) {
				long deltaMs = getCurrentMillis() - lastTick;
				game.onTick((int)deltaMs);
				lastTick += deltaMs;
				GameMouse.getInstance().poll();
				GameKeyboard.getInstance().poll();
			}

			// Reset Screen
			glClear(GL_COLOR_BUFFER_BIT);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

			// Game Render Section
			game.render();

			// LWJGL Updating
			Display.update();
			Display.sync(60);

			++fps;
			if (getCurrentMillis() - lastFps >= 1000) {
				if(fps < 30) {
					System.out.println("FPS Drop! " + fps + "fps");
				}
				fps = 0;
				lastFps += 1000;
			}
		}
		
		cleanupGL();
		cleanupAL();
	}

	/**
	 * Gets the current time in millis by dividing the current nano time by
	 * 100'000
	 * 
	 * @return
	 */
	public static long getCurrentMillis() {
		return System.nanoTime() / 1000000;
	}

	public static void sleep(long ms) {
		System.out.println("Sleeping for " + ms + "ms");
		long timeSlept = System.currentTimeMillis();
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			timeSlept = System.currentTimeMillis() - timeSlept;
			if ((ms - timeSlept) > 0)
				sleep(ms - timeSlept);
		}
	}
	
	/**
	 * Prepare the sound library
	 */
	private void initOpenAL() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void cleanupGL() {		
		TextRender.getInstance().cleanup();
		game.cleanup();
		Display.destroy();
	}
	
	private void cleanupAL() {
		SoundManager.getInstance().cleanup();
		AL.destroy();
	}

	/**
	 * Initialising the display and OpenGL basic setup
	 */
	private void initOpenGL() {
		try {

			// Init Display
			/*int maxWidth = Math.min(WIDTH, Display.getDesktopDisplayMode().getWidth());
			int maxHeight = Math.min(HEIGHT, Display.getDesktopDisplayMode().getHeight());
			DisplayMode selectedMode = new DisplayMode(0, 0);
			DisplayMode[] mode = Display.getAvailableDisplayModes();
			for (DisplayMode m : mode) {
				if (m.getWidth() <= maxWidth && m.getHeight() <= maxHeight) {
					if (m.getWidth() > selectedMode.getWidth() && m.getHeight() > selectedMode.getHeight()) {
						selectedMode = m;
					}
				}
			}*/
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("MiniHarvest");
			// Display.setVSyncEnabled(true);
			Display.create();

			// Init OpenGL Frame
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
