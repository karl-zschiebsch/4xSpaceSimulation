package org.tss.entity;

import java.util.function.Consumer;
import java.util.function.Function;

import org.tss.controller.Controller;

public record Constructor<T extends SpaceObject> (Function<Controller, T> creator) {

	@SafeVarargs
	public final T create(Controller controller, Consumer<T>... options) {
		T created = creator.apply(controller);
		for (Consumer<T> option : options) {
			option.accept(created);
		}
		return created;
	}
}
