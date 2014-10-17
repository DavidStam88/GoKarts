package kart;

import goKarts.GoKarts;

import java.util.ArrayList;
import java.util.List;

import lapTime.LapTime;


import baan.Baan;
import baanPunten.BaanChecks;

import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.objects.collisions.TileCollision;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 
 * @author David & Ismay Deze klasse is een uitbreiding op de superklasse kart.
 *         Met de klasse kan men een speler toevoegen.
 * 
 */
public class KartSpeler extends Kart {

	/**
	 * De constructor voor de KartSpeler.
	 * 
	 * @param gefinished
	 *            de ArrayList rondeTijden bevat rondetijden van de klasse
	 *            LapTime. Verder wordt er in de constructor een sprite geset,
	 *            een wrijving, een snelheid en een richting.
	 **/
	public KartSpeler(ArrayList<BaanChecks> punten, String kleur, Baan baan,
			GoKarts goKarts) {
		super(punten, kleur, baan, goKarts);
		setSprite("kartr", 4);
		setFriction(0.05);
		setSpeed(0);
		setDirection(0);
		TouchInput.use = false;
		OnScreenButtons.use = true;
		rondeTijden = new ArrayList<LapTime>();
	}

	/**
	 * @author David Deze methode voegt een LapTime toe aan de ArrayList met
	 *         rondeTijden.
	 */
	@Override
	public void voegRondeToe() {
		rondeTijden.add(new LapTime());
		rondeTijden.get(ronde).startTimer();
	}

	/**
	 * @author David Deze methode stopt de timer van de klasse LapTime.
	 */
	@Override
	public void stopTimer() {
		rondeTijden.get(ronde).stopTimer();
	}

	@Override
	public void update() {
		super.update();
		if (goKarts.getStartRace()) {
			checkVoorBotsing();
			checkKnopIngedrukt();
		}
	}

	/**
	 * @author David Deze methode kijkt op welke knop men drukt en welke actie
	 *         er dan uitgevoerd moet worden door middel van het verwijzen naar
	 *         andere methodes.
	 */
	public void checkKnopIngedrukt() {
		if (OnScreenButtons.dPadUp) {
			setDirection(0);
			positie();
			setSnelheid();
		}
		if (OnScreenButtons.dPadDown) {
			setDirection(180);
			positie();
			setSnelheid();
		}
		if (OnScreenButtons.dPadRight) {
			setDirection(90);
			positie();
			setSnelheid();
		}
		if (OnScreenButtons.dPadLeft) {
			setDirection(270);
			positie();
			setSnelheid();
		}
	}

	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) {

	}

	/**
	 * @author David Deze geeft de scores boven in beeld tijdens de race weer.
	 */
	protected void geefScoresWeer(Canvas canvas, Paint text) {
		if (goKarts.getStartRace()) {
			canvas.drawText(
					"Ronde: " + this.ronde + "/" + baan.getAantalRondes(), 20,
					20, text);
			canvas.drawText("Laptime: " + rondeTijden.get(ronde).toString(),
					600, 20, text);
			canvas.drawText("Positie na ronde " + this.ronde + ":"
					+ this.positie, 150, 20, text);
		} else {
			canvas.drawText("De race start bij 0: " + goKarts.afteller(), 300,
					20, text);
		}
	}

	/**
	 * @author David & Ismay Deze methode tekent het zwarte vlak achter de
	 *         scores boven in beeld tijdens de race.
	 */
	protected void tekenRechthoek(Canvas canvas, Paint rect) {
		canvas.drawRect(0, 0, 891, 30, rect);
	}

	/**
	 * @author David Deze methode geeft het eindresultaat kort weer als de race
	 *         net afgelopen is.
	 */
	protected void geefEindresultaatWeer(Canvas canvas, Paint text) {
		canvas.drawText("Je bent gefinished als: " + this.positie + "e", 200,
				20, text);
		canvas.drawText("Je snelste ronde was: "
				+ berekenSnelsteRonde().getEindTijd() / 100, 500, 20, text);
	}

	public void drawGameObject(Canvas canvas) {
		super.drawGameObject(canvas);
		Paint text = new Paint();
		text.setColor(Color.WHITE);
		text.setTextSize(16);
		Paint rect = new Paint();
		rect.setColor(Color.BLACK);
		tekenRechthoek(canvas, rect);
		if (checkOfGefinished()) {
			geefEindresultaatWeer(canvas, text);
			goKarts.toonScorebord(positie,
					berekenSnelsteRonde().getEindTijd() / 100);
		} else if (!checkOfGefinished()) {
			geefScoresWeer(canvas, text);
		}
	}

	/**
	 * @return een ArrayList met de gegevens van de rondetijden.
	 */
	public ArrayList<LapTime> getRondeTijden() {
		return this.rondeTijden;
	}

	/**
	 * @author David Deze methode berekend wat de snelst gereden ronde van de
	 *         speler was na de race.
	 * @return snelste rondetijd.
	 */
	public LapTime berekenSnelsteRonde() {
		float snelsteRonde = rondeTijden.get(0).getEindTijd();
		LapTime snelsteRondeTijd = rondeTijden.get(0);
		for (int i = 1; i < baan.getAantalRondes(); i++) {
			if (rondeTijden.get(i).getEindTijd() < snelsteRonde) {
				snelsteRonde = rondeTijden.get(i).getEindTijd();
				snelsteRondeTijd = rondeTijden.get(i);
			}
		}
		return snelsteRondeTijd;
	}

}
