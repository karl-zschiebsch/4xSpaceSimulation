package org.tss.controller;

import org.tss.base.Destructable;
import org.tss.base.Entity;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public abstract class Controller implements Entity, Destructable {

	private static final long serialVersionUID = -3875467935907735000L;

	public static final ObservableList<Controller> CONTOLLER = FXCollections.observableArrayList();

	protected final ObservableList<Unit> units = FXCollections.observableArrayList();

	protected Controller() {
		units.addListener(new ListChangeListener<Unit>() {

			@Override
			public void onChanged(Change<? extends Unit> c) {
				if (units.isEmpty()) {
					destruct();
				}
			}

		});
		CONTOLLER.add(this);
	}

	@Override
	public void update(double deltaT) {
		for (int i = 0; i < getUnits().size(); i++) {
			getUnits().get(i).update(deltaT);
		}
	}

	@Override
	public void destruct() {
		CONTOLLER.remove(this);
	}

	public ObservableList<Unit> getUnits() {
		return units;
	}

}
