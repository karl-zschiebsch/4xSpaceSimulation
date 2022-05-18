package org.tss.unit;

import org.tss.base.ModalDoubleValue;
import org.tss.base.SpaceObject;
import org.tss.controller.Controller;
import org.tss.controller.Player;

import javafx.scene.input.MouseEvent;

public abstract class Unit extends SpaceObject implements Harmable {

	private static final long serialVersionUID = 8147204360521291943L;

	protected Unit(Controller controller) {
		super(controller);
		getController().getUnits().add(this);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0) {
				destruct();
			}
		});
		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (getController().isMainController()) {
				Player player = (Player) getController();
				if (e.getClickCount() == 2) {
					player.centralize(getPosition());
				}
				if (!e.isShiftDown()) {
					player.getSelected().clear();
				}
				if (player.getSelected().contains(this))
					return;
				player.getSelected().add(this);
			} else {
				for (Unit unit : getController().getMainController().getSelected()) {
					unit.setTarget(this);
				}
			}
		});
	}

	@Override
	public void destruct() {
		super.destruct();
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
