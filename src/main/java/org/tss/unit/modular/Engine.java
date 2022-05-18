package org.tss.unit.modular;

public class Engine extends Modul {

	private static final long serialVersionUID = 2929385733879335614L;

	private final double speed;

	protected Engine(Modular modular, double speed) {
		super(modular);
		this.speed = speed;
	}

	@Override
	public void update(double deltaT) {
		getModular().move(deltaT * speed);
		getModular().rotate(deltaT * speed);
	}

}
