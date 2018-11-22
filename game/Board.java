package game;

import tile.Tile;
import java.util.ArrayList;
import java.util.LinkedList;
import character.MrJackCharacter;
import java.util.HashMap;
import tile.Lantern;
import tile.Manhole;
import tile.Exit;
import tile.Building;
import tile.Road;

/**
 * This class models the Board that the Players manipulate for the Mr. Jack game.
 * 
 * TODO: Should really be an interface between this and GameModel
 * TODO: Cause then new kinds of tiles and interpreting them would be a new Board, cause that is a significant enough change 
 * 
 * @author Mac Clevinger and Sarah MacEwan
 *
 */

public class Board {
	
//---  Constant Values   ----------------------------------------------------------------------
	
	private static final char[] IDENTITIES = {'b', 'e', 'l', 'm', 'r'};
	
//---  Instance Variables   -------------------------------------------------------------------

	/** Tile[] object containing a list of Tile objects representing the Mr. Jack game's board*/
	Tile[] mapTiles;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/** Constructor for Board objects that derives the contents of its Tile[] from the provided
	 * information in the format of individual String objects.
	 * 
	 * @param boardDesign - String[] object describing 
	 */
	
	public Board(String[] boardDesign) {
		mapTiles = new Tile[Integer.parseInt(boardDesign[0])];
		for(int i = 1; i < boardDesign.length; i++) {
			mapTiles[i - 1] = initializeTile(boardDesign[i]);
			mapTiles[i-1].setBarricade(-1);
		}

		//Relate Manholes to each other
		Tile[] manholes = getTilesOfType('m');
		for(Tile t : manholes) {
			((Manhole)t).assignManholeNeighbors(manholes);
		}
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * 
	 * 
	 * @param activeCharacter
	 * @return
	 */
	
	//-------related to character------
	public boolean[] getLegalMovements(MrJackCharacter activeCharacter, int[] characterLocations) {
		boolean[] validDestinationTiles = new boolean[mapTiles.length];
		
		if(activeCharacter == null)
			return validDestinationTiles;
		
		HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
		LinkedList<Tile> queue = new LinkedList<Tile>();
		int maxDist = activeCharacter.getDistance();
		
		int activeCharLocation=activeCharacter.getLocation();
		//
		queue.add(mapTiles[activeCharLocation]);
		dist.put(activeCharLocation, 0);
		validDestinationTiles[activeCharLocation] = false;
		
		

		while(!queue.isEmpty()) {
			Tile top = queue.poll();
			
			for(int index : top.getNeighbors()) {  //neighbours of the current tile 
				if(index == -1) 
					continue;
				if(dist.get(index) == null && activeCharacter.canMove(top, mapTiles[index], dist.get(top.getLocation()) + 1)) {

					dist.put(index, dist.get(top.getLocation()) + 1);
					queue.add(mapTiles[index]);
					validDestinationTiles[index] = dist.get(index) <= maxDist && mapTiles[index].canShare();

					for(int i : characterLocations) {
						if(index == i)
							validDestinationTiles[index] = false;
					}//for i
				}//if
			}//for index	
		}//while
		
		return validDestinationTiles;
	}//getLegalMovements
	
	/**
	 * Format: [index #] [identity char] [neighbors # # # # # #] [optional: state]
	 * 
	 * @return
	 */
	
	public String convertToOutboundFormat() {
		String out = mapTiles.length + " " + mapTiles[0].getNeighbors().length + " " + "\n";
		int loc = 0;
		for(Tile t : mapTiles) {
			out += (loc++) + " " + t.getIdentity();
			for(int i : t.getNeighbors()) {
				out += " " + i;
			}
			switch(t.getIdentity()) {
				case 'l': out += " " + ((Lantern)t).getLight(); break;
				case 'e': out += " " + ((Exit)t).getBlocked(); break;
				case 'm': out += " " + ((Manhole)t).getCovered(); break;
				default: break;
			}
			out += "\n";
		}
		return out;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	
	public Tile getTileAtLocation(int index) {
		return mapTiles[index];
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * This method returns an array containing all the tiles of the specified type.
	 * 
	 * @param tileType
	 * @return
	 */
	
	public Tile[] getTilesOfType(char tileType) {
		ArrayList<Tile> correctTiles = new ArrayList<Tile>();
		for(Tile t : mapTiles) {
			if(t.getIdentity() == tileType) 
				correctTiles.add(t);
		}
		return correctTiles.toArray(new Tile[correctTiles.size()]);
	}//getTilesOfType

	/**
	 * @param index
	 * @return
	 */
	
	public char getTileIdentity(int index) {
		return mapTiles[index].getIdentity();
	}

	/** 
	 * @return
	 */
	
	public boolean[] getLitTiles(int[] charLocations) {
		boolean[] witnessedChars = new boolean[mapTiles.length];
		
		for(Tile t : getTilesOfType('l')) {//checks which tiles are lit up
			if(((Lantern)t).getLight()) {
				for(int i : t.getNeighbors()) {
					if(i != -1)
						witnessedChars[i] = true;
				}
			}
		}
		
		for(int charLoc : charLocations) {
			for(int adj : mapTiles[charLoc].getNeighbors()) {
				for(int neighbourCharLoc : charLocations) {
					if(neighbourCharLoc == adj) {
						witnessedChars[charLoc] = true;
						witnessedChars[neighbourCharLoc] = true;
					}
				}
			}
			
		}
		
		
		return witnessedChars;
	}

	/**
	 * @param indices
	 * @return
	 */
	
	public Tile[] getTiles(int[] indices) {  // does this get used?
		Tile[] out = new Tile[indices.length];
		for(int i = 0; i < indices.length; i++) {
			out[i] = mapTiles[indices[i]];
		}
		return out;
	}
	
	/** 
	 * @return
	 */
	
	public int getNumberOfTiles() {
		return mapTiles.length;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public int[] getBarricadeTiles() {
		int[] out = new int[2];
		out[0] = -1;
		out[1] = -1;
		for(Tile t : mapTiles) {
			if(t.getBarricade() != -1) {
				out[0] = t.getLocation();
				out[1] = t.getBarricade();
				break;
			}
		}
		return out;
	}
	
//---  Helper Methods   ---------------------------------------------------------------------------
	
	/**
	 * 
	 * Input Format: [#] [char] [# # # # # #]
	 * 
	 * @param newTileInfo
	 * @return
	 */
	
	private Tile initializeTile(String newTileInfo) {
		String[] tileSpecs = newTileInfo.split(" ");
		int[] neighbors = new int[tileSpecs.length - 2];
		for(int i = 2; i < tileSpecs.length; i++) {
			neighbors[i-2] = Integer.parseInt(tileSpecs[i]); //assigning neighbours to tile
		}
		
		switch(tileSpecs[1]) {//creating the appropriate tile type and setting its neighbours
			case "l":
				Lantern lant = new Lantern(Integer.parseInt(tileSpecs[0]));
				lant.assignNeighbors(neighbors);
				return lant;
			case "m":
				Manhole manhole = new Manhole(Integer.parseInt(tileSpecs[0]));
				manhole.assignNeighbors(neighbors);
				return manhole;
			case "e":
				Exit exit = new Exit(Integer.parseInt(tileSpecs[0]));
				exit.assignNeighbors(neighbors);
				return exit;
			case "b":
				Building build = new Building(Integer.parseInt(tileSpecs[0]));
				build.assignNeighbors(neighbors);
				return build;
			case "r":
				Road rd = new Road(Integer.parseInt(tileSpecs[0]));
				rd.assignNeighbors(neighbors);
				return rd;
			default:
				System.out.println("Invalid entry for this kind of Board object");
				return null;
		}
	}
	
}