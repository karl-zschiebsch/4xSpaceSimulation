package org.tss.unit.modul;

import org.tss.base.Destructable;
import org.tss.base.Entity;
import org.tss.base.MinmaxDoubleValue;
import org.tss.unit.Harmable;
import org.tss.unit.Unit;

public abstract class Modul implements Entity, Destructable, Harmable {

	private static final long serialVersionUID = 384505554762996035L;

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

	protected MinmaxDoubleValue hitPoints = new MinmaxDoubleValue(1);

	public final void setHitPoints(double value) {
		hitPoints.setCur(value);
	}

	public final double getHitPoints() {
		return hitPoints.getCur();
	}

	public Unit getModular() {
		return unit;
	}
}
