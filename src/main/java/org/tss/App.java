package org.tss;

import org.tss.controller.Player;
import org.tss.map.Map;
import org.tss.unit.Unit;
import org.tss.unit.linear.Linear;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void init() throws Exception {
		AnimationTimer frameRateMeter = new AnimationTimer() {
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

					for (int i = 0; i < Player.PLAYERS.size(); i++) {
						Player.PLAYERS.get(i).update(deltaT);
					}
				}
			}
		};
		frameRateMeter.start();
	}

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane();

		Map map = new Map(new Group(), 200, 200);

		Unit unit = new Linear(new Player());
		unit.setTarget(new Point2D(0, 0));
		unit.place(map, 100, 100);

		pane.setCenter(map);

		stage.setScene(new Scene(pane));
		stage.setIconified(false);
		stage.show();
	}
}
