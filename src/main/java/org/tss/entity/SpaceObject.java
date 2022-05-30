package org.tss.entity;

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
import org.tss.map.Placeable;
import org.tss.unit.Unit;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public abstract class SpaceObject extends Pane implements Entity, Placeable, Destructable {

	private final Controller controller;

	protected SpaceObject(Controller controller) {
		this.controller = controller;
	}

	private Map map;

	@Override
	public void place(Map map, double x, double y, double r) {
		this.map = map;
		setLayoutX(x - getWidth() / 2);
		setLayoutY(y - getHeight() / 2);
		setRotate(r);
		map.getObjects().add(this);
	}

	@Override
	public void destruct() {
		map.getObjects().remove(this);
	}

	public Point2D getPosition() {
		return new Point2D(getLayoutX() + getWidth() / 2, getLayoutY() + getHeight() / 2);
	}

	private Point2D destination;

	public Point2D getDestination() {
		return destination == null ? getPosition() : destination;
	}

	public void setDestination(Point2D destination) {
		this.destination = destination;
	}

	private Unit target;

	public Unit getTarget() {
		if (target != null)
			if (target.getHitPoints() <= 0)
				target = null;
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
	}

	public void move(double deltaT) {
		double difX = getPosition().getX() - getDestination().getX();
		double difY = getPosition().getY() - getDestination().getY();
		double length = hypot(difX, difY);
		if (length > max(deltaT, 5)) {
			double tem = deltaT * 64;
			if (tem >= length) {
				tem = length;
			}
			setLayoutX(getLayoutX() + sin(toRadians(getRotate())) * tem);
			setLayoutY(getLayoutY() + -cos(toRadians(getRotate())) * tem);
		} else {
			setDestination(null);
		}
	}

	public void rotate(double deltaT) {
		double difX = getPosition().getX() - getDestination().getX();
		double difY = getPosition().getY() - getDestination().getY();
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
				double ang = deltaT * 16;
				if (ang > abs(difR)) {
					setRotate(getRotate() + difR);
				} else {
					setRotate(getRotate() + signum(difR) * ang);
				}
			}
		}
	}

	public boolean inside(SpaceObject o) {
		return o.getBoundsInParent().contains(getPosition());
	}

	public Controller getController() {
		return controller;
	}

	@Override
	public Map getMap() {
		return map;
	}
}
