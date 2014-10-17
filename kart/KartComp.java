package kart;

import goKarts.GoKarts;

import java.util.ArrayList;
import java.util.List;

import lapTime.LapTime;


import baan.Baan;
import baanPunten.BaanChecks;

import android.gameengine.icadroids.objects.collisions.TileCollision;

/**
 * 
 * @author David & Ismay Deze klasse is een uitbreiding op de superklasse kart.
 *         Met de klasse kan men een computer gestuurde kart toevoegen.
 * 
 */
public class KartComp extends Kart {
	/**
	 * De constructor voor de KartComp. In de constructor wordt een wrijving
	 * geset en een normale snelheid.
	 **/
	public KartComp(ArrayList<BaanChecks> punten, String kleur, Baan baan,
			GoKarts goKarts) {
		super(punten, kleur, baan, goKarts);
		setFriction(0.05);
		setNormaleSnelheid();
	}

	@Override
	public void update() {
		super.update();
		if (goKarts.getStartRace()) {
			setSnelheid();
			this.beweegNaarPunt();
			this.positie();
			checkVoorBotsing();
		}
	}

	/**
	 * @author David Deze methode zorgt ervoor dat de computerbestuurde karts
	 *         naar hun coordinaten rijden.
	 */
	public void beweegNaarPunt() {
		if (this.contactPunt > (baan.getPunten().size() - 1)) {
			this.contactPunt = 0;
		}
		this.moveTowardsAPoint(baan.getPunten().get(contactPunt).getPositieX(),
				baan.getPunten().get(contactPunt).getPositieY());
		if (this.getX() > (baan.getPunten().get(contactPunt).getPositieX() - 10)
				&& this.getX() < (baan.getPunten().get(contactPunt)
						.getPositieX() + 10)
				&& this.getY() > (baan.getPunten().get(contactPunt)
						.getPositieY() - 10)
				&& this.getY() < (baan.getPunten().get(contactPunt)
						.getPositieY() + 10)) {
			contactPunt++;
		}
	}

	/**
	 * @return de waarde van het variabele contactPunt.
	 */
	public int getContactPunt() {
		return this.contactPunt;
	}

	/**
	 * Deze methodes zijn vanwege de abstracte superklasse verplicht maar voeren
	 * niets uit.
	 */
	@Override
	public void voegRondeToe() {
	}

	@Override
	public void stopTimer() {
	}

	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) {
		// TODO Auto-generated method stub
	}

	/**
	 * @author Ismay Stelt de normale snelheid van de Kart in. Dit is de
	 *         snelheid die de kart zal hebben op de baan wanneer er geen
	 *         muntjes opgepakt zijn. Het is van belang dat deze methode maar 1
	 *         keer aangeroepen wordt..
	 */

	public void setNormaleSnelheid() {
		if (this.kleur == "blauw") {
			normaleSnelheid = normaleSnelheid - 2;
		} else if (this.kleur == "groen") {
			normaleSnelheid = normaleSnelheid - 3;
		} else if (this.kleur == "bruin") {
			normaleSnelheid = normaleSnelheid + 1;
		}
	}

	@Override
	public ArrayList<LapTime> getRondeTijden() {
		// TODO Auto-generated method stub
		return null;
	}

}
