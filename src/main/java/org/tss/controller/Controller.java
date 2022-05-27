package org.tss.controller;

import org.tss.base.Destructable;
import org.tss.map.Map;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Controller implements Destructable {

	protected final ObservableList<Unit> units = FXCollections.observableArrayList();

	private final Map map;

	public Controller(Map map) {
		map.getControllers().add(this);

		this.map = map;
		units.addListener(new ListChangeListener<Unit>() {

			@Override
			public void onChanged(Change<? extends Unit> c) {
				if (units.isEmpty()) {
					destruct();
				}
			}

		});
	}

	@Override
	public void destruct() {
		map.getControllers().remove(this);
	}

	public ObservableList<Unit> getUnits() {
		return units;
	}

	private static Player main;

	protected void setMainController(Player player) throws Exception {
		if (main != null)
			throw new Exception("Only one main controller is allowed");
		main = player;
	}

	public Player getMainController() {
		return main;
	}

	public boolean isMainController() {
		return this == main;
	}

	public Map getMap() {
		return map;
	}
}
