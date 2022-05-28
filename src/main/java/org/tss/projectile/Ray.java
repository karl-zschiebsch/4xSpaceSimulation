package org.tss.projectile;

import org.tss.controller.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ray extends Projectile {

	private static final long serialVersionUID = -9146952228367397633L;

	public Ray(Controller controller) {
		super(controller, 0.08, 2, 0.1, 1.4);

		Polygon poly = new Polygon(5, 0, 10, 20, 0, 20);
		poly.setFill(Color.RED);
		getChildren().add(poly);
	}

	@Override
	public void update(double deltaT) {
		if (getTarget() != null)
			setDestination(getTarget().getPosition());
		move(deltaT);
		super.update(deltaT);
	}

}
