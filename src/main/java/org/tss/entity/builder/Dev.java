package org.tss.entity.builder;

import java.util.EnumMap;

import org.tss.controller.ResourceType;
import org.tss.entity.Cloner;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Dev extends Pane {

	private Builder builder;
	private Cloner<Slot> cloner;

	private EnumMap<ResourceType, Double> price;

	public Dev(Builder builder, Cloner<Slot> cloner, double... prices) {
		this.builder = builder;
		this.cloner = cloner;

		for (int i = 0; i < ResourceType.values().length; i++) {
			ResourceType key = ResourceType.values()[i];
			Double value = (i < prices.length) ? prices[i] : 0.0;
			price.put(key, value);
		}

		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> buy());
	}

	void buy() {
		cloner.next().add();
	}
}
