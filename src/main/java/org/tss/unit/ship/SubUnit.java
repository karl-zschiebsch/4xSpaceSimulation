package org.tss.unit.ship;

public class SubUnit extends Ship {

	private static final long serialVersionUID = -8466940362637005902L;

	private final Squadron<? extends SubUnit> squadron;

	protected SubUnit(Squadron<?> squadron) {
		super(squadron.getController());
		this.squadron = squadron;
	}

	@Override
	public void destruct() {
		super.destruct();
		squadron.getSubUnits().remove(this);
	}

	public Squadron<?> getSquadron() {
		return squadron;
	}
}
