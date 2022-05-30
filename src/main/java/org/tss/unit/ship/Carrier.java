package org.tss.unit.ship;

import org.tss.controller.Controller;
import org.tss.entity.Constructor;
import org.tss.projectile.Rocket;
import org.tss.unit.modul.Engine;
import org.tss.unit.modul.Hangar;
import org.tss.unit.modul.Shield;
import org.tss.unit.modul.Weapon;

import com.google.common.base.Function;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Carrier extends Ship {

	public Carrier(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(20, 0, 22, 28, 28, 28, 30, 0, 50, 60, 25, 68, 0, 60);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		new Engine(this, .26);
		new Hangar(this, new Constructor<Squadron<Hunter>>(new Function<Controller, Squadron<Hunter>>() {
			@Override
			public Squadron<Hunter> apply(Controller c) {
				Squadron<Hunter> squad = new Squadron<>(controller);
				for (int i = 0; i < 3; i++) {
					squad.getSubUnits().add(new Hunter(squad));
				}
				return squad;
			}
		}), 4.5, 3);
		new Weapon(this, new Constructor<>(new Function<Controller, Rocket>() {
			@Override
			public Rocket apply(Controller t) {
				return new Rocket(t);
			}
		}), .6, 1, 10, 4);
		new Shield(this, 6, 0.4);
	}

}
