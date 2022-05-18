package org.tss.projectile;

import org.tss.base.ModalDoubleValue;
import org.tss.base.SpaceObject;
import org.tss.controller.Controller;
import org.tss.unit.Harmable;

public abstract class Projectile extends SpaceObject {

	private static final long serialVersionUID = -5682494091592111719L;

	protected Projectile(Controller controller) {
		super(controller);

		fuelPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void update(double deltaT) {
		setFuelPoints(getFuelPoints() - deltaT / 10);

		for (int i = 0; i < getMap().getObjects().size(); i++) {
			SpaceObject object = getMap().getObjects().get(i);
			if (object instanceof Harmable && object.getController() != getController()) {
				if (inside(object)) {
					((Harmable) object).harm(1);
					destruct();
				}
			}
		}
	}

	protected ModalDoubleValue fuelPoints = new ModalDoubleValue(1);

	public final void setFuelPoints(double value) {
		fuelPoints.set(value);
	}

	public final double getFuelPoints() {
		return fuelPoints.get();
	}
}
