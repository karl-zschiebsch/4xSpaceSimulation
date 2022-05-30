package org.tss.unit.station;

import java.util.function.Function;

import org.tss.controller.Controller;
import org.tss.entity.Constructor;
import org.tss.projectile.Rocket;
import org.tss.unit.modul.Shield;
import org.tss.unit.modul.Shipyard;
import org.tss.unit.modul.Weapon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Headquarter extends Station {

	public Headquarter(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(0, 20, 20, 0, 40, 0, 60, 20, 60, 40, 40, 60, 20, 60, 0, 40);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		new Weapon(this, new Constructor<>(new Function<Controller, Rocket>() {
			@Override
			public Rocket apply(Controller t) {
				return new Rocket(t);
			}
		}), .6, 1, 15, 4);
		new Shield(this, 20, 10);
		new Shipyard(this);
	}

}
