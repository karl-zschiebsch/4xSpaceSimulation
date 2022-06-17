package org.tss.unit.modul;

import org.tss.unit.station.Station;

public class Shipyard extends Modul {

	public Shipyard(Station station) {
		super(station);
	}

	@Override
	public void update(double deltaT) {
//		ArrayList<UnitBuilder> builder = ((Station) getUnit()).getProgress();
////		System.out.println(builder);
//		for (int i = 0; i < builder.size(); i++) {
//			builder.get(i).update(deltaT);
//		}
	}
}
