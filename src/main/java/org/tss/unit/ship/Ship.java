package org.tss.unit.ship;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

public class Ship extends Unit {

	private final Squadron<? extends Unit> squadron;

	protected Ship(Squadron<?> squadron) {
		super(squadron.getController());
		this.squadron = squadron;
	}

	protected Ship(Controller controller) {
		super(controller);
		this.squadron = null;
	}

	@Override
	public void destruct() {
		super.destruct();
		if (squadron != null)
			squadron.getSubUnits().remove(this);
	}

	public Squadron<?> getSquadron() {
		return squadron;
	}
}
