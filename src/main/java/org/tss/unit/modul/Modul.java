package org.tss.unit.modul;

import org.tss.entity.Destructable;
import org.tss.entity.Entity;
import org.tss.unit.Harmable;
import org.tss.unit.Unit;
import org.tss.value.PercentageValue;

public abstract class Modul implements Entity, Destructable, Harmable {

	private final Unit unit;

	protected Modul(Unit unit) {
		this.unit = unit;
		unit.getModules().add(this);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void destruct() {
		unit.getModules().remove(this);
	}

	@Override
	public void harm(double value) {
		hitPoints.setCur(hitPoints.getCur() - value);
	};

	protected PercentageValue hitPoints = new PercentageValue(1);

	public final void setHitPoints(double value) {
		hitPoints.setCur(value);
	}

	public final double getHitPoints() {
		return hitPoints.getCur();
	}

	public Unit getUnit() {
		return unit;
	}
}
