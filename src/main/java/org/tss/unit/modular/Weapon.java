package org.tss.unit.modular;

import org.tss.base.Constructor;
import org.tss.projectile.Rocket;

public class Weapon extends Modul {

	private static final long serialVersionUID = -6687452767666327138L;

	private final Constructor<Rocket> constructor;
	private final double reload;
	private final int salve;

	protected Weapon(Modular modular, Constructor<Rocket> constructor, double reload, int salve) {
		super(modular);
		this.constructor = constructor;
		this.reload = reload;
		this.salve = salve;
	}

	private double s = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void update(double deltaT) {
		s += deltaT;
		if (s >= reload && getModular().getTarget() != null) {
			s -= reload;
			for (int i = 0; i < salve; i++) {
				constructor.create(getModular().getController(),
						u -> u.place(getModular().getMap(), getModular().getPosition().getX(),
								getModular().getPosition().getY(), getModular().getRotate()),
						u -> u.setTarget(getModular().getTarget()));
			}
		}
	}

}
