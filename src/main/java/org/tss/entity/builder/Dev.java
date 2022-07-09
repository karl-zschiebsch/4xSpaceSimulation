package org.tss.entity.builder;

import java.util.EnumMap;

import org.tss.controller.ResourceType;
import org.tss.entity.Cloner;
import org.tss.unit.Iconifiable;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Dev implements Iconifiable<Node> {

	private Builder builder;
	private Cloner<Slot> cloner;

	private EnumMap<ResourceType, Double> price = new EnumMap<>(ResourceType.class);

	public Dev(Builder builder, Cloner<Slot> cloner, double... prices) {
		this.builder = builder;
		this.cloner = cloner;
		this.builder.getDevs().add(this);

		for (int i = 0; i < ResourceType.values().length; i++) {
			ResourceType key = ResourceType.values()[i];
			Double value = (i < prices.length) ? prices[i] : 0.0;
			price.put(key, value);
		}
	}

	void buy() {
		cloner.next().add();
	}

	private transient Node icon;

	@Override
	public Node getIcon() {
		if (icon == null) {
			icon = createIcon();
			icon.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> buy());
		}
		return icon;
	}

	@Override
	public Node createIcon() {
		return new Rectangle(20, 20, Color.CRIMSON);
	}

	@Override
	public boolean isSupported() {
		return true;
	}
}
