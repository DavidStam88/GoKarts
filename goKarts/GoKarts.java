package goKarts;

import java.util.ArrayList;

import schermen.Scherm;

import munten.Munt;
import munten.MuntController;

import kart.Kart;
import kart.KartComp;
import kart.KartSpeler;

import baan.Baan;
import baanPunten.BaanChecks;
import baanPunten.CheckPoint;

import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.objects.GameObject;

/**
 * 
 * @author David, Ismay Dit is de kern van het spel. Zodra het spel wordt
 *         uitgevoerd zorgt deze klasse ervoor dat de objecten worden aangemaakt
 *         en geplaatst in de wereld.
 * 
 */
public class GoKarts extends GameEngine {

	private boolean startRace;
	private Baan baan;
	private ArrayList<Kart> karts;
	private Scherm scherm;
	private MuntController controller;
	private int afteller;

	/**
	 * De constructor voor GoKarts.
	 * 
	 * @param baan
	 *            Deze klasse bevat een baan van het type baan.
	 * @param karts
	 *            Deze arraylist bevat alle kartjes uit het spel.
	 * @param scherm
	 *            Deze klasse bevat een scherm van het type scherm voor het
	 *            tonen van een beeld voor het starten en het eindigen van het
	 *            spel.
	 * @param controller
	 *            Deze klasse bevat een controller voor het tonen en verwijderen
	 *            van de muntjes op de baan.
	 * @param afteller
	 *            Deze integer zorgt ervoor dat de race niet gelijk begint maar
	 *            pas na 50 loops.
	 * @param startRace
	 *            Deze boolean bepaald of de race mag starten bij true.
	 * 
	 */

	public GoKarts() {
		scherm = new Scherm("begin", this, true);
		controller = new MuntController(this);
		karts = new ArrayList<Kart>();
		startRace = false;
		afteller = -1; // Dit omdat de standaardwaarde 0 is en daarmee de race
						// gelijk start.
	}

	@Override
	public void update() {
		super.update();
		aftellenVoorBeginRace();
	}

	@Override
	protected void initialize() {
		super.initialize();
		addGameObject(scherm, 0, 0);
	}

	/**
	 * @author David, Ismay Als de speler het spel start, dan wordt deze methode
	 *         aangeroepen. Deze maakt de wereld en de speler/AI-spelers.
	 */
	public void startSpel() {
		maakBaan();
		bepaalKarts();
		voegFinishToe();
		voegKartsToe();
		voegCheckpointsToe();
		controller.setGepauzeerd(false);
		getKartSpeler().voegRondeToe();
		startRace = false;
		afteller = 50;
	}

	/**
	 * @author Ismay Deze methode verwijderd alle karts, munten en checkpoints
	 *         wanneer de speler gefinisht is.
	 */
	public void stopSpel() {
		for (GameObject object : items) {
			if (object instanceof Kart || object instanceof Munt
					|| object instanceof BaanChecks) {
				deleteGameObject(object);
			}
		}

		karts.clear();
		controller.setGepauzeerd(true);
	}

	/**
	 * @author David Deze methode berekend de positie (hoeveelste de speler
	 *         staat) van de kart van de speler ten opzichte van die van de
	 *         andere karts. Deze methode wordt na de botsing met de finish
	 *         aangeroepen.
	 */

	public int berekenPositie() {
		int positie = 1;
		for (Kart k : karts) {
			if (k instanceof KartComp) {
				int rondeK = k.getRonde();
				if (rondeK >= getKartSpeler().getRonde()) {
					positie++;
				}
			}
		}
		return positie;
	}

	/**
	 * @author David Deze methode maakt het parcours en de omgeving van de baan
	 *         met behulp van de door de GameEngine aangeboden tilesmethode.
	 */
	public void maakBaan() {
		baan = new Baan();
		setTileMap(baan.getTegelMap());
	}

	/**
	 * @author David Deze methode "maakt" de karts voor het spelen van het spel.
	 *         Hij bepaald wie de speler is en wie de computergestuurde karts
	 *         zijn.
	 */
	public void bepaalKarts() {
		karts.add(new KartSpeler(baan.getPunten(), "rood", baan, this));
		karts.add(new KartComp(baan.getPunten(), "oranje", baan, this));
		karts.add(new KartComp(baan.getPunten(), "blauw", baan, this));
		karts.add(new KartComp(baan.getPunten(), "paars", baan, this));
		karts.add(new KartComp(baan.getPunten(), "groen", baan, this));
		karts.add(new KartComp(baan.getPunten(), "bruin", baan, this));
	}

	/**
	 * @author David Deze methode voegt de karts uit de ArrayList karts toe aan
	 *         het speelveld.
	 */
	public void voegKartsToe() {
		for (int i = 0; i < karts.size() / 2; i++) {
			addGameObject(karts.get(i), (baan.getFinish().getPositieX() + 26)
					+ (45 * i), (baan.getFinish().getPositieY() + 15));
		}
		int x = 0;
		for (int i = (karts.size() / 2); i < karts.size(); i++) {
			addGameObject(karts.get(i), (baan.getFinish().getPositieX() + 26)
					+ (45 * x), (baan.getFinish().getPositieY() + 50));
			x++;
		}

	}

