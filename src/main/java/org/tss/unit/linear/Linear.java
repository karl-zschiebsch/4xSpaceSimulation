package org.tss.unit.linear;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

public class Linear extends Unit {

	private static final long serialVersionUID = 7266084694106452548L;

	public Linear(Controller controller) {
		super(controller);
	}

	@Override
	public void update(double deltaT) {
		rotate(deltaT);
		move(deltaT);
	}

	@Override
	public void harm(double value) {
		hitPoints.set(value);
	}

}
