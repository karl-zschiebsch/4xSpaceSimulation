package org.tss.unit.ship;

import java.util.ArrayList;

import org.tss.controller.Controller;
import org.tss.map.Map;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class Squadron<E extends Unit> extends Unit {

	private static final long serialVersionUID = 5980057030621705715L;

	private final ObservableList<E> subunits = FXCollections.observableArrayList();
	private final ArrayList<E> removed = new ArrayList<>();

	public Squadron(Controller controller) {
		super(controller);
		subunits.addListener(new ListChangeListener<E>() {
			@Override
			public void onChanged(Change<? extends E> c) {
				c.next();
				removed.addAll(c.getRemoved());
				double cur = subunits.size(), total = cur + removed.size();
				setHitPoints(cur / total);
			}
		});

		Rectangle rect = new Rectangle(30, 30, Color.GREEN);
		rect.setArcHeight(10);
		rect.setArcWidth(10);
		getChildren().add(rect);
	}

	@Override
	public void update(double deltaT) {
		for (E e : subunits) {
			e.setDestination(getDestination());
			e.setTarget(getTarget());
		}

		if (!subunits.isEmpty()) {
			move(deltaT);
			rotate(deltaT);
		}
	}

	@Override
	public void harm(double value) {
		E unit = subunits.get((int) (Math.random() * subunits.size()));
		unit.harm(value);
	}

	@Override
	public void move(double deltaT) {
		double curX = 0, curY = 0;

		for (E e : subunits) {
			Point2D p = e.getPosition();
			curX += p.getX();
			curY += p.getY();
		}

		setLayoutX(curX / subunits.size() - getWidth() / 2);
		setLayoutY(curY / subunits.size() - getHeight() / 2);
	}

	@Override
	public void rotate(double deltaT) {

	}

	@Override
	public void place(Map map, double x, double y, double r) {
		for (E e : subunits) {
			e.place(map, x, y, r);
		}
		super.place(map, x, y, 0);
	}

	public void pack(Squadron<E> stack) {
		if (stack.getChildren().size() > 0)
			subunits.addAll(stack.getSubUnits());
		stack.destruct();
	}

	public Squadron<E> split() {
		Squadron<E> stack = new Squadron<E>(getController());
		for (E e : subunits.subList(0, (int) (subunits.size() / 2))) {
			stack.getSubUnits().add(e);
			getSubUnits().remove(e);
		}
		return stack;
	}

	public ObservableList<E> getSubUnits() {
		return subunits;
	}
}
