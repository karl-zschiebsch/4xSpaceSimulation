package org.tss.unit.linear;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Linear extends Unit {

	private static final long serialVersionUID = 7266084694106452548L;

	public Linear(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(20,0,40,60,0,60);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);
	}

	@Override
	public void update(double deltaT) {
		rotate(deltaT);
		move(deltaT);
	}

	@Override
	public void harm(double value) {
		hitPoints.set(value);
	}

}
