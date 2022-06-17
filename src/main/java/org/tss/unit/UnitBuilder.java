//package org.tss.unit;
//
//import java.util.function.Function;
//
//import org.tss.controller.Controller;
//import org.tss.controller.ResourceType;
//import org.tss.controller.ResourceType.ResourceCost;
//import org.tss.entity.Constructor;
//import org.tss.entity.Entity;
//import org.tss.unit.ship.Carrier;
//import org.tss.unit.station.Station;
//import org.tss.value.DoubleCounter;
//import org.tss.value.PercentageValue;
//
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//public class UnitBuilder extends Pane implements Entity, Cloneable {
//
//	private final Station reference;
//	private final Constructor<? extends Unit> launcher;
//	private final ResourceCost cost;
//	private final PercentageValue progress = new PercentageValue(1);
//	private final DoubleCounter timer;
//
//	public UnitBuilder(Station reference, Constructor<? extends Unit> launcher, ResourceCost cost, double time) {
//		this.reference = reference;
//		this.launcher = launcher;
//		this.cost = cost;
//		this.timer = new DoubleCounter(time);
//
//		// TODO add progress circle
//
//		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//			System.out.println("reached");
//			if (e.getButton() == MouseButton.PRIMARY) {
//				addBuilder();
//				System.out.println("add");
//			}
//			if (e.getButton() == MouseButton.SECONDARY) {
//				cancelBuilder();
//				System.out.println("cancel");
//			}
//		});
//	}
//
//	@Override
//	public void update(double deltaT) {
//		if (timer.hasReached()) {
//			System.out.println("build");
//			launcher.create(reference.getController(), u -> u.place(reference.getMap(), reference.getPosition().getX(),
//					reference.getPosition().getY(), reference.getRotate()));
//			reference.getProgress().remove(this);
//		} else {
//			timer.up(deltaT);
//			System.out.println("left: " + (timer.getUp() - timer.getValue()));
//		}
//		progress.setCur(timer.getValue() / timer.getUp());
//	}
//
//	public boolean addBuilder() {
//		Controller controller = reference.getController();
//		if (controller.canPay(cost)) {
////			try {
//			reference.getProgress().add(new UnitBuilder(reference, launcher, cost, timer.getUp()));
//			System.out.println(reference.getProgress());
////			} catch (CloneNotSupportedException ex) {
////				ex.printStackTrace();
////			}
//			controller.pay(cost);
//			return true;
//		}
//		return false;
//	}
//
//	public boolean cancelBuilder() {
//		return false;
//	}
//
//	@Override
//	public UnitBuilder clone() throws CloneNotSupportedException {
//		return new UnitBuilder(reference, launcher, cost, timer.getUp());
//	}
//
//	/**
//	 * UnitBuilder builder = new UnitBuilder(this, new Constructor<Carrier>(new
//	 * Function<Controller, Carrier>() {
//	 * 
//	 * @Override public Carrier apply(Controller t) { return new Carrier(t); } }),
//	 *           new ResourceCost(ResourceType.CREDIT, 30), 20);
//	 *           builder.getChildren().add(new Rectangle(20, 20, Color.BLUEVIOLET));
//	 * 
//	 *           getBuildings().add(builder);
//	 */
//}
