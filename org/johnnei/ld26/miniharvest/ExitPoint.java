package org.johnnei.ld26.miniharvest;

/**
 * Class to hold data about the exit points on a given map
 * @author Johnnei
 *
 */
public class ExitPoint {
	
	private Point exitPoint;
	private Point entryPoint;
	private String mapName;
	
	public ExitPoint(int x, int y, int x2, int y2, String mapName) {
		exitPoint = new Point(x, y);
		entryPoint = new Point(x2, y2);
		this.mapName = mapName;
	}
	
	public Point getExitPoint() {
		return exitPoint;
	}
	
	public Point getEntryPoint() {
		return entryPoint;
	}
	
	public String getMapName() {
		return mapName;
	}

}
