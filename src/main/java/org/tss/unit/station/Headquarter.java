package org.tss.unit.station;

import org.tss.controller.Controller;
import org.tss.entity.Constructor;
import org.tss.entity.builder.Dev;
import org.tss.entity.builder.Slot;
import org.tss.projectile.Rocket;
import org.tss.unit.modul.Shield;
import org.tss.unit.modul.Factory;
import org.tss.unit.modul.Weapon;
import org.tss.unit.ship.Carrier;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Headquarter extends Station {

	public Headquarter(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(0, 20, 20, 0, 40, 0, 60, 20, 60, 40, 40, 60, 20, 60, 0, 40);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		new Weapon(this, new Constructor<>(() -> new Rocket(getController())), .6, 1, 15, 4);
		new Shield(this, 20, 10);
		new Factory(this);

		new Dev(this, () -> new Slot(this, new Constructor<>(() -> new Carrier(getController())), 5));
	}
}
