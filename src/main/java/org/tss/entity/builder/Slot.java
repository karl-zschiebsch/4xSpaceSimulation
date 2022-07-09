package org.tss.entity.builder;

import org.tss.entity.Constructor;
import org.tss.entity.Entity;
import org.tss.value.DoubleCounter;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Slot extends Pane implements Entity {

	private Builder builder;
	private Constructor<?> constructor;
	private DoubleCounter counter;

	public Slot(Builder builder, Constructor<?> constructor, double time) {
		this.builder = builder;
		this.constructor = constructor;
		this.counter = new DoubleCounter(time);

		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cancel());
	}

	@Override
	public void update(double deltaT) {
		counter.up(deltaT);
		if (counter.hasReached()) {
			constructor.create();
		}
		builder.getSlots().remove(this);
	}

	void add() {
		builder.getSlots().add(this);
	}

	void cancel() {
		builder.getSlots().remove(this);
	}
}
