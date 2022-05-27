package org.tss.projectile;

import org.tss.base.MinmaxDoubleValue;
import org.tss.base.SpaceObject;
import org.tss.controller.Controller;
import org.tss.unit.Harmable;

public abstract class Projectile extends SpaceObject {

	private static final long serialVersionUID = -5682494091592111719L;

	private final double damage, speed, critChance, critDamage;

	protected Projectile(Controller controller, double damage, double speed, double critChance, double critDamage) {
		super(controller);
		this.damage = damage;
		this.speed = speed;
		this.critChance = critChance;
		this.critDamage = critDamage;

		fuelPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void update(double deltaT) {
		setFuelPoints(getFuelPoints() - deltaT / 10);

		for (int i = 0; i < getMap().getObjects().size(); i++) {
			SpaceObject object = getMap().getObjects().get(i);
			if (object instanceof Harmable && object.getController() != getController()) {
				if (inside(object)) {
					double value = calc();
					((Harmable) object).harm(value);
					System.out.println(value);
					destruct();
				}
			}
		}
	}

	protected double calc() {
		return ((critChance % 1) > Math.random() ? damage * critDamage : damage) * Math.pow(2, (int) critChance + 1);
	}

	@Override
	public void move(double deltaT) {
		super.move(deltaT * speed);
	}

	@Override
	public void rotate(double deltaT) {
		super.rotate(deltaT * speed);
	}

	protected MinmaxDoubleValue fuelPoints = new MinmaxDoubleValue(1);

	public final void setFuelPoints(double value) {
		fuelPoints.setCur(value);
	}

	public final double getFuelPoints() {
		return fuelPoints.getCur();
	}
}
