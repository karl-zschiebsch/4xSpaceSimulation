package org.tss.unit;

import org.tss.base.ModalDoubleValue;
import org.tss.base.SpaceObject;
import org.tss.controller.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public abstract class Unit extends SpaceObject implements Harmable {

	private static final long serialVersionUID = 8147204360521291943L;

	protected Unit(Controller controller) {
		super(controller);

		Polygon poly = new Polygon(0, 0, 60, 50, -60, 50);
		poly.setFill(Color.BLUE);
		getChildren().add(poly);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
	}

	@Override
	public void construct() {
		getController().getUnits().add(this);
	}

	@Override
	public void destruct() {
		getController().getUnits().remove(this);
	}

	protected ModalDoubleValue hitPoints = new ModalDoubleValue(1);

	public final void setHitPoints(double value) {
		hitPoints.set(value);
	}

	public final double getHitPoints() {
		return hitPoints.get();
	}

	protected ModalDoubleValue armorPoints = new ModalDoubleValue(1);

	public final void setArmorPoints(double value) {
		armorPoints.set(value);
	}

	public final double getArmorPoints() {
		return armorPoints.get();
	}

	protected ModalDoubleValue shieldPoints = new ModalDoubleValue(1);

	public final void setShieldPoints(double value) {
		shieldPoints.set(value);
	}

	public final double getShieldPoints() {
		return shieldPoints.get();
	}
}
