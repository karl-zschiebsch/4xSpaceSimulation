package org.tss.unit;

import javafx.scene.Node;

public interface Iconifiable<T extends Node> {
	public T getIcon();

	abstract T createIcon();

	public boolean isSupported();
}
