package org.tss.unit.ship;

import org.tss.controller.Controller;
import org.tss.entity.Constructor;
import org.tss.projectile.Rocket;
import org.tss.unit.modul.Engine;
import org.tss.unit.modul.Shield;
import org.tss.unit.modul.Weapon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public final class Defender extends Ship {

	public Defender(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(20, 0, 40, 50, 20, 55, 0, 50);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		new Engine(this, .72);
		new Weapon(this, new Constructor<>(() -> {
			return new Rocket(getController());
		}), .6, 1, 45, 4);
		new Shield(this, 4, 0.4);
	}

}
