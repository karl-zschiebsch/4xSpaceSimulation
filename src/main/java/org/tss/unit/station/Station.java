package org.tss.unit.station;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tss.controller.Controller;
import org.tss.entity.builder.Builder;
import org.tss.entity.builder.Dev;
import org.tss.entity.builder.Slot;
import org.tss.unit.Unit;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Station extends Unit implements Builder {

	private Set<Dev> devs = new HashSet<>();
	private List<Slot> slots = new ArrayList<>();

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
		for (Slot slot : slots) {
			slot.update(deltaT);
		}
	}

	@Override
	public Node createIcon() {
		return new Rectangle(20, 20, Color.GRAY);
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public Set<Dev> getDevs() {
		return devs;
	}

	@Override
	public List<Slot> getSlots() {
		return slots;
	}
}
