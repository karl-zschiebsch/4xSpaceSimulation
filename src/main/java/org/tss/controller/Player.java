package org.tss.controller;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.S;

import java.util.EnumMap;
import java.util.function.Consumer;

import org.tss.map.Map;
import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Player extends Controller {

	protected static final EnumMap<KeyCode, Consumer<Player>> SHORTCUTS = new EnumMap<>(KeyCode.class);
	static {
		SHORTCUTS.put(A, c -> c.selected.setAll(c.units));
		SHORTCUTS.put(S, c -> c.selected.forEach(u -> u.setDestination(null)));
	}

	protected final ObservableList<Unit> selected = FXCollections.observableArrayList();

	public Player(Map map) {
		super(map);

		try {
			setMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final Camera camera = new ParallelCamera();

	public void centralize(Point2D z) {
		camera.setLayoutX(z.getX() - getMap().getWidth() / 2);
		camera.setLayoutY(z.getY() - getMap().getHeight() / 2);
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

	private int s = 0;

	public EventHandler<? super MouseEvent> getMouseHandle() {
		return e -> {
			switch (e.getButton()) {
			case PRIMARY:

				break;
			case SECONDARY:
				for (Unit unit : selected) {
					unit.setDestination(
							new Point2D(e.getSceneX() + camera.getLayoutX(), e.getSceneY() + camera.getLayoutY()));
				}
				break;
			case MIDDLE:

				break;
			case FORWARD:
				if (selected.isEmpty())
					break;
				s = (selected.size() - 1 == s) ? 0 : s + 1;
				centralize(selected.get(s).getPosition());
				break;
			case BACK:
				if (selected.isEmpty())
					break;
				s = (0 == s) ? selected.size() - 1 : s - 1;
				centralize(selected.get(s).getPosition());
				break;
			default:
				break;
			}
		};
	}

	public ObservableList<Unit> getSelected() {
		return selected;
	}

	public Camera getCamera() {
		return camera;
	}
}
