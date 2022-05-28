package org.tss.unit.ship;

import java.util.function.Function;

import org.tss.base.Constructor;
import org.tss.controller.Controller;
import org.tss.projectile.Rocket;
import org.tss.unit.modul.Engine;
import org.tss.unit.modul.Shield;
import org.tss.unit.modul.Weapon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public final class Defender extends Ship {

	private static final long serialVersionUID = -1780840938026816993L;

	public Defender(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(20, 0, 40, 50, 20, 55, 0, 50);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		new Engine(this, .4);
		new Weapon(this, new Constructor<>(new Function<Controller, Rocket>() {
			@Override
			public Rocket apply(Controller t) {
				return new Rocket(t);
			}
		}), .6, 1, 4);
		new Shield(this, 20, 10);
	}

}
