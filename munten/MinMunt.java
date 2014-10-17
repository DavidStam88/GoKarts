package munten;

import goKarts.GoKarts;
import kart.KartSpeler;
import android.gameengine.icadroids.alarms.Alarm;

/**
 * 
 * @author Ismay Deze klasse zorgt voor alle acties die uitgevoerd moeten worden
 *         wanneer de minMunt wordt opgepakt. De minMunt zorgt ervoor dat de
 *         computerspelers langzamer gaan rijden.
 */
public class MinMunt extends Munt {
	private Alarm myAlarm;

	/**
	 * Constructor van de minMunt. De constructor maakt een nieuw alarm aan om
	 * er voor te zorgen dat de munt automatisch verwijderd wordt wanneer deze
	 * niet wordt opgepakt.
	 * 
	 * @param game
	 *            het GoKarts spel
	 */
	public MinMunt(GoKarts game) {
		super(game);
		setSprite("min");
		myAlarm = new Alarm(0, 300, this);
		myAlarm.startAlarm();
	}

	/**
	 * Deze methode vertraagd alle kartComputers. De methode maakt de munt
	 * onzichtbaar en stelt een alarm in zodat de kartComputers na 100 updates
	 * weer op normale snelheid gaan rijden.
	 */
	public void voerActieMuntUit() {
		if (muntOpgepakt == false) {
			muntOpgepakt = true;
			for (int i = 0; i < this.mygame.getKarts().size(); i++) {
				if ((this.mygame.getKarts().get(i) instanceof KartSpeler) == false) {
					this.mygame.getKarts().get(i).rijdtMetAangepasteSnelheid(2);
				}
			}

			setVisibility(false);
			myAlarm = new Alarm(1, 100, this);
			myAlarm.startAlarm();
		}
	}

	/**
	 * Deze methode wordt aangeroepen wanneer een alarm afgaat. Wanneer de munt
	 * nog niet is opgepakt, dan zal de munt verwijderd worden. Is de munt wel
	 * opgepakt én is het het alarm dat bij voerActieMuntUit() is gemaakt, dan
	 * zullen alle Karts weer op normale snelheid gaan rijden.
	 * 
	 * @param Geeft
	 *            aan welk alarm afgaat.
	 */
	public void triggerAlarm(int alarmID) {
		if (alarmID != 1 && muntOpgepakt == false) {
			this.mygame.deleteGameObject(this);
		} else {
			for (int i = 0; i < this.mygame.getKarts().size(); i++) {
				if ((this.mygame.getKarts().get(i) instanceof KartSpeler) == false) {
					this.mygame.getKarts().get(i).rijdtNormaal();
				}
			}
			this.mygame.deleteGameObject(this);
		}

	}

}
