package org.tss.unit.modular;

import java.util.function.Function;

import org.tss.controller.Controller;
import org.tss.projectile.Projectile;

public class Weapon extends Modul {

	private static final long serialVersionUID = -6687452767666327138L;

	private final Function<Controller, Projectile> launcher;
	private final double reload;
	private final int salve;

	protected Weapon(Modular modular, Function<Controller, Projectile> launcher, double reload, int salve) {
		super(modular);
		this.launcher = launcher;
		this.reload = reload;
		this.salve = salve;
	}

	private final Function<Projectile, Projectile> processor = new Function<Projectile, Projectile>() {
		public Projectile apply(Projectile t) {
			t.place(getModular().getMap(), getModular().getPosition().getX(), getModular().getPosition().getY(),
					getModular().getRotate());
			t.setTarget(getModular().getTarget());
			return t;
		};
	};

	private double s = 0;

	@Override
	public void update(double deltaT) {
		s += deltaT;
		if (s >= reload && getModular().getTarget() != null) {
			s -= reload;
			for (int i = 0; i < salve; i++) {
				launcher.andThen(processor).apply(getModular().getController());
			}
		}
	}

}
