package org.tss.unit.station;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Station extends Unit {

//	private final ArrayList<UnitBuilder> buildings = new ArrayList<>();
//	private final ArrayList<UnitBuilder> progress = new ArrayList<>();

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
//		for (int i = 0; i < progress.size(); i++) {
//			progress.get(i).update(deltaT);
//		}
	}

	@Override
	public Node createIcon() {
		return new Rectangle(20, 20, Color.GRAY);
	}

	@Override
	public boolean isSupported() {
		return true;
	}

//	public ArrayList<UnitBuilder> getBuildings() {
//		return buildings;
//	}
//
//	public ArrayList<UnitBuilder> getProgress() {
//		return progress;
//	}
}
