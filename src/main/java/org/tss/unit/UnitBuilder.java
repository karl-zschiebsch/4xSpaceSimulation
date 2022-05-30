package org.tss.unit;

import org.tss.controller.ResourceType.ResourceCost;
import org.tss.entity.Constructor;
import org.tss.entity.Entity;
import org.tss.unit.station.Station;
import org.tss.value.DoubleCounter;

public class UnitBuilder implements Entity, Cloneable {

	private final Station reference;
	private final Constructor<? extends Unit> launcher;
	private final ResourceCost cost;
	private final DoubleCounter timer;

	public UnitBuilder(Station reference, Constructor<? extends Unit> launcher, ResourceCost cost, double time) {
		this.reference = reference;
		this.launcher = launcher;
		this.cost = cost;
		this.timer = new DoubleCounter(time);
	}

	@Override
	public void update(double deltaT) {
		if (timer.hasReached()) {
			launcher.create(reference.getController(), u -> u.place(reference.getMap(), reference.getPosition().getX(),
					reference.getPosition().getY(), reference.getRotate()));
			reference.getProgress().remove(this);
		} else {
			timer.up(deltaT);
		}
	}

	@Override
	public UnitBuilder clone() throws CloneNotSupportedException {
		return new UnitBuilder(reference, launcher, cost, timer.getUp());
	}

}
