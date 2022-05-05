package org.tss.base;

import org.tss.controller.Controller;
import org.tss.map.Map;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public abstract class SpaceObject extends Pane implements Entity, Constructable, Placeable {

	private static final long serialVersionUID = 2581581743285122661L;

	private final Controller controller;

	protected SpaceObject(Controller controller) {
		this.controller = controller;

		construct();
	}

	private Map map;

	@Override
	public void place(Map map, double x, double y) {
		this.map = map;
		setLayoutX(x);
		setLayoutY(y);
		map.getObjects().add(this);
	}

	@Override
	public void remove() {
		map.getObjects().remove(this);
	}

	public Point2D getPosition() {
		return new Point2D(getLayoutX(), getLayoutY());
	}

	private Point2D target;

	public Point2D getTarget() {
		return target == null ? getPosition() : target;
	}

	public void setTarget(Point2D target) {
		this.target = target;
	}

	public void move(double deltaT) {
		double difX = getTarget().getX() - getPosition().getX();
		double difY = getTarget().getY() - getPosition().getY();

		double length = Math.hypot(difX, difY);

		if (length > Math.max(deltaT, 10)) {
			double tem = deltaT * 20;
			if (tem >= length) {
				tem = length;
			}
			setLayoutX(getLayoutX() + Math.sin(getRotate() / 180 * Math.PI) * tem);
			setLayoutY(getLayoutY() + -Math.cos(getRotate() / 180 * Math.PI) * tem);
		} else {
			setTarget(null);
		}
	}

	public void rotate(double deltaT) {
		double difX = getTarget().getX() - getPosition().getX();
		double difY = getTarget().getY() - getPosition().getY();

		double alpha = -Math.atan2(-difY, difX) * 180 / Math.PI + 90;
		double length = Math.hypot(difX, difY);

		if (length > Math.max(deltaT, 10)) {

			double difR = alpha - getRotate();
			if (difR > 180) {
				difR -= 360;
			}
			if (difR < -180) {
				difR += 360;
			}
			if (difR > .5 || difR < -.5) {
				double ang = deltaT * 20;
				if (ang > Math.abs(difR)) {
					setRotate(getRotate() + difR);
				} else {
					setRotate(getRotate() + Math.signum(difR) * ang);
				}
			}
		}
	}

	public Controller getController() {
		return controller;
	}

	public Map getMap() {
		return map;
	}
}
