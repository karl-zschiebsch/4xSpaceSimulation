package org.tss;

import org.tss.controller.Controller;
import org.tss.controller.Party;
import org.tss.controller.Player;
import org.tss.controller.ResourceType;
import org.tss.map.Map;
import org.tss.unit.ship.Carrier;
import org.tss.unit.ship.Defender;
import org.tss.unit.station.Headquarter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
					for (int j = 0; j < map.getControllers().size(); j++) {
						map.getControllers().get(j).update(1);
					}
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
	Player player = new Player(new Party(map));

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane();

		player.getResourceTable().put(ResourceType.CREDIT, "resource", 5000.0);
		player.getResourceTable().put(ResourceType.CREDIT, "upkeep", 10.0);
		new Headquarter(player).place(map, 100, 600);
		Carrier carrier = new Carrier(player);
		carrier.place(map, 200, 600);

		Controller opponent = new Controller(new Party(map));
		new Defender(opponent).place(map, 400, 200);
		new Defender(opponent).place(map, 700, 300);

		StackPane stack = new StackPane();
		Rectangle rect = new Rectangle(1080, 720, Color.WHITE);
		rect.setOpacity(0);
		rect.addEventHandler(MouseEvent.MOUSE_CLICKED, player.getMouseHandle());
		stack.getChildren().addAll(rect, map);

		pane.setCenter(stack);
		pane.setLeft(player.getOverview());
		pane.setRight(player.getOptions());

		Scene scene = new Scene(pane);
		scene.setOnKeyPressed(player.getKeyHandle());

		stage.setScene(scene);
		stage.setIconified(false);
		stage.show();
	}
}
