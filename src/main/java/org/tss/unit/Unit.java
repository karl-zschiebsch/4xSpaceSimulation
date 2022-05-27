package org.tss.unit;

import org.tss.base.MinmaxDoubleValue;
import org.tss.base.SpaceObject;
import org.tss.controller.Controller;
import org.tss.controller.Player;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class Unit extends SpaceObject implements Harmable {

	private static final long serialVersionUID = 8147204360521291943L;

	protected Unit(Controller controller) {
		super(controller);
		getController().getUnits().add(this);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0)
				destruct();
		});
		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.PRIMARY && getController().isMainController()) {
				Player player = (Player) getController();
				if (e.getClickCount() == 2)
					player.centralize(getPosition());
				if (!e.isShiftDown())
					player.getSelected().clear();
				if (!player.getSelected().contains(this))
					player.getSelected().add(this);
			} else if (e.getButton() == MouseButton.SECONDARY && !getController().isMainController()) {
				for (Unit unit : getController().getMainController().getSelected())
					unit.setTarget(this);
			}
		});
	}

	@Override
	public void destruct() {
		super.destruct();
		getController().getUnits().remove(this);
	}

	protected double damagingShields(double damage) {
		double excess = damage - getShieldPoints();
		setShieldPoints(getShieldPoints() - damage);
		return excess < 0 ? 0 : excess;
	}

	protected double damagingHull(double damage) {
		return damage / getArmorPoints();
	}

	protected MinmaxDoubleValue hitPoints = new MinmaxDoubleValue(1);

	public final void setHitPoints(double value) {
		hitPoints.setCur(value);
	}

	public final double getHitPoints() {
		return hitPoints.getCur();
	}

	protected MinmaxDoubleValue armorPoints = new MinmaxDoubleValue(1);

	public final void setArmorPoints(double value) {
		armorPoints.setCur(value);
	}

	public final double getArmorPoints() {
		return armorPoints.getCur();
	}

	protected MinmaxDoubleValue shieldPoints = new MinmaxDoubleValue(1);

	public final void setShieldPoints(double value) {
		shieldPoints.setCur(value);
	}

	public final double getShieldPoints() {
		return shieldPoints.getCur();
	}
}
