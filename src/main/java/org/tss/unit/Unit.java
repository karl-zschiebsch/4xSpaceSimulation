package org.tss.unit;

import java.util.ArrayList;

import org.tss.controller.Controller;
import org.tss.controller.Player;
import org.tss.entity.SpaceObject;
import org.tss.unit.modul.Modul;
import org.tss.value.PercentageValue;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class Unit extends SpaceObject implements Harmable, Iconifiable<Node> {

	private final ObservableList<Modul> modules = FXCollections.observableArrayList();
	private final ArrayList<Modul> removed = new ArrayList<>();

	protected Unit(Controller controller) {
		super(controller);
		getController().getUnits().add(this);

		hitPoints.addListener((observable, o, n) -> {
			if (n.doubleValue() <= 0)
				destruct();
		});
		modules.addListener(new ListChangeListener<Modul>() {
			@Override
			public void onChanged(Change<? extends Modul> c) {
				c.next();
				removed.addAll(c.getRemoved());

				setHitPoints(modules.size() / (modules.size() + removed.size()));
			}
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
	public void update(double deltaT) {
		for (int i = 0; i < modules.size(); i++) {
			modules.get(i).update(deltaT);
		}
	}

	@Override
	public void destruct() {
		super.destruct();
		getController().getUnits().remove(this);
	}

	@Override
	public void harm(double value) {
		if (modules.isEmpty())
			return;
		Modul modul = modules.get((int) (Math.random() * modules.size()));
		modul.setHitPoints(modul.getHitPoints() - damagingHull(damagingShields(value)));
	}

	protected double damagingShields(double damage) {
		double excess = damage - getShieldPoints();
		setShieldPoints(getShieldPoints() - damage);
		return excess < 0 ? 0 : excess;
	}

	protected double damagingHull(double damage) {
		return damage / getArmorPoints();
	}

	public PercentageValue hitPoints = new PercentageValue(1);

	public final void setHitPoints(double value) {
		hitPoints.setCur(value);
	}

	public final double getHitPoints() {
		return hitPoints.getCur();
	}

	public PercentageValue armorPoints = new PercentageValue(1);

	public final void setArmorPoints(double value) {
		armorPoints.setCur(value);
	}

	public final double getArmorPoints() {
		return armorPoints.getCur();
	}

	public PercentageValue shieldPoints = new PercentageValue(0);

	public final void setShieldPoints(double value) {
		shieldPoints.setCur(value);
	}

	public final double getShieldPoints() {
		return shieldPoints.getCur();
	}

	public ObservableList<Modul> getModules() {
		return modules;
	}

	private transient Node icon;

	@Override
	public final Node getIcon() {
		if (icon == null) {
			icon = createIcon();
			addMouseEvents();
			addTouchEvents();
		}
		return icon;
	}

	@Override
	public void addMouseEvents() {
		Iconifiable.applyMouseEvents(icon, e -> {
			if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
				if (e.getButton() == MouseButton.PRIMARY) {
					getController().getMainController().getSelected().retainAll(this);
				}
				if (e.getButton() == MouseButton.SECONDARY) {
					getController().getMainController().getSelected().remove(this);
				}
			}
		});
	}
}
