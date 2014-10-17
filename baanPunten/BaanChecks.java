package baanPunten;

import android.gameengine.icadroids.objects.MoveableGameObject;

/**
 * 
 * @author David Met deze abstracte klasse kan men een onderdeel van de baan
 *         maken. Een checkpoint, coordinaat voor de computerkartjes of een
 *         finish.
 * 
 */
public abstract class BaanChecks extends MoveableGameObject {
	private int positieX;
	private int positieY;

	/**
	 * De constructor voor de BaanCheck.
	 * 
	 * @param positieX
	 *            Deze variabele bevat de X-coordinaat van het object.
	 * @param positieY
	 *            Deze variabele bevat de Y-coordinaat van het object. Deze twee
	 *            waardes worden meegegeven bij het aanmaken van een BaanCheck.
	 * 
	 * */

	public BaanChecks(int positieX, int positieY) {
		this.positieX = positieX;
		this.positieY = positieY;
	}

	public int getPositieX() {
		return this.positieX;
	}

	public int getPositieY() {
		return this.positieY;
	}

	public String toString() {
		return "X: " + this.positieX + "Y: " + this.positieY;
	}

	/**
	 * 
	 * Deze methode returned de nummer van de BaanCheck. Elke BaanCheck krijgt
	 * een eigen nummer zodat het checkpunt makkelijk te vinden is in een
	 * ArrayList en men weet welke checkpunt precies aangedaan wordt.
	 * 
	 */
	public abstract int getCheckpointNummer();
}
