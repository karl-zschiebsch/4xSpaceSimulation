package org.tss.controller;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.S;

import java.util.EnumMap;
import java.util.function.Consumer;

import org.tss.map.Map;
import org.tss.unit.Unit;
import org.tss.unit.UnitBuilder;
import org.tss.unit.station.Station;

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
import javafx.scene.layout.VBox;

public class Player extends Controller {

	protected static final EnumMap<KeyCode, Consumer<Player>> SHORTCUTS = new EnumMap<>(KeyCode.class);
	static {
		SHORTCUTS.put(A, c -> c.selected.setAll(c.units));
		SHORTCUTS.put(S, c -> c.selected.forEach(u -> u.setDestination(null)));
	}

	protected final ObservableList<Unit> selected = FXCollections.observableArrayList();

	private final VBox overview = new VBox(10);
	private final Map map;

	public Player(Party party, Map map) {
		super(party);
		this.map = map;

		selected.addListener(new ListChangeListener<Unit>() {
			@Override
			public void onChanged(Change<? extends Unit> c) {
				c.next();
				modified: {
					if (c.getList().size() == 1) {
						Unit u = c.getList().get(0);
						if (u instanceof Station) {
							for (UnitBuilder unit : ((Station) u).getBuildings()) {

							}
							break modified;
						}
					}
					for (Unit unit : c.getRemoved()) {
						overview.getChildren().remove(unit.getIconified());
					}
					for (Unit unit : c.getAddedSubList()) {
						overview.getChildren().add(unit.getIconified());
					}
					break modified;
				}
			}
		});
		try {
			setMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.setCamera(camera);
	}

	private final Camera camera = new ParallelCamera();

	public void centralize(Point2D z) {
		camera.setLayoutX(z.getX() - map.getWidth() / 2);
		camera.setLayoutY(z.getY() - map.getHeight() / 2);
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

	public VBox getOverview() {
		return overview;
	}

	public Camera getCamera() {
		return camera;
	}
}
