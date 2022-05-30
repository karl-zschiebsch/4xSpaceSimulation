package org.tss.unit.modul;

import org.tss.entity.Constructor;
import org.tss.unit.Unit;
import org.tss.value.DoubleCounter;
import org.tss.value.IntegerCounter;

public class Hangar extends Modul {

	private final Constructor<? extends Unit> constructor;
	private final DoubleCounter droprate;
	private final IntegerCounter capaticity;

	public Hangar(Unit unit, Constructor<? extends Unit> constructor, double droprate, int capaticity) {
		super(unit);
		this.constructor = constructor;
		this.droprate = new DoubleCounter(droprate);
		this.capaticity = new IntegerCounter(capaticity, 0);
	}

	@Override
	public void update(double deltaT) {
		if (!capaticity.hasReached()) {
			if (droprate.hasReached()) {
				constructor.create(getModular().getController(),
						u -> u.place(getModular().getMap(), getModular().getPosition().getX(),
								getModular().getPosition().getY(), getModular().getRotate()),
						u -> u.setTarget(getModular().getTarget()));
				droprate.down();
				capaticity.up(1);
			} else {
				droprate.up(deltaT);
			}
		}
	}
}
