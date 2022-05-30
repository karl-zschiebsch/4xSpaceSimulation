package org.tss.unit.station;

import java.util.ArrayList;

import org.tss.controller.Controller;
import org.tss.unit.Unit;
import org.tss.unit.UnitBuilder;

public class Station extends Unit {

	private final ArrayList<UnitBuilder> buildings = new ArrayList<>();
	private final ArrayList<UnitBuilder> progress = new ArrayList<>();

	protected Station(Controller controller) {
		super(controller);
	}

	@Override
	public final void move(double deltaT) {

	}

	@Override
	public final void rotate(double deltaT) {

	}

	public void progress(double deltaT) {
		for (int i = 0; i < progress.size(); i++) {
			progress.get(i).update(deltaT);
		}
	}

	public ArrayList<UnitBuilder> getBuildings() {
		return buildings;
	}

	public ArrayList<UnitBuilder> getProgress() {
		return progress;
	}
}
