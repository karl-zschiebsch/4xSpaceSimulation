package org.tss.controller;

import org.tss.base.Entity;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Controller implements Entity {

	private static final long serialVersionUID = -3875467935907735000L;

	protected final ObservableList<Unit> units = FXCollections.observableArrayList();

	public ObservableList<Unit> getUnits() {
		return units;
	}
}
