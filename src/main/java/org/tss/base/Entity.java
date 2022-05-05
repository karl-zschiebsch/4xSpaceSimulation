package org.tss.base;

import java.io.Serializable;

public interface Entity extends Serializable {

	public void update(double deltaT);
}
