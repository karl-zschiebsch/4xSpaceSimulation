package org.tss.unit.ship;

import java.util.function.Function;

import org.tss.controller.Controller;
import org.tss.entity.Constructor;
import org.tss.projectile.Ray;
import org.tss.unit.modul.Engine;
import org.tss.unit.modul.Weapon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hunter extends Ship {

	public Hunter(Squadron<Hunter> squadron) {
		super(squadron);

		Polygon poly = new Polygon(3, 0, 6, 9, 3, 7, 0, 9);
		poly.setFill(Color.CADETBLUE);

		getChildren().add(poly);

		new Engine(this, 2);
		new Weapon(this, new Constructor<>(new Function<Controller, Ray>() {
			@Override
			public Ray apply(Controller t) {
				return new Ray(t);
			}
		}), .1, .5, 0, 1);
	}

	@Override
	public boolean isSupported() {
		return false;
	}
}
