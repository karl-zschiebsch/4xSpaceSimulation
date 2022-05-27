package org.tss.unit.modul;

import org.tss.base.Destructable;
import org.tss.base.Entity;
import org.tss.base.MinmaxDoubleValue;
import org.tss.unit.Harmable;
import org.tss.unit.ship.Ship;

public abstract class Modul implements Entity, Destructable, Harmable {

	private static final long serialVersionUID = 384505554762996035L;

	private final Ship ship;

	protected Modul(Ship ship) {
		this.ship = ship;
		ship.getModules().add(this);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void destruct() {
		ship.getModules().remove(this);
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

	public Ship getModular() {
		return ship;
	}
}
