package org.tss.value;

public abstract class DownUpCounter<T extends Number> {

	protected final T up;

	public DownUpCounter(T up, T in) {
		this.up = up;
		this.value = in;
	}

	public DownUpCounter(T up) {
		this(up, up);
	}

	protected T value;

	public abstract void up(T another);

	public abstract void down();

	public abstract boolean hasReached();

	public T getUp() {
		return up;
	}
}
