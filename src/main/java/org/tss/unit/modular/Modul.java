package org.tss.unit.modular;

import org.tss.base.Constructable;
import org.tss.base.Entity;
import org.tss.base.ModalDoubleValue;
import org.tss.unit.Harmable;

public abstract class Modul implements Entity, Constructable, Harmable {

	private static final long serialVersionUID = 384505554762996035L;
	
	private final Modular modular;

	protected Modul(Modular modular) {
		this.modular = modular;

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void construct() {
		modular.getModules().add(this);
	}

	@Override
	public void destruct() {
		modular.getModules().remove(this);
	}

	@Override
	public void harm(double value) {
		hitPoints.set(hitPoints.get() - value);
	};

	protected ModalDoubleValue hitPoints = new ModalDoubleValue(1);

	public final void setHitPoints(double value) {
		hitPoints.set(value);
	}

	public final double getHitPoints() {
		return hitPoints.get();
	}
}
