package org.tss.projectile;

import org.tss.base.SpaceObject;
import org.tss.controller.Controller;

public abstract class Projectile extends SpaceObject {

	private static final long serialVersionUID = -5682494091592111719L;

	protected Projectile(Controller controller) {
		super(controller);
	}

}
