package munten;

import goKarts.GoKarts;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.GameObject;

/**
 * 
 * @author Ismay De superklasse voor alle munten in het spel.
 */

public abstract class Munt extends GameObject implements IAlarm {
	protected GoKarts mygame;
	protected boolean muntOpgepakt;

	/**
	 * Constructor voor de munt. Slaat het GoKarts spel op en stelt in dat de
	 * munt nog niet is opgepakt.
	 * 
	 * @param game
	 *            GoKarts spel
	 */
	public Munt(GoKarts game) {
		mygame = game;
		muntOpgepakt = false;
	}

	/**
	 * Deze methode zorgt ervoor dat de actie uitgevoerd wordt die bij de
	 * desbetreffende munt hoort die wordt opgepakt.
	 */
	public abstract void voerActieMuntUit();
}
