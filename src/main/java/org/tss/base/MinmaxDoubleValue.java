package org.tss.base;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MinmaxDoubleValue implements ObservableValue<Number> {

	private ExpressionHelper<Number> helper = null;

	private double cur, min, max;

	public MinmaxDoubleValue(double value) {
		cur = value;
		max = value;
		min = 0;
	}

	public MinmaxDoubleValue() {
		this(0);
	}

	@Override
	public void addListener(ChangeListener<? super Number> listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	@Override
	public void removeListener(ChangeListener<? super Number> listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	@Override
	public Number getValue() {
		return cur;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	protected void fireValueChangedEvent() {
		ExpressionHelper.fireValueChangedEvent(helper);
	}

	protected final void set(double value) {
		var temp = cur;
		cur = Math.min(Math.max(value, min), max);
		if (temp != cur)
			fireValueChangedEvent();
	}

	public void setCur(double value) {
		set(value);
	}

	public double getCur() {
		return cur;
	}

	public void setMax(double value) {
		max = value;
	}

	public double getMax() {
		return max;
	}

	public boolean isMax() {
		return cur == max;
	}

	public void setMin(double value) {
		min = value;
	}

	public double getMin() {
		return min;
	}

	public boolean isMin() {
		return cur == min;
	}
}
