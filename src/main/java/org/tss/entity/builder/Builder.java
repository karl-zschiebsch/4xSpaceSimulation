package org.tss.entity.builder;

import java.util.List;
import java.util.Set;

import org.tss.controller.Controller;
import org.tss.entity.Entity;

public interface Builder extends Entity {

	Set<Dev> getDevs();

	List<Slot> getSlots();
	
	Controller getController();
}
