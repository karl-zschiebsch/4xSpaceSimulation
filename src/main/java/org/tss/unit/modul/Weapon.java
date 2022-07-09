package org.tss.unit.modul;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.hypot;
import static java.lang.Math.max;
import static java.lang.Math.signum;
import static java.lang.Math.toDegrees;

import org.tss.entity.Constructor;
import org.tss.projectile.Projectile;
import org.tss.unit.Unit;
import org.tss.value.DoubleCounter;
import org.tss.value.IntegerCounter;

public class Weapon extends Modul {

	private final Constructor<? extends Projectile> constructor;
	private final DoubleCounter reloadspeed, firerate;
	private final IntegerCounter salve;

	public Weapon(Unit unit, Constructor<? extends Projectile> constructor, double firerate, double reloadspeed,
			double rotationspeed, int salve) {
		super(unit);
		this.constructor = constructor;
		this.firerate = new DoubleCounter(firerate);
		this.reloadspeed = new DoubleCounter(reloadspeed);
		this.salve = new IntegerCounter(salve, 0);
		this.rotationspeed = rotationspeed;
	}

	@Override
	public void update(double deltaT) {
		if (getUnit().getTarget() != null) {
			if (rotationspeed != 0) {
				rotate(deltaT * rotationspeed);
			}
			if (reloadspeed.hasReached()) {
				if (firerate.hasReached()) {
					if (!salve.hasReached()) {
						constructor.create(
								u -> u.place(getUnit().getMap(), getUnit().getPosition().getX(),
										getUnit().getPosition().getY(), getUnit().getRotate() + rotate),
								u -> u.setTarget(getUnit().getTarget()));
						firerate.reset();
						salve.up(1);
					} else {
						reloadspeed.reset();
						salve.reset();
					}
				} else {
					firerate.up(deltaT);
				}
			} else {
				reloadspeed.up(deltaT);
			}
		}
	}

	private double rotationspeed, rotate = 0;

	public void rotate(double deltaT) {
		double difX = getUnit().getPosition().getX() - getUnit().getTarget().getPosition().getX();
		double difY = getUnit().getPosition().getY() - getUnit().getTarget().getPosition().getY();
		double alpha = -toDegrees(atan2(difX, difY));
		double length = hypot(difX, difY);
		if (length > max(deltaT, 5)) {
			double difR = alpha - getUnit().getRotate() - rotate;
			if (difR > 180) {
				difR -= 360;
			}
			if (difR < -180) {
				difR += 360;
			}
			if (difR > .5 || difR < -.5) {
				double ang = deltaT;
				if (ang > abs(difR)) {
					rotate += difR;
				} else {
					rotate += signum(difR) * ang;
				}
			}
		}
	}
}
