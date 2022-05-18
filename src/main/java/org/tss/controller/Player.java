package org.tss.controller;

import org.tss.unit.Unit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Player extends Controller {

	protected final ObservableList<Unit> selected = FXCollections.observableArrayList();

	private final Stage stage;

	public Player(Stage stage) {
		this.stage = stage;

		try {
			setMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final Camera camera = new ParallelCamera();

	public void centralize(Point2D z) {
		camera.setLayoutX(z.getX() - stage.getWidth() / 2);
		camera.setLayoutY(z.getY() - stage.getHeight() / 2);
	}

	public EventHandler<? super KeyEvent> getKeyHandle() {
		return e -> {
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
