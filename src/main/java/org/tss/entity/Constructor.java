package org.tss.entity;

import java.util.function.Consumer;

public record Constructor<T extends SpaceObject> (Cloner<T> creator) {

	@SafeVarargs
	public final T create(Consumer<T>... options) {
		T created = creator.next();
		for (Consumer<T> option : options) {
			option.accept(created);
		}
		return created;
	}
}
