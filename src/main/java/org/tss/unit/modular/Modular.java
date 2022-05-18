package org.tss.unit.modular;

import java.util.function.Function;

import org.tss.controller.Controller;
import org.tss.projectile.Projectile;
import org.tss.projectile.Rocket;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Modular extends Unit {

	private static final long serialVersionUID = 221341942869079545L;

	private final ObservableList<Modul> modules = FXCollections.observableArrayList();

	public Modular(Controller controller) {
		super(controller);
		modules.addListener(new ListChangeListener<Modul>() {

			@Override
			public void onChanged(Change<? extends Modul> c) {
				c.next();
				int a = c.getAddedSize();
				if (a > 0) {
					hitPoints.setMax(hitPoints.getMax() + a);
					hitPoints.setCur(hitPoints.getCur() + a);
				}

				int r = c.getRemovedSize();
				if (r > 0) {
					hitPoints.setCur(hitPoints.getCur() - r);
					System.out.println(r);
				}
			}
		});

		Polygon poly = new Polygon(20, 0, 40, 60, 0, 60);
		poly.setFill(Color.RED);
		getChildren().add(poly);

		new Engine(this, 1);
		new Weapon(this, new Function<Controller, Projectile>() {
			@Override
			public Projectile apply(Controller t) {
				return new Rocket(t);
			}
		}, 0.8, 1);
	}

	@Override
	public void update(double deltaT) {
		for (int i = 0; i < modules.size(); i++) {
			modules.get(i).update(deltaT);
		}
	}

	@Override
	public void harm(double value) {
		Modul modul = modules.get((int) (Math.random() * modules.size()));
		modul.setHitPoints(modul.getHitPoints() - value);
	}

	public ObservableList<Modul> getModules() {
		return modules;
	}

}
