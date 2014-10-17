package baanPunten;

/**
 * 
 * @author David Met deze klasse kan men een finish maken welke aan de baan
 *         toegevoegd wordt. Let op: Deze klasse is eenuitbreiding op de klasse
 *         BaanChecks.
 */
public class Finish extends BaanChecks {
	private int checkpointNummer;

	/**
	 * De constructor voor de finish.
	 * 
	 * @param checkpointNummer
	 *            Deze variabele bevat het unieke nummer van de checkpoint. Ook
	 *            wordt er in de constructor een sprite geset die het uiterlijk
	 *            van de finish bepaald.
	 * 
	 * */
	public Finish(int positieX, int positieY, int checkpointNummer) {
		super(positieX, positieY);
		setSprite("finish");
		this.checkpointNummer = checkpointNummer;
	}

	/**
	 * @return Het nummer van de checkpoint.
	 */
	@Override
	public int getCheckpointNummer() {
		return this.checkpointNummer;
	}

}
