package org.tss.map;

import java.io.Serializable;

import org.tss.controller.Controller;
import org.tss.entity.SpaceObject;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.Line;

public class Map extends SubScene implements Serializable {
	private static final long serialVersionUID = -1284103814257393278L;

	protected final ObservableList<Controller> controllers = FXCollections.observableArrayList();
	protected final ObservableList<SpaceObject> objects = FXCollections.observableArrayList();

	public Map(Group root, double width, double height) {
		super(root, width, height);

		objects.addListener(new ListChangeListener<SpaceObject>() {
			@Override
			public void onChanged(Change<? extends SpaceObject> c) {
				c.next();
				for (SpaceObject spaceObject : c.getAddedSubList()) {
					root.getChildren().add(spaceObject);
				}
				for (SpaceObject spaceObject : c.getRemoved()) {
					root.getChildren().remove(spaceObject);
				}
			}
		});

		for (int x = -200; x <= width; x += 100) {
			root.getChildren().add(new Line(x, -200, x, height));
		}
		for (int y = -200; y <= height; y += 100) {
			root.getChildren().add(new Line(-200, y, width, y));
		}
	}

	public ObservableList<Controller> getControllers() {
		return controllers;
	}

	public ObservableList<SpaceObject> getObjects() {
		return objects;
	}
}