	/**
	 * @author David Deze methode voegt de finish toe aan het speelveld.
	 */
	public void voegFinishToe() {
		addGameObject(baan.getFinish(), baan.getFinish().getPositieX(), baan
				.getFinish().getPositieY());
	}

	/**
	 * @author David Deze methode voegt de checkpoints toe aan het speelveld.
	 */
	public void voegCheckpointsToe() {
		for (BaanChecks b : baan.getCheckpoints()) {
			if (b instanceof CheckPoint) {
				addGameObject(b, b.getPositieX(), b.getPositieY());
			}
		}
	}

	/**
	 * @author David Deze methode berekend of de karts elkaar in de weg zitten
	 *         (dus botsen) en verteld wat er dan moet gebeuren. Deze methode is
	 *         vanwege tijdgebrek nog niet helemaal in orde. Zo berekend en
	 *         verteld het nog niet wat er moet gebeuren als de zijkanten van de
	 *         karts elkaar raken.
	 */
	public void checkBotsingKarts(Kart kart) {
		for (int i = 0; i < karts.size(); i++) {
			if (kart.getKleur() != karts.get(i).getKleur()) {
				if (kart.getDirection() > 225 && kart.getDirection() <= 315) {
					if (kart.getY() + 20 > this.karts.get(i).getY()
							&& kart.getY() + 20 < this.karts.get(i).getY() + 45
							&& kart.getX() < this.karts.get(i).getX() + 40
							&& kart.getX() > this.karts.get(i).getX()) {
						kart.setSpeed(0);
						this.karts.get(i).setX(this.karts.get(i).getX() - 15);
					}

				}

				if (kart.getDirection() > 45 && kart.getDirection() <= 135) {
					if ((kart.getY() + 20) > this.karts.get(i).getY()
							&& (kart.getY() + 20) < this.karts.get(i).getY() + 45
							&& kart.getX() + 40 > this.karts.get(i).getX()
							&& kart.getX() + 40 < this.karts.get(i).getX() + 40) {
						kart.setSpeed(0);
						this.karts.get(i).setX(this.karts.get(i).getX() + 10);
					}
				}

				if (kart.getDirection() > 315 && kart.getDirection() <= 0
						|| kart.getDirection() >= 0 && kart.getDirection() < 45) {
					if (kart.getX() + 20 > this.karts.get(i).getX()
							&& kart.getX() + 20 < this.karts.get(i).getX() + 40
							&& kart.getY() < this.karts.get(i).getY() + 45
							&& kart.getY() > this.karts.get(i).getY()) {
						kart.setSpeed(0);
						this.karts.get(i).setY(this.karts.get(i).getY() - 10);
					}

				}

				if (kart.getDirection() > 135 && kart.getDirection() <= 225) {
					if (kart.getX() + 20 > this.karts.get(i).getX()
							&& kart.getX() + 20 < this.karts.get(i).getX() + 40
							&& kart.getY() < this.karts.get(i).getY() + 45
							&& kart.getY() > this.karts.get(i).getY()) {
						kart.setSpeed(0);
						this.karts.get(i).setY(this.karts.get(i).getY() + 15);
					}

				}

			}
		}

	}

	/**
	 * @author David Deze methode returned de karts uit de ArrayList karts.
	 */
	public ArrayList<Kart> getKarts() {
		return karts;
	}

	/**
	 * @author David Deze methode returned de aangemaakte baan die deze klasse
	 *         bevat.
	 */
	public Baan getBaan() {
		return baan;
	}

	/**
	 * @author David Deze methode returned de kart van de speler.
	 */
	public Kart getKartSpeler() {
		for (Kart k : karts) {
			if (k instanceof KartSpeler) {
				return k;
			}

		}
		return null;
	}

	/**
	 * @author Ismay Deze methode toont het scorebord op het scherm, met daarop
	 *         de melding of de speler naar het volgende level mag of niet.
	 * @param positie
	 *            De positie waarop de speler gefinisht is. Deze positie bepaald
	 *            of de speler door mag naar het volgende level.
	 * @param snelsteRonde
	 *            Dit is de snelste rondetijd in seconden die de KartSpeler
	 *            heeft gereden in het afgelopen level.
	 */
	public void toonScorebord(int positie, float snelsteRonde) {
		scherm.setSnelsteRonde(snelsteRonde);
		scherm.setKartPositie(positie);
		scherm.setType("eind");
	}

	/**
	 * @author David Deze methode bepaald wanneer de race mag beginnen.
	 */
	public void aftellenVoorBeginRace() {
		if (afteller > -1) {
			afteller--;
		}
		if (afteller == 0) {
			startRace = true;
			getKartSpeler().getRondeTijden().get(0).startTimer();
		}
	}

	/**
	 * @return de waarde van de afteller.
	 */
	public int afteller() {
		return afteller;
	}

	/**
	 * @return de boolean van startRace en of de race dus mag beginnen.
	 */
	public boolean getStartRace() {
		return startRace;
	}
}