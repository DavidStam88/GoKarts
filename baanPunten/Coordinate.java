package baanPunten;

/**
 * 
 * @author David Met deze klasse kan men een coordinaat aanmaken die de
 *         computergestuurde kart aan moet doen op het parcours. Let op: Deze
 *         klasse is eenuitbreiding op de klasse BaanChecks.
 */
public class Coordinate extends BaanChecks {

	public Coordinate(int positieX, int positieY) {
		super(positieX, positieY);
	}

	/**
	 * Een methode die de klasse moet bevatten van de superconstructor, maar
	 * welke in deze klasse feitelijk hol is.
	 * 
	 * @return 0.
	 */
	@Override
	public int getCheckpointNummer() {
		// TODO Auto-generated method stub
		return 0;
	}

}
