package org.tss.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Party {

	private final ObservableList<Controller> members = FXCollections.observableArrayList();
	
	public ObservableList<Controller> getMembers() {
		return members;
	}
}
