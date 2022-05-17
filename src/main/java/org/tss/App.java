package org.tss;

import org.tss.controller.Player;
import org.tss.map.Map;
import org.tss.unit.linear.Linear;

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

					for (int i = 0; i < Player.CONTOLLER.size(); i++) {
						Player.CONTOLLER.get(i).update(deltaT);
					}
				}
			}
		}.start();
	}

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane();

		Group group = new Group();
		for (int x = 0; x < 1_200; x += 100) {
			group.getChildren().add(new Line(x, 0, x, 720));
		}
		for (int y = 0; y < 800; y += 100) {
			group.getChildren().add(new Line(0, y, 1080, y));
		}

		Map map = new Map(group, 1080, 720);
		Player player = new Player(stage);

		new Linear(player).place(map, 100, 200);
		new Linear(player).place(map, 300, 500);

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
