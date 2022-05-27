package org.tss;

import org.tss.controller.Controller;
import org.tss.controller.Player;
import org.tss.map.Map;
import org.tss.unit.ship.Defender;
import org.tss.unit.ship.Hunter;
import org.tss.unit.ship.Squadron;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class App extends Application {

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
				}
			}
		}.start();
	}

	Group group = new Group();
	Map map = new Map(group, 1080, 720);

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane();

		for (int x = 100; x < 1_100; x += 100) {
			group.getChildren().add(new Line(x, 0, x, 800));
		}
		for (int y = 100; y < 800; y += 100) {
			group.getChildren().add(new Line(0, y, 1200, y));
		}

		Player player = new Player(map);

		Squadron<Hunter> squad = new Squadron<>(player);
		for (int i = 0; i < 3; i++) {
			squad.getSubUnits().add(new Hunter(squad));
		}
		squad.place(map, 600, 200);
		new Defender(player).place(map, 300, 500);

		new Defender(new Controller(map)).place(map, 100, 200);

		pane.setCenter(map);

		Scene scene = new Scene(pane);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, player.getKeyHandle());
		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, player.getMouseHandle());
		scene.setCamera(player.getCamera());

		stage.setScene(scene);
		stage.setIconified(false);
		stage.show();
	}
}
