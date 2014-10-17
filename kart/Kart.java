package kart;

import goKarts.GoKarts;

import java.util.ArrayList;
import java.util.List;

import lapTime.LapTime;
import munten.Munt;


import baan.Baan;
import baanPunten.BaanChecks;
import baanPunten.CheckPoint;
import baanPunten.Finish;

import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;
import android.gameengine.icadroids.tiles.Tile;

/**
 * 
 * @author David & Ismay Met deze abstracte (super)klasse kan men een kart
 *         maken.
 * 
 */
public abstract class Kart extends MoveableGameObject implements ICollision {

	protected boolean gefinished;
	protected int contactPunt;
	protected String kleur;
	protected Baan baan;
	protected int snelheid;
	protected int normaleSnelheid;
	protected int ronde;
	protected GoKarts goKarts;
	protected int positie;
	protected boolean[] checkpoints;
	protected boolean rijdtMetAangepasteSnelheid;
	ArrayList<LapTime> rondeTijden;

	/**
	 * De constructor voor de Kart.
	 * 
	 * @param gefinished
	 *            Deze boolean wordt geset als de kart gefinished is.
	 * @param contactPunt
	 *            Deze int weet hoeveel coordinaten de kart gepasseerd heeft.
	 * @param kleur
	 *            Deze string bepaald de kleur van de kart, deze wordt
	 *            meegegeven met de constructor.
	 * @param baan
	 *            Deze bevat dezelfde baan die ook de klasse GoKarts bevat.
	 * @param snelheid
	 *            Deze variabele bevat de snelheid van de kart.
	 * @param normaleSnelheid
	 *            Deze variabele bevat de snelheid van de kart onder normale
	 *            omstandigheden.
	 * @param ronde
	 *            Deze variabele weet hoeveel rondes er gereden zijn.
	 * @param goKarts
	 *            Deze bevat de klasse GoKarts.
	 * @param positie
	 *            Deze variabale onthoud de positie(hoeveelste de speler staat)
	 *            van de kart.
	 * @param checkpoints
	 *            Deze boolean array weet welke checkpoints er zijn aangedaan
	 *            tijdens een ronde.
	 * @param rijdtMetAangepasteSnelheid
	 *            Deze variabele bevat de aangepaste snelheid zodra er
	 *            bijvoorbeeld een munt gepakt is.
	 * @param rondeTijden
	 *            Deze ArrayList bevat de rondetijden van de kart.
	 * 
	 **/

	@Override
	public void update() {
		super.update();
		// goKarts.checkBotsingKarts(this);
	}
	
	public Kart(ArrayList<BaanChecks> punten, String kleur, Baan baan,
			GoKarts goKarts) {
		this.baan = baan;
		this.kleur = kleur;
		if (kleur == "rood") {
			setSprite("kartr", 4);
		}
		if (kleur == "groen") {
			setSprite("kartg", 4);
		}
		if (kleur == "paars") {
			setSprite("kartp", 4);
		}
		if (kleur == "blauw") {
			setSprite("kartb", 4);
		}
		if (kleur == "bruin") {
			setSprite("karth", 4);
		}
		if (kleur == "oranje") {
			setSprite("karto", 4);
		}
		ronde = 0;
		gefinished = false;
		snelheid = 8;
		normaleSnelheid = snelheid;
		contactPunt = 0;
		this.goKarts = goKarts;
		positie = 1;
		this.checkpoints = new boolean[baan.getCheckpoints().size()];
	}

	/**
	 * @author David Deze methode berekend op welke nummer tegel de kart op het
	 *         moment rijdt.
	 */
	protected int checkOpWelkeTegel() {
		Tile huidig = baan.getTegelNummer(this.getX() + 20, this.getY() + 20);
		int tegelNummer = huidig.getTileType();
		return tegelNummer;
	}

	/**
	 * @author David Deze methode set de variabele snelheid.
	 */
	protected void setSnelheid() {
		if (!checkOfGefinished()) {

			if (checkOpWelkeTegel() == 0) {
				snelheid = 2;
			} else if (rijdtMetAangepasteSnelheid == false) {
				snelheid = normaleSnelheid;

			}
			setSpeed(snelheid);
		} else if (checkOfGefinished()
				&& this.getX() < baan.getFinish().getPositieX() - 100) {
			setSpeed(0);
		}
	}

