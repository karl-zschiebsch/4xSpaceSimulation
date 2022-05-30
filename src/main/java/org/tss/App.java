package org.tss;

import java.util.EnumMap;

import org.tss.controller.Controller;
import org.tss.controller.Player;
import org.tss.controller.ResourceType;
import org.tss.controller.ResourceType.ResourceCost;
import org.tss.map.Map;
import org.tss.unit.ship.Carrier;
import org.tss.unit.ship.Defender;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class App extends Application {

	StringBinding resource = new StringBinding() {
		@Override
		protected String computeValue() {
			StringBuilder string = new StringBuilder();
			for (EnumMap.Entry<ResourceType, DoubleProperty> entry : player.getResources().entrySet()) {
				ResourceType key = entry.getKey();
				DoubleProperty val = entry.getValue();

				string.append("[" + key + "] : " + val.getValue().intValue() + "\n");
			}
			return string.toString();
		}
	};

	@Override
	public void init() throws Exception {
		new AnimationTimer() {
			final long[] frameTimes = new long[100];
			int frameTimeIndex = 0;
			boolean arrayFilled = false;

			@Override
			public void handle(long now) {
				long oldFrameTime = frameTimes[frameTimeIndex];
				frameTimes[frameTimeIndex] = now;
				frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
				if (frameTimeIndex == 0) {
					arrayFilled = true;
				}
				if (arrayFilled) {
					long elapsedNanos = now - oldFrameTime;
					long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
					double frameRate = 1_000_000_000 / elapsedNanosPerFrame;
					double deltaT = frameRate / 10_000;

					for (int i = 0; i < map.getObjects().size(); i++) {
						map.getObjects().get(i).update(deltaT);
					}

					for (int j = 0; j < map.getControllers().size(); j++) {
						map.getControllers().get(j).update(deltaT);
					}
					resource.invalidate();
				}
			}
		}.start();
	}

	Group group = new Group();
	Map map = new Map(group, 1080, 720);
	Player player = new Player(map);

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane();

		for (int x = -200; x <= 1_800; x += 100) {
			group.getChildren().add(new Line(x, -200, x, 1_000));
		}
		for (int y = -200; y <= 1_000; y += 100) {
			group.getChildren().add(new Line(-200, y, 1_800, y));
		}

		player.addUpkeep(new ResourceCost(ResourceType.CREDIT, 10.0));
		Controller opponent = new Controller(map);

		new Carrier(opponent).place(map, 200, 600);

		new Defender(player).place(map, 400, 200);
		new Defender(player).place(map, 700, 300);

		pane.setCenter(map);

		Label label = new Label();
		label.textProperty().bind(resource);
		pane.setTop(label);

		Scene scene = new Scene(pane);
		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, player.getMouseHandle());
		scene.addEventFilter(KeyEvent.KEY_PRESSED, player.getKeyHandle());

		stage.setScene(scene);
		stage.setIconified(false);
		stage.show();
	}
}
