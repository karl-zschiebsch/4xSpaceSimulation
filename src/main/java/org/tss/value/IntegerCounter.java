package org.tss.value;

public class IntegerCounter extends DownUpCounter<Integer> {

	public IntegerCounter(int up, int in) {
		super(up, in);
	}

	@Override
	public void up(Integer another) {
		value = Integer.min(up, value + another);
	}

	@Override
	public void down() {
		value = 0;
	}

	@Override
	public boolean hasReached() {
		return value >= up;
	}
}
