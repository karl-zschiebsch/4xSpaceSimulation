package org.tss.map;

import org.tss.base.SpaceObject;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.SubScene;

public class Map extends SubScene {

	protected final ObservableList<SpaceObject> objects = FXCollections.observableArrayList();

	protected final Camera camera = new ParallelCamera();

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
	}

	public ObservableList<SpaceObject> getObjects() {
		return objects;
	}
}
