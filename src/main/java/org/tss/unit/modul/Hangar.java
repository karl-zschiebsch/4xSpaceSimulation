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
				constructor.create(u -> u.place(getUnit().getMap(), getUnit().getPosition().getX(),
						getUnit().getPosition().getY(), getUnit().getRotate()),
						u -> u.setTarget(getUnit().getTarget()));
				droprate.reset();
				capaticity.up(1);
			} else {
				droprate.up(deltaT);
			}
		}
	}
}
