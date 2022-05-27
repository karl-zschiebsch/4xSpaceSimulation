package org.tss.unit.modul;

import org.tss.base.Constructor;
import org.tss.projectile.Projectile;
import org.tss.unit.ship.Ship;

public class Weapon extends Modul {

	private static final long serialVersionUID = -6687452767666327138L;

	private final Constructor<? extends Projectile> constructor;
	private final double reload, firerate;
	private final int salve;

	public Weapon(Ship ship, Constructor<? extends Projectile> constructor, double firerate, double reload, int salve) {
		super(ship);
		this.constructor = constructor;
		this.firerate = firerate;
		this.reload = reload;
		this.salve = salve;
	}

	private double r = 0, f = 0;
	private int s = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void update(double deltaT) {
		if (r >= reload) {
			if (getModular().getTarget() != null) {
				if (f >= firerate) {
					if (s < salve) {
						constructor.create(getModular().getController(),
								u -> u.place(getModular().getMap(), getModular().getPosition().getX(),
										getModular().getPosition().getY(), getModular().getRotate()),
								u -> u.setTarget(getModular().getTarget()));
						s++;
						f = 0;
					} else {
						r = 0;
						s = 0;
					}
				} else {
					f += deltaT;
				}
			}
		} else {
			r = r > reload ? reload : r + deltaT;
		}
	}

}
