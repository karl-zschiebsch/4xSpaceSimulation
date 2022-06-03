package org.tss.controller;

import java.util.EnumMap;

import org.tss.controller.ResourceType.ResourceCost;
import org.tss.entity.Destructable;
import org.tss.entity.Entity;
import org.tss.unit.Unit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Controller implements Entity, Destructable {

	protected final ObservableList<Unit> units = FXCollections.observableArrayList();

	protected final EnumMap<ResourceType, DoubleProperty> resources = new EnumMap<>(ResourceType.class);
	protected final EnumMap<ResourceType, DoubleProperty> upkeep = new EnumMap<>(ResourceType.class);

	private final Party party;

	public Controller(Party party) {
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
		for (EnumMap.Entry<ResourceType, DoubleProperty> entry : upkeep.entrySet()) {
			ResourceType key = entry.getKey();
			DoubleProperty val = entry.getValue();

			DoubleProperty p = resources.get(key);
			if (p == null) {
				resources.put(key, new SimpleDoubleProperty(val.get() * deltaT));
			} else {
				p.set(p.get() + val.get() * deltaT);
			}
		}
	}

	@Override
	public void destruct() {
		party.getMembers().remove(this);
	}

	public void addUpkeep(ResourceCost cost) {
		DoubleProperty p = upkeep.get(cost.type());
		if (p == null) {
			upkeep.put(cost.type(), new SimpleDoubleProperty(cost.costs()));
		} else {
			p.set(p.get() + cost.costs());
		}
	}

	public void removeUpkeep(ResourceCost cost) {
		DoubleProperty p = upkeep.get(cost.type());
		if (p == null) {
			upkeep.put(cost.type(), new SimpleDoubleProperty(-cost.costs()));
		} else {
			p.set(p.get() - cost.costs());
		}
	}

	public boolean canPaid(ResourceCost r) {
		DoubleProperty v = getResources().get(r.type());
		if (v != null) {
			if (v.get() >= r.costs()) {
				return true;
			}
		}
		resources.put(r.type(), new SimpleDoubleProperty());
		return false;
	}

	public boolean pay(ResourceCost r) {
		DoubleProperty v = getResources().get(r.type());
		if (v != null) {
			if (v.get() >= r.costs()) {
				v.set(v.get() - r.costs());
				return true;
			}
		}
		return false;
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

	public ObservableList<Unit> getUnits() {
		return units;
	}

	public EnumMap<ResourceType, DoubleProperty> getResources() {
		return resources;
	}

	public Party getParty() {
		return party;
	}
}
