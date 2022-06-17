package org.tss.controller;

import org.tss.map.Map;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Party {

	private transient final ObservableList<Controller> members = FXCollections.observableArrayList();

	private transient final Map map;

	public Party(Map map) {
		this.map = map;

		members.addListener(new ListChangeListener<Controller>() {
			@Override
			public void onChanged(Change<? extends Controller> c) {
				c.next();
				for (Controller controller : c.getAddedSubList()) {
					map.getControllers().add(controller);
				}
				for (Controller controller : c.getRemoved()) {
					map.getControllers().remove(controller);
				}
			}
		});
	}

	public ObservableList<Controller> getMembers() {
		return members;
	}

	public Map getMap() {
		return map;
	}
}
