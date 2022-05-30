package org.tss.value;

public class DoubleCounter extends DownUpCounter<Double> {

	public DoubleCounter(double up, double in) {
		super(up);
	}

	public DoubleCounter(double up) {
		super(up);
	}

	@Override
	public void up(Double another) {
		value = Double.min(up, value + another);
	}

	@Override
	public void down() {
		value = 0.0;
	}

	@Override
	public boolean hasReached() {
		return value >= up;
	}
}