	/**
	 * @author David Deze methode berekend of de kart botst met een ander
	 *         GameObject.
	 */
	public void checkVoorBotsing() {
		ArrayList<GameObject> gebotst = getCollidedObjects();
		if (gebotst != null) {
			for (GameObject g : gebotst) {
				if (g instanceof Finish) {
					checkpoints[((BaanChecks) g).getCheckpointNummer()] = true;
					checkRondePlus();
				} else if (g instanceof CheckPoint) {
					checkpoints[((BaanChecks) g).getCheckpointNummer()] = true;
				} else if (g instanceof Munt && this instanceof KartSpeler) {
					((Munt) g).voerActieMuntUit();
				}
			}
		}
	}

	/**
	 * @author David Deze methode bekijkt od=f de speler alle checkpoint geraakt
	 *         heeft en dus mag finishen.
	 */

	public void checkRondePlus() {
		int aantalCheckpointsGehaald = 0;
		for (int i = 0; i < baan.getCheckpoints().size(); i++) {
			if (this.checkpoints[i]) {
				aantalCheckpointsGehaald++;
			}

		}
		if (aantalCheckpointsGehaald == baan.getCheckpoints().size()) {
			stopTimer();
			ronde++;
			voegRondeToe();
			this.positie = goKarts.berekenPositie();
			for (int i = 0; i < baan.getCheckpoints().size(); i++) {
				checkpoints[i] = false;

			}
		}
	}

	/**
	 * @author David Deze methode berekend de positie van de kart ten opzicht
	 *         van de andere karts.
	 */
	public void positie() {
		double direction = this.getDirection();
		if (direction >= 0 && direction < 45 || direction > 315
				&& direction < 360) {
			setFrameNumber(2);
		}
		if (direction > 45 && direction < 135) {
			setFrameNumber(3);
		}
		if (direction > 135 && direction < 225) {
			setFrameNumber(1);
		}
		if (direction > 225 && direction < 315) {
			setFrameNumber(0);
		}
	}

	/**
	 * @author David
	 * @return de waarde van contactPunt
	 */
	public int getContactPunt() {
		return this.contactPunt;
	}

	/**
	 * @author David
	 * @return Hoeveel rondes de kart gereden heeft.
	 */
	public int getRonde() {
		return this.ronde;
	}

	/**
	 * @author David Deze methode gaat na of de kart alle rondes gereden heeft.
	 */
	public boolean checkOfGefinished() {
		if (ronde == baan.getAantalRondes()) {
			return true;
		}
		return false;
	}

	/**
	 * @author David Deze methode voegt een LapTime toe aan de ArrayList met
	 *         rondeTijden. Deze is voor de klasse kart abstract.
	 */
	public abstract void voegRondeToe();

	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) {
		// TODO Auto-generated method stub

	}

	/**
	 * @author David
	 * @return de kleur van de kart.
	 */
	public String getKleur() {
		return this.kleur;
	}

	/**
	 * @return een ArrayList met de gegevens van de rondetijden. Voor deze
	 *         klasse is deze methode abstract.
	 */
	public abstract ArrayList<LapTime> getRondeTijden();

	/**
	 * @author David Deze methode stopt de timer van de LapTime van de kart
	 *         wanneer deze een ronde gehaald heeft. Voor deze klasse is deze
	 *         methode abstract.
	 */
	public abstract void stopTimer();

	/**
	 * @author Ismay Deze methode zorgt ervoor dat zodra er een munt opgepakt
	 *         is, de snelheid wordt aangepast naar de regel die deze munt
	 *         heeft.
	 * @param snelheid
	 *            De snelheid die kart zal hebben nadat de munt op is gepakt.
	 */
	public void rijdtMetAangepasteSnelheid(int snelheid) {
		this.snelheid = snelheid;
		rijdtMetAangepasteSnelheid = true;
	}

	/**
	 * @author Ismay Deze methode zorgt ervoor dat nadat de ingestelde tijd
	 *         verstreken is wanneer de munt is opgepakt, de kart weer op
	 *         normale snelheid gaat rijden.
	 */
	public void rijdtNormaal() {
		rijdtMetAangepasteSnelheid = false;
	}
}
