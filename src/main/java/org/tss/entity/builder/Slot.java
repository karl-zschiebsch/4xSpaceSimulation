package org.tss.entity.builder;

import org.tss.entity.Constructor;
import org.tss.entity.Entity;
import org.tss.unit.Iconifiable;
import org.tss.unit.Unit;
import org.tss.value.DoubleCounter;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Slot implements Entity, Iconifiable<Node> {

	private Builder builder;
	private Constructor<?> constructor;
	private DoubleCounter counter;

	public Slot(Builder builder, Constructor<?> constructor, double time) {
		this.builder = builder;
		this.constructor = constructor;
		this.counter = new DoubleCounter(time);
	}

	@Override
	public void update(double deltaT) {
		counter.up(deltaT);
		if (counter.hasReached()) {
			Unit unit = (Unit) builder;
			constructor.create(u -> u.place(unit.getMap(), unit.getLayoutX(), unit.getLayoutY()));
			builder.getSlots().remove(this);
		}
	}

	void add() {
		builder.getSlots().add(this);
	}

	void cancel() {
		builder.getSlots().remove(this);
	}

	private transient Node icon;

	@Override
	public Node getIcon() {
		if (icon == null) {
			icon = createIcon();
			addMouseEvents();
			addTouchEvents();
		}
		return icon;
	}

	@Override
	public Node createIcon() {
		return new Rectangle(20, 20, Color.DARKMAGENTA);
	}

	@Override
	public void addMouseEvents() {
		Iconifiable.applyMouseEvents(icon, e -> {
			if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
				if (e.getButton() == MouseButton.PRIMARY) {
					add();
				}
				if (e.getButton() == MouseButton.SECONDARY) {
					cancel();
				}
			}
		});
	}
}
