package org.tss.base;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.max;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import org.tss.controller.Controller;
import org.tss.map.Map;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public abstract class SpaceObject extends Pane implements Entity, Destructable, Placeable {

	private static final long serialVersionUID = 2581581743285122661L;

	private final Controller controller;

	protected SpaceObject(Controller controller) {
		this.controller = controller;
	}

	private Map map;

	@Override
	public void place(Map map, double x, double y) {
		this.map = map;
		setLayoutX(x + getWidth() / 2);
		setLayoutY(y + getHeight() / 2);
		map.getObjects().add(this);
	}

	@Override
	public void remove() {
		map.getObjects().remove(this);
	}

	public Point2D getPosition() {
		return new Point2D(getLayoutX() + getWidth() / 2, getLayoutY() + getHeight() / 2);
	}

	private Point2D target;

	public Point2D getTarget() {
		return target == null ? getPosition() : target;
	}

	public void setTarget(Point2D target) {
		this.target = target;
	}

	public void move(double deltaT) {
		double difX = getPosition().getX() - getTarget().getX();
		double difY = getPosition().getY() - getTarget().getY();
		double length = hypot(difX, difY);
		if (length > max(deltaT, 5)) {
			double tem = deltaT * 20;
			if (tem >= length) {
				tem = length;
			}
			setLayoutX(getLayoutX() + sin(toRadians(getRotate())) * tem);
			setLayoutY(getLayoutY() + -cos(toRadians(getRotate())) * tem);
		} else {
			setTarget(null);
		}
	}

	public void rotate(double deltaT) {
		double difX = getPosition().getX() - getTarget().getX();
		double difY = getPosition().getY() - getTarget().getY();
		double alpha = -toDegrees(atan2(difX, difY));
		double length = hypot(difX, difY);
		if (length > max(deltaT, 5)) {
			double difR = alpha - getRotate();
			if (difR > 180) {
				difR -= 360;
			}
			if (difR < -180) {
				difR += 360;
			}
			if (difR > .5 || difR < -.5) {
				double ang = deltaT * 20;
				if (ang > abs(difR)) {
					setRotate(getRotate() + difR);
				} else {
					setRotate(getRotate() + signum(difR) * ang);
				}
			}
		}
	}

	@Override
	public String toString() {
		return getTarget() + " :: " + getPosition() + " :: " + getRotate();
	}

	public Controller getController() {
		return controller;
	}

	public Map getMap() {
		return map;
	}
}
