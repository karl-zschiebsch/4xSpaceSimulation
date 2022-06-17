package org.tss.value;

public class DoubleCounter extends DownUpCounter<Double> {
	private static final long serialVersionUID = 85993209095698932L;

	public DoubleCounter(double up, double in) {
		super(up, in);
	}

	public DoubleCounter(double up) {
		super(up);
	}

	@Override
	public void up(Double another) {
		value = Double.min(up, value + another);
	}

	@Override
	public void down(Double another) {
		value -= another;
	}

	@Override
	public void reset() {
		value = 0.0;
	}

	@Override
	public boolean hasReached() {
		return value >= up;
	}
}