package baanPunten;

/**
 * 
 * @author David Met deze klasse kan men een checkpoint maken. Deze moet in het
 *         spel aangeraakt worden anders kan de speler niet finishen. Dit is om
 *         valspelen te voorkomen bijvoorbeeld. Let op: Deze klasse is
 *         eenuitbreiding op de klasse BaanChecks.
 * 
 */
public class CheckPoint extends BaanChecks {
	private int checkpointNummer;

	/**
	 * De constructor voor het checkpoint.
	 * 
	 * @param checkpointNummer
	 *            Deze variabele bevat het unieke nummer van de checkpoint. Ook
	 *            wordt er in de constructor een sprite geset die het uiterlijk
	 *            van de checkpoint bepaald.
	 * 
	 * */

	public CheckPoint(int positieX, int positieY, int checkpointNummer) {
		super(positieX, positieY);
		this.checkpointNummer = checkpointNummer;
		setSprite("checkpoint");
	}

	/**
	 * @return Het nummer van de checkpoint.
	 */
	@Override
	public int getCheckpointNummer() {
		return this.checkpointNummer;
	}

}
