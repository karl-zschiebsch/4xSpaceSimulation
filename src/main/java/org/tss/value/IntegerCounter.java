package org.tss.value;

public class IntegerCounter extends DownUpCounter<Integer> {
	private static final long serialVersionUID = 895486334111771554L;

	public IntegerCounter(int up, int in) {
		super(up, in);
	}

	public IntegerCounter(int up) {
		super(up);
	}

	@Override
	public void up(Integer another) {
		value = Integer.min(up, value + another);
	}

	@Override
	public void down(Integer another) {
		value -= another;
	}

	@Override
	public void reset() {
		value = 0;
	}

	@Override
	public boolean hasReached() {
		return value >= up;
	}
}