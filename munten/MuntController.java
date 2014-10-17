package munten;

import goKarts.GoKarts;
import android.gameengine.icadroids.alarms.*;

/**
 * 
 * @author Ismay Deze klasse plaatst de munten in het spel wanneer de karts
 *         rijden.
 */
public class MuntController implements IAlarm {
	private GoKarts mygame;
	private Alarm myAlarm;
	private boolean isGepauzeerd;

	/**
	 * De constructor van de muntController. Deze constructor maakt een nieuw
	 * alarm aan voor het plaatsen van een munt.
	 * 
	 * @param gk
	 *            het GoKarts spel
	 */
	public MuntController(GoKarts gk) {
		mygame = gk;
		myAlarm = new Alarm(2, 100, this);
		myAlarm.startAlarm();
		isGepauzeerd = true;
	}

	/**
	 * Deze methode wordt aangeroepen wanneer er een alarm afgaat. Als het alarm
	 * af gaat, wordt er een plek gezocht waar geen gras of finish is om een
	 * munt te plaatsen. Vervolgens wordt het alarm opnieuw gestart.
	 * 
	 * @param Geeft
	 *            aan welk alarm afgaat.
	 */
	public void triggerAlarm(int alarmID) {
		if (isGepauzeerd == false) {
			int x;
			int y;
			Munt munt;

			if (Math.random() < 0.50) {
				munt = new PlusMunt(mygame);
			} else {
				munt = new MinMunt(mygame);
			}

			do {
				x = 15 + (int) (Math.random() * 800);
				y = 15 + (int) (Math.random() * 550);
			} while (mygame.getBaan().getTegels()[y / 100][x / 100] == 0
					|| mygame.getBaan().getTegels()[y / 100][x / 100] == 9
					|| mygame.getBaan().getTegels()[y / 100][(x + 19) / 100] == 0
					|| mygame.getBaan().getTegels()[y / 100][(x + 19) / 100] == 9
					|| mygame.getBaan().getTegels()[(y + 19) / 100][x / 100] == 0
					|| mygame.getBaan().getTegels()[(y + 19) / 100][x / 100] == 9
					|| mygame.getBaan().getTegels()[(y + 19) / 100][(x + 19) / 100] == 0
					|| mygame.getBaan().getTegels()[(y + 19) / 100][(x + 19) / 100] == 9);

			mygame.addGameObject(munt, x, y);
		}
		myAlarm.restartAlarm();
	}

	/**
	 * Stelt in of de muntController moet pauzeren of niet.
	 * 
	 * @param isGepauzeerd
	 *            Geeft aan of de muntController is gepauzeerd.
	 */
	public void setGepauzeerd(boolean isGepauzeerd) {
		this.isGepauzeerd = isGepauzeerd;
	}

}
