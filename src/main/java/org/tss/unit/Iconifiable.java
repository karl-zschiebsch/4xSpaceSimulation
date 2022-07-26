package org.tss.unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

public interface Iconifiable<T extends Node> {
	public T getIcon();

	T createIcon();

	default void addMouseEvents() {

	}

	default void addTouchEvents() {

	}

	@SafeVarargs
	static Node applyMouseEvents(Node node, EventHandler<? super MouseEvent>... events) {
		for (EventHandler<? super MouseEvent> event : events) {
			node.addEventFilter(MouseEvent.ANY, event);
		}
		return node;
	}

	@SafeVarargs
	static Node applyTouchEvents(Node node, EventHandler<? super TouchEvent>... events) {
		for (EventHandler<? super TouchEvent> event : events) {
			node.addEventFilter(TouchEvent.ANY, event);
		}
		return node;
	}

	public default boolean isSupported() {
		return true;
	}

	public static class ResourceLoader {
		private static final Map<String, Image> IMAGES = new HashMap<>();

		private static Image loadImage(String location) {
			try (FileInputStream stream = new FileInputStream(location)) {
				Image image = new Image(stream);
				IMAGES.put(location, image);
				return image;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public static Image getImage(String location) {
			var image = IMAGES.get(location);
			if (image == null) {
				image = loadImage(location);
			}
			return image;
		}

		private static final Map<String, String> FILES = new HashMap<>();

		private static String loadFile(String location) {
			try (FileInputStream stream = new FileInputStream(location)) {
				String file = new String(stream.readAllBytes());
				FILES.put(location, file);
				return file;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public static String getFile(String location) {
			var file = FILES.get(location);
			if (file == null) {
				file = loadFile(location);
			}
			return file;
		}
	}
}
