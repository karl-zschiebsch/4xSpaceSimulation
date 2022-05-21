package org.tss.unit.linear;

import org.tss.controller.Controller;
import org.tss.unit.Unit;

public class Stack<E extends Linear> extends Unit {

	private static final long serialVersionUID = 5980057030621705715L;

	protected Stack(Controller controller) {
		this(controller, 1);
	}

	public Stack(Controller controller, double size) {
		super(controller);
		hitPoints.setMax(size);
		hitPoints.setCur(size);
	}

	@Override
	public void update(double deltaT) {
	}

	@Override
	public void harm(double value) {
		hitPoints.setCur(hitPoints.getCur() - value);
	}

	public final void add(double count) {
		hitPoints.setMax(hitPoints.getMax() + count);
		hitPoints.setCur(hitPoints.getCur() + count);
	}

	public void pack(Stack<E> stack) {
		if (stack.size() > 0)
			add(stack.hitPoints.getCur());
		stack.destruct();
	}

	public final void remove(double count) {
		hitPoints.setCur(hitPoints.getCur() - count);
	}

	public Stack<E> split() {
		if (size() < 2)
			return null;

		double n = hitPoints.getCur() / 2;
		remove(n);
		return new Stack<>(getController(), n);
	}

	public int size() {
		return (int) Math.ceil(hitPoints.getCur());
	}
}
