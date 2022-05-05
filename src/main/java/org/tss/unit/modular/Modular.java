package org.tss.unit.modular;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

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
				}
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
		modules.get((int) (Math.random() * modules.size()));
	}

	public ObservableList<Modul> getModules() {
		return modules;
	}

}
