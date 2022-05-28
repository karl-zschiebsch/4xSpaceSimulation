package org.tss.unit.modul;

import org.tss.base.MinmaxDoubleValue;
import org.tss.unit.ship.Ship;

public class Shield extends Modul {

	private static final long serialVersionUID = 4794258387594539089L;

	private final MinmaxDoubleValue value;
	private final double regeneration;

	public Shield(Ship ship, double max, double regeneration) {
		super(ship);
		this.value = ship.shieldPoints;
		this.regeneration = regeneration;

		value.setMax(value.getMax() + max);
		value.setCur(value.getCur() + max);
	}

	@Override
	public void update(double deltaT) {
		value.setCur(deltaT * regeneration);
	}

}
