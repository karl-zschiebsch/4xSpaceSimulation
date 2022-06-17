package org.tss.controller;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import org.tss.entity.Destructable;
import org.tss.entity.Entity;
import org.tss.unit.Unit;
import org.tss.value.HashTable;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Controller implements Entity, Destructable, Serializable {

	private static final long serialVersionUID = -7771921930095513632L;

	protected transient final ObservableList<Unit> units = FXCollections.observableArrayList();

	protected final HashTable<ResourceType, String, Double> resources;

	private final Party party;

	public Controller(Party party) {
		this.resources = new HashTable<>(EnumSet.allOf(ResourceType.class), Set.of("resource", "upkeep"));
		this.party = party;

		party.getMembers().add(this);
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
	public void update(double deltaT) {

	}

	@Override
	public void destruct() {
		party.getMembers().remove(this);
	}

	private static Player main;

	protected void setMainController(Player player) {
		if (main != null)
			throw new UnsupportedOperationException();
		main = player;
	}

	public Player getMainController() {
		return main;
	}

	public boolean isMainController() {
		return this == main;
	}

	public ObservableList<Unit> getUnits() {
		return units;
	}

	/**
	 * @implNote rows -> {@code EnumSet.allOf(ResourceType.class)}
	 * @implNote columns -> {@code Set.of("resource", "upkeep")}
	 */
	public HashTable<ResourceType, String, Double> getResourceTable() {
		return resources;
	}

	public Party getParty() {
		return party;
	}
}
