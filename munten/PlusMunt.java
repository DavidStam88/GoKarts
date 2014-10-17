package munten;

import goKarts.GoKarts;
import kart.KartSpeler;
import android.gameengine.icadroids.alarms.Alarm;

/**
 * 
 * @author Ismay Deze klasse zorgt voor alle acties die uitgevoerd moeten worden
 *         wanneer de plusMunt wordt opgepakt. De munt zorgt ervoor dat de
 *         kartSpeler een 'turbo' krijgt voor 50 updates.
 */
public class PlusMunt extends Munt {
	private KartSpeler speler;
	private Alarm myAlarm;

	/**
	 * constructor van de plusMunt. De constructor maakt een nieuw alarm aan om
	 * er voor te zorgen dat de munt automatisch verwijderd wordt wanneer deze
	 * niet wordt opgepakt.
	 * 
	 * @param game
	 *            Het GoKarts spel.
	 */
	public PlusMunt(GoKarts game) {
		super(game);
		setSprite("plus");
		myAlarm = new Alarm(0, 300, this);
		myAlarm.startAlarm();
	}

	/**
	 * Deze methode geeft de kartSpeler een turbo. De methode maakt de munt
	 * onzichtbaar en stelt een alarm in zodat de kartSpeler na 50 updates weer
	 * op normale snelheid gaat rijden.
	 */
	public void voerActieMuntUit() {
		if (muntOpgepakt == false) {
			muntOpgepakt = true;
			for (int i = 0; i < this.mygame.getKarts().size(); i++) {
				if (this.mygame.getKarts().get(i) instanceof KartSpeler) {
					speler = (KartSpeler) this.mygame.getKarts().get(i);
				}
			}

			speler.rijdtMetAangepasteSnelheid(16);
			setVisibility(false);
			myAlarm = new Alarm(1, 50, this);
			myAlarm.startAlarm();
		}
	}

	/**
	 * Deze methode wordt aangeroepen wanneer een alarm afgaat. Wanneer de munt
	 * nog niet is opgepakt, dan zal de munt verwijderd worden. Is de munt wel
	 * opgepakt én is het het alarm dat bij voerActieMuntUit() is gemaakt, dan
	 * zal de KartSpeler weer op de normale snelheid gaan rijden.
	 * 
	 * @param Geeft
	 *            aan welk alarm afgaat.
	 */
	public void triggerAlarm(int alarmID) {
		if (alarmID != 1 && muntOpgepakt == false) {
			this.mygame.deleteGameObject(this);
		} else {
			speler.rijdtNormaal();
		}
		this.mygame.deleteGameObject(this);
	}

}
