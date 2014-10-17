package lapTime;

/**
 * 
 * @author Ismay Deze klasse laat een teller lopen zodra de KartSpeler begint
 *         met rijden.
 */
public class LapTime {
	private long startTijd;
	private float eindTijd;

	/**
	 * Deze methode vraagt de tijd van de computer op, die vervolgens wordt
	 * ingesteld als starttijd.
	 */
	public void startTimer() {
		startTijd = System.currentTimeMillis();
	}

	/**
	 * Deze methode stopt de timer en slaat de verstreken tijd op als eindtijd.
	 */
	public void stopTimer() {
		eindTijd = getVerstrekenTijd();
	}

	/**
	 * Deze methode geeft de tijd die de methode gelopen heeft.
	 * 
	 * @return de verstreken tijd
	 */
	public float getEindTijd() {
		return eindTijd;
	}

	/**
	 * Deze methode geeft de starttijd van de timer.
	 * 
	 * @return de starttijd
	 */
	public long getStartTijd() {
		return startTijd;
	}

	/**
	 * Geeft de verstreken tijd door de starttijd van de huidige tijd af te
	 * trekken.
	 * 
	 * @return de verstreken tijd
	 */
	public float getVerstrekenTijd() {
		long nu = System.currentTimeMillis();
		return ((nu - startTijd) / 10);
	}

	/**
	 * Geeft de verstreken tijd weer in seconden.
	 * 
	 * @return de verstreken tijd in seconden
	 */
	public String toString() {
		return "" + getVerstrekenTijd() / 100;
	}

}
