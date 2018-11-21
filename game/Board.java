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
	Tile[] tiles;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for Board objects that derives the contents of its Tile[] from the provided
	 * information in the format of individual String objects.
	 * 
	 * @param boardDesign - String[] object describing 
	 */
	
	public Board(String[] boardDesign) {
		tiles = new Tile[Integer.parseInt(boardDesign[0].split(" ")[0])];
		for(int i = 1; i < boardDesign.length; i++)
			tiles[i - 1] = interpretInput(boardDesign[i]);
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * 
	 * 
	 * @param mover
	 * @return
	 */
	
	public boolean[] getLegalMovement(MrJackCharacter mover, int[] characterLocations) {
		boolean[] reachable = new boolean[tiles.length];
		
		if(mover == null)
			return reachable;
		
		HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
		LinkedList<Tile> queue = new LinkedList<Tile>();
		int maxDist = mover.getDistance();
		queue.add(tiles[mover.getLocation()]);
		dist.put(mover.getLocation(), 0);
		reachable[mover.getLocation()] = false;

		while(!queue.isEmpty()) {
			Tile top = queue.poll();
			
			for(int index : top.getNeighbors()) {
				if(index == -1)
					continue;
				if(dist.get(index) == null && mover.canMove(tiles[index], dist.get(top.getLocation()) + 1)) {
					dist.put(index, dist.get(top.getLocation()) + 1);
					queue.add(tiles[index]);
					reachable[index] = dist.get(index) <= maxDist && tiles[index].canShare();
					for(int i : characterLocations) {
						if(index == i)
							reachable[index] = false;
					}
				}
			}	
		}
		
		return reachable;
	}
	
	/**
	 * 
	 * Format: [index #] [identity char] [neighbors # # # # # #] [optional: state]
	 * 
	 * @return
	 */
	
	public String convertToOutboundFormat() {
		String out = tiles.length + " " + tiles[0].getNeighbors().length + " " + "\n";
		int loc = 0;
		for(Tile t : tiles) {
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
	
	public Tile getTile(int index) {
		return tiles[index];
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * 
	 * @param key
	 * @return
	 */
	
	public Tile[] getTiles(char key) {
		ArrayList<Tile> matchTiles = new ArrayList<Tile>();
		for(Tile t : tiles) {
			if(t.getIdentity() == key) {
				matchTiles.add(t);
			}
		}
		Tile[] out = new Tile[matchTiles.size()];
		for(int i = 0; i < matchTiles.size(); i++) {
			out[i] = matchTiles.get(i);
		}
		
		return out;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	
	public char getTileIdentity(int index) {
		return tiles[index].getIdentity();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	
	public boolean[] getLitTiles(int[] charLoc) {
		boolean[] whoIsLit = new boolean[tiles.length];
		
		for(Tile t : getTiles('l')) {
			if(((Lantern)t).getLight()) {
				for(int i : t.getNeighbors()) {
					if(i != -1)
						whoIsLit[i] = true;
				}
			}
		}
		
		for(int loc : charLoc) {
			for(int adj : tiles[loc].getNeighbors()) {
				for(int locTwo : charLoc) {
					if(locTwo == adj) {
						whoIsLit[loc] = true;
						whoIsLit[locTwo] = true;
					}
				}
			}
			
		}
		
		return whoIsLit;
	}

	/**
	 * 
	 * @param indexes
	 * @return
	 */
	
	public Tile[] getTiles(int[] indexes) {
		Tile[] out = new Tile[indexes.length];
		for(int i = 0; i < indexes.length; i++) {
			out[i] = tiles[indexes[i]];
		}
		return out;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public int getNumberOfTiles() {
		return tiles.length;
	}
	
//---  Helper Methods   ---------------------------------------------------------------------------
	
	/**
	 * 
	 * Input Format: [#] [char] [# # # # # #]
	 * 
	 * @param in
	 * @return
	 */
	
	private Tile interpretInput(String in) {
		String[] detes = in.split(" ");
		
		int[] neighbors = new int[detes.length - 2];
		for(int i = 2; i < detes.length; i++) {
			neighbors[i-2] = Integer.parseInt(detes[i]);
		}
		
		switch(detes[1]) {
			case "l":
				Lantern lant = new Lantern(Integer.parseInt(detes[0]));
				lant.assignNeighbors(neighbors);
				return lant;
			case "m":
				Manhole man = new Manhole(Integer.parseInt(detes[0]));
				man.assignNeighbors(neighbors);
				return man;
			case "e":
				Exit exi = new Exit(Integer.parseInt(detes[0]));
				exi.assignNeighbors(neighbors);
				return exi;
			case "b":
				Building build = new Building(Integer.parseInt(detes[0]));
				build.assignNeighbors(neighbors);
				return build;
			case "r":
				Road rd = new Road(Integer.parseInt(detes[0]));
				rd.assignNeighbors(neighbors);
				return rd;
			default:
				System.out.println("Invalid entry for this kind of Board object");
				return null;
		}
	}
	
}