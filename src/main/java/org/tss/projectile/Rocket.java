package org.tss.projectile;

import org.tss.base.MinmaxDoubleValue;
import org.tss.controller.Controller;
import org.tss.unit.Harmable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Rocket extends Projectile implements Harmable {

	private static final long serialVersionUID = 1L;

	public Rocket(Controller controller) {
		super(controller, 0.7, 0.9, 0.3, 2);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});

		Polygon poly = new Polygon(5, 0, 10, 20, 0, 20);
		poly.setFill(Color.RED);
		getChildren().add(poly);
	}

	@Override
	public void update(double deltaT) {
		if (getTarget() != null)
			setDestination(getTarget().getPosition());
		move(deltaT);
		rotate(deltaT);
		super.update(deltaT);
	}

	@Override
	public void harm(double value) {
		setHitPoints(getHitPoints() - value);
	}

	protected MinmaxDoubleValue hitPoints = new MinmaxDoubleValue(1);

	public final void setHitPoints(double value) {
		hitPoints.setCur(value);
	}

	public final double getHitPoints() {
		return hitPoints.getCur();
	}
}
