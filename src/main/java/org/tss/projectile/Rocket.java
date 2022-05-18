package org.tss.projectile;

import org.tss.controller.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Rocket extends Projectile {

	private static final long serialVersionUID = 1L;

	public Rocket(Controller controller) {
		super(controller);
		
		Polygon poly = new Polygon(10,0,20,30,0,30);
		poly.setFill(Color.RED);
		getChildren().add(poly);
	}

	@Override
	public void update(double deltaT) {
		if (getTarget() != null) setDestination(getTarget().getPosition());
		move(deltaT);
		rotate(deltaT);
		super.update(deltaT);
	}
}
