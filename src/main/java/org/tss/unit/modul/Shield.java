package org.tss.unit.modul;

import org.tss.unit.Unit;
import org.tss.value.PercentageValue;

public class Shield extends Modul {

	private final PercentageValue value;
	private final double regeneration;

	public Shield(Unit unit, double max, double regeneration) {
		super(unit);
		this.value = unit.shieldPoints;
		this.regeneration = regeneration;

		value.setMax(value.getMax() + max);
		value.setCur(value.getCur() + max);
	}

	@Override
	public void update(double deltaT) {
		value.setCur(value.getCur() + deltaT * regeneration);
	}

}
