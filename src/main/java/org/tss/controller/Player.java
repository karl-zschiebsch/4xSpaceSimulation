package org.tss.controller;

import org.tss.base.Constructable;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Player extends Controller implements Constructable {

	public static final ObservableList<Player> PLAYERS = FXCollections.observableArrayList();

	private static final long serialVersionUID = -5595285092934056297L;

	public Player() {
		units.addListener(new ListChangeListener<Unit>() {

			@Override
			public void onChanged(Change<? extends Unit> c) {
				if (units.isEmpty()) {
					destruct();
				}
			}

		});
		construct();
	}
	
	@Override
	public void update(double deltaT) {
		for (int i = 0; i < getUnits().size(); i++) {
			getUnits().get(i).update(deltaT);
		}
	}

	@Override
	public void construct() {
		PLAYERS.add(this);
	}

	@Override
	public void destruct() {
		PLAYERS.remove(this);
	}

}
