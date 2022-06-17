package org.tss.value;

import java.io.Serializable;

public abstract class DownUpCounter<T extends Number> implements Serializable {
	private static final long serialVersionUID = -5400818084388635793L;

	protected T up, value;

	public DownUpCounter(T up, T in) {
		this.up = up;
		this.value = in;
	}

	public DownUpCounter(T up) {
		this(up, up);
		reset();
	}

	public abstract void up(T another);

	public abstract void down(T another);

	public abstract void reset();

	public abstract boolean hasReached();

	public T getValue() {
		return value;
	}

	public void setValue(T another) {
		value = another;
	}

	public T getUp() {
		return up;
	}

	public void setUp(T another) {
		up = another;
	}
}