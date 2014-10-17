package schermen;

import goKarts.GoKarts;
import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 
 * @author Ismay Deze klasse zorgt ervoor dat het begin- en eindscherm op de
 *         juiste manier wordt weergegeven (afhankelijk van de score van de
 *         speler.)
 */

public class Scherm extends MoveableGameObject implements IAlarm {
	private String type;
	private GoKarts game;
	private int kartPositie;
	private float snelsteRonde;
	private boolean tekenScherm;
	private boolean goToNextLevel;
	private Alarm alarmAlsSpelerGefinisht;

	/**
	 * De constructor voor het scherm.
	 * 
	 * @param type
	 *            Het type scherm (begin- of eindscherm)
	 * @param game
	 *            Het GoKarts spel
	 * @param tekenScherm
	 *            Of het scherm getekend moet worden
	 */
	public Scherm(String type, GoKarts game, boolean tekenScherm) {
		this.type = type;
		this.game = game;
		this.tekenScherm = tekenScherm;
		TouchInput.use = false;
		OnScreenButtons.use = true;
	}

	/**
	 * Tekent het scherm.
	 * 
	 * @param Canvas
	 *            waarop getekend wordt.
	 */
	public void drawGameObject(Canvas canvas) {
		super.drawGameObject(canvas);
		checkKnopIngedrukt();
		if (tekenScherm == true) {
			Paint text = new Paint();
			text.setColor(Color.WHITE);
			text.setTextSize(24);

			if (type == "begin") {
				tekenBeginscherm(canvas, text);
			} else if (type == "eind") {
				tekenEindscherm(canvas, text);
			}
		}
	}

	/**
	 * Slaat de eindpositie van de speler op. Ook wordt hier ingesteld of de
	 * speler door mag naar het volgende level.
	 * 
	 * @param positie
	 *            De eindpositie van de speler.
	 */
	public void setKartPositie(int positie) {
		kartPositie = positie;
		if (positie < 3) {
			goToNextLevel = true;
		} else {
			goToNextLevel = false;
		}
	}

	/**
	 * Deze methode slaat de snelste rondetijd van de speler op.
	 * 
	 * @param snelsteRonde
	 *            De snelste ronde van de speler.
	 */
	public void setSnelsteRonde(float snelsteRonde) {
		this.snelsteRonde = snelsteRonde;
	}

	/**
	 * Deze methode stelt in of het scherm getekend moet worden.
	 * 
	 * @param moetTekenen
	 *            Geeft aan of het scherm getekend moet worden.
	 */
	public void setTekenScherm(boolean moetTekenen) {
		tekenScherm = moetTekenen;
	}

	/**
	 * Deze methode stelt het type van het scherm in, en start een alarm als het
	 * type 'eind' is en er nog geen alarm is gestart. Dit alarm wordt gebruikt
	 * zodat men niet meteen het scorebord ziet zodra de speler gefinisht is.
	 * 
	 * @param type
	 *            Het type scherm
	 */
	public void setType(String type) {
		this.type = type;
		if (this.type == "eind" && alarmAlsSpelerGefinisht == null) {
			alarmAlsSpelerGefinisht = new Alarm(1, 75, this);
			alarmAlsSpelerGefinisht.startAlarm();
		}
	}

	private void tekenEindscherm(Canvas canvas, Paint text) {
		setSprite("scorebord");

		canvas.drawText("Position:", 450, 180, text);
		canvas.drawText("Best laptime:", 450, 305, text);
		if (goToNextLevel == true) {
			canvas.drawText("You have reached the next level.", 295, 460, text);
		} else {
			canvas.drawText("You failed to reach the next level.", 295, 460,
					text);
		}

		text.setTextSize(48);
		canvas.drawText("" + kartPositie, 450, 230, text);
		canvas.drawText("" + snelsteRonde, 450, 350, text);
		if (goToNextLevel == true) {
			canvas.drawText("Congratulations!", 295, 430, text);
		} else {
			canvas.drawText("Sorry!", 400, 430, text);
		}

		text.setTextSize(58);
		if (goToNextLevel == true) {
			canvas.drawText("Press a button to continue", 100, 540, text);
		} else {
			canvas.drawText("Press a button to try again", 100, 540, text);
		}
	}

	private void tekenBeginscherm(Canvas canvas, Paint text) {
		setSprite("startscherm");
	}

	private void checkKnopIngedrukt() {
		if (OnScreenButtons.dPadUp || OnScreenButtons.dPadDown
				|| OnScreenButtons.dPadRight || OnScreenButtons.dPadLeft) {
			if (goToNextLevel == true && tekenScherm == true) {
				tekenScherm = false;
				setSprite("leeg");

				// Veranderen zodra er een nieuw level wordt ingesteld.
				game.startSpel();
			} else if (tekenScherm == true) {
				tekenScherm = false;
				setSprite("leeg");
				game.startSpel();
			}
		}
	}

	/**
	 * Deze methode wordt aangeroepen als er een alarm afgaat, en zorgt ervoor
	 * dat het scorebord wordt weergegeven.
	 * 
	 * @param Geeft
	 *            aan welk alarm afgaat.
	 */
	public void triggerAlarm(int alarmID) {
		if (alarmID == 1) {
			alarmAlsSpelerGefinisht = null;
			tekenScherm = true;
			game.stopSpel();
		}
	}
}
