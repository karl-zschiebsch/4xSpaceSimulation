package org.tss.unit.modul;

import org.tss.entity.builder.Builder;
import org.tss.unit.station.Station;

public class Factory extends Modul {

	public Factory(Station station) {
		super(station);
	}

	@Override
	public void update(double deltaT) {
		((Builder) getUnit()).progress(deltaT);
	}
}
