package org.tss.controller;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.S;

import java.util.EnumMap;
import java.util.function.Consumer;

import org.tss.unit.Unit;
import org.tss.unit.station.Station;
import org.tss.value.IntegerCounter;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Player extends Controller {

	private static final long serialVersionUID = -2070557564288348371L;

	protected static final EnumMap<KeyCode, Consumer<Player>> SHORTCUTS = new EnumMap<>(KeyCode.class);
	static {
		SHORTCUTS.put(A, c -> c.selected.setAll(c.units));
		SHORTCUTS.put(S, c -> c.selected.forEach(u -> u.setDestination(null)));
	}

	protected final ObservableList<Unit> selected = FXCollections.observableArrayList();

	private final VBox overview = new VBox(10);
	private final HBox options = new HBox(10);

	public Player(Party party) {
		super(party);

		selected.addListener(new ListChangeListener<Unit>() {
			@Override
			public void onChanged(Change<? extends Unit> c) {
				c.next();
				if (c.getList().size() == 1) {
					Unit u = c.getList().get(0);
					if (u instanceof Station) {
						// TODO add types to options
					}
				}

				for (Unit unit : c.getRemoved()) {
					if (unit.isSupported())
						overview.getChildren().remove(unit.getIcon());
				}
				for (Unit unit : c.getAddedSubList()) {
					if (unit.isSupported())
						overview.getChildren().add(unit.getIcon());
				}
			}
		});
		setMainController(this);
		getParty().getMap().setCamera(camera);
	}

	private final Camera camera = new ParallelCamera();

	public void centralize(Point2D z) {
		camera.setLayoutX(z.getX() - getParty().getMap().getWidth() / 2);
		camera.setLayoutY(z.getY() - getParty().getMap().getHeight() / 2);
	}

	public EventHandler<KeyEvent> getKeyHandle() {
		return e -> {
			if (e.isControlDown()) {
				Consumer<Player> c = SHORTCUTS.get(e.getCode());
				if (c != null)
					c.accept(this);
			} else
				switch (e.getCode()) {
				case W:
					camera.setLayoutY(camera.getLayoutY() - 10);
					break;
				case A:
					camera.setLayoutX(camera.getLayoutX() - 10);
					break;
				case S:
					camera.setLayoutY(camera.getLayoutY() + 10);
					break;
				case D:
					camera.setLayoutX(camera.getLayoutX() + 10);
					break;
				default:
					break;
				}
		};
	}

	private IntegerCounter centralized = new IntegerCounter(selected.size());

	public EventHandler<? super MouseEvent> getMouseHandle() {
		return e -> {
			switch (e.getButton()) {
			case PRIMARY:

				break;
			case SECONDARY:
				for (Unit unit : selected) {
					unit.setDestination(new Point2D(e.getX() + camera.getLayoutX(), e.getY() + camera.getLayoutY()));
				}
				break;
			case MIDDLE:

				break;
			case FORWARD:
				if (selected.isEmpty())
					break;
				centralized.up(1);
				if (centralized.hasReached()) {
					centralized.reset();
				}
				centralize(selected.get(centralized.getValue()).getPosition());
				break;
			case BACK:
				if (selected.isEmpty())
					break;
				centralized.down(1);
				if (centralized.getValue() < 0) {
					centralized.setValue(centralized.getUp());
				}
				centralize(selected.get(centralized.getValue()).getPosition());
				break;
			default:
				break;
			}
		};
	}

	public ObservableList<Unit> getSelected() {
		return selected;
	}

	public VBox getOverview() {
		return overview;
	}

	public HBox getOptions() {
		return options;
	}

	public Camera getCamera() {
		return camera;
	}
}
