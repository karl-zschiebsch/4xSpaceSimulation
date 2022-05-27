package org.tss.unit.ship;

import java.util.ArrayList;

import org.tss.controller.Controller;
import org.tss.unit.Unit;
import org.tss.unit.modul.Modul;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Ship extends Unit {

	private static final long serialVersionUID = 221341942869079545L;

	private final ObservableList<Modul> modules = FXCollections.observableArrayList();
	private final ArrayList<Modul> removed = new ArrayList<>();

	protected Ship(Controller controller) {
		super(controller);
		modules.addListener(new ListChangeListener<Modul>() {
			@Override
			public void onChanged(Change<? extends Modul> c) {
				c.next();
				removed.addAll(c.getRemoved());

				setHitPoints(modules.size() / (modules.size() + removed.size()));
			}
		});
	}

	@Override
	public void update(double deltaT) {
		for (int i = 0; i < modules.size(); i++) {
			modules.get(i).update(deltaT);
		}
	}

	@Override
	public void harm(double value) {
		if (modules.isEmpty())
			return;
		Modul modul = modules.get((int) (Math.random() * modules.size()));
		modul.setHitPoints(modul.getHitPoints() - damagingHull(damagingShields(value)));
	}

	public ObservableList<Modul> getModules() {
		return modules;
	}

}
