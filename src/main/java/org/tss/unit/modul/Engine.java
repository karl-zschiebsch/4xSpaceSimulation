package org.tss.unit.modul;

import org.tss.unit.ship.Ship;

public class Engine extends Modul {

	private final double speed;

	public Engine(Ship ship, double speed) {
		super(ship);
		this.speed = speed;
	}

	@Override
	public void update(double deltaT) {
		getUnit().move(deltaT * speed);
		getUnit().rotate(deltaT * speed);
	}

}
