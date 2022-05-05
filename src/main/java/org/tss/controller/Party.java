package org.tss.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Party extends Controller {

	private static final long serialVersionUID = 6660480150945799790L;

	private final ObservableList<Controller> members = FXCollections.observableArrayList();

	@Override
	public void update(double deltaT) {
		// defaultImpl
	}

	public ObservableList<Controller> getMembers() {
		return members;
	}
}
