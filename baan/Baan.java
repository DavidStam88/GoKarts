package baan;

import java.util.ArrayList;

import baanPunten.BaanChecks;
import baanPunten.CheckPoint;
import baanPunten.Coordinate;
import baanPunten.Finish;

import android.gameengine.icadroids.tiles.GameTiles;
import android.gameengine.icadroids.tiles.Tile;

/**
 * 
 * @author David Met deze klasse wordt het uiterlijk, de coordinaten voor de
 *         computergestuurde karts, de plekken van de checkpoints en de plek van
 *         de finish bepaald.
 * 
 */
public class Baan {
	private int aantalRondes;
	private ArrayList<BaanChecks> checkpoints = new ArrayList<BaanChecks>();
	private GameTiles tegelMap;
	private ArrayList<BaanChecks> punten = new ArrayList<BaanChecks>();
	private String[] tegelNamen = { "gras", "rechtboven", "rechtonder",
			"rechtsboven", "linksboven", "rechtsonder", "linksonder", "linksb",
			"rechtsb" };
	int[][] tegels = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 4, 1, 3, 0, 4, 3, 0 }, { 0, 0, 7, 0, 6, 2, 5, 8, 0 },
			{ 0, 0, 7, 0, 0, 0, 0, 8, 0 }, { 0, 0, 6, 2, 2, 2, 2, 5, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	/**
	 * De constructor voor de Baan.
	 * 
	 * @param aantalRondes
	 *            Deze variabele bepaald het aantal rondes dat de karts moeten
	 *            rijden.
	 * @param checkpoints
	 *            Deze ArrayList bevat alle checkpoints in het spel, dus ook de
	 *            finish.
	 * @param tegelMap
	 *            Deze GameTiles bevat de namen en de nummers van de tegels voor
	 *            de baan.
	 * @param punten
	 *            Deze ArrayList bevat de coordinaten die de computergestuurde
	 *            karts dienen aan te doen.
	 * @param tegelNamen
	 *            Deze string array bevat de namen van de tegels voor de baan.
	 * @param tegels
	 *            Deze int array bevat de waardes van de tegels.
	 * 
	 **/

	public Baan() {
		punten.add(new Coordinate(250, 450));
		punten.add(new Coordinate(250, 150));
		punten.add(new Coordinate(450, 150));
		punten.add(new Coordinate(450, 250));
		punten.add(new Coordinate(650, 250));
		punten.add(new Coordinate(650, 150));
		punten.add(new Coordinate(750, 150));
		punten.add(new Coordinate(750, 450));
		punten.add(new Coordinate(400, 450));

		checkpoints.add(new CheckPoint(288, 98, 0));
		checkpoints.add(new CheckPoint(685, 98, 1));
		checkpoints.add(new Finish(445, 396, 2));
		this.aantalRondes = 1;
		this.tegelMap = new GameTiles(this.tegelNamen, this.tegels, 99);
	}

	/**
	 * Geeft de de tegelMap die het uiterlijk van de baan bevat terug.
	 * 
	 * @return de tegelMap.
	 */

	public GameTiles getTegelMap() {
		return this.tegelMap;
	}

	/**
	 * Geeft de coordinaten van de baan terug.
	 * 
	 * @return De coordinaten van de baan.
	 */
	public ArrayList<BaanChecks> getPunten() {
		return punten;
	}

	/**
	 * @return Het huidige nummer van de tegel waar de kart zich op bevindt.
	 */
	public Tile getTegelNummer(int x, int y) {
		Tile huidig = tegelMap.getTileOnPosition(x, y);
		return huidig;
	}

	/**
	 * 
	 * Deze methode returned de finish welke in de ArrayList checkpoints zit.
	 * Dus moet er eerst uitgevonden worden welke de finish is.
	 * 
	 * @return De finish van de baan.
	 * 
	 */
	public BaanChecks getFinish() {
		for (BaanChecks b : checkpoints) {
			if (b instanceof Finish) {
				return b;
			}
		}
		return null;
	}

	/**
	 * @return Alle checkpoints van de baan, ook de finish.
	 */
	public ArrayList<BaanChecks> getCheckpoints() {
		return this.checkpoints;
	}

	/**
	 * @return Het aantal rondes van die gereden moeten worden op dit parcours.
	 */
	public int getAantalRondes() {
		return this.aantalRondes;
	}

	/**
	 * @return De waardes van de tegels van de baan.
	 */
	public int[][] getTegels() {
		return this.tegels;
	}
}
