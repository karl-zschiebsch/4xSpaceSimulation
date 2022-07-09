package org.tss.unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;

public interface Iconifiable<T extends Node> {
	public T getIcon();

	T createIcon();

	public boolean isSupported();

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
