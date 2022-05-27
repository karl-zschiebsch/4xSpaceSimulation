package org.tss.base;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MinmaxDoubleValue implements ObservableValue<Double> {

	private ExpressionHelper<Double> helper = null;

	private double cur, min, max;

	public MinmaxDoubleValue(double cur, double min, double max) {
		this.cur = cur;
		this.min = min;
		this.max = max;
	}

	public MinmaxDoubleValue(double value) {
		this(value, 0, value);
	}

	public MinmaxDoubleValue() {
		this.min = Double.NEGATIVE_INFINITY;
		this.max = Double.POSITIVE_INFINITY;
	}

	@Override
	public void addListener(ChangeListener<? super Double> listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	@Override
	public void removeListener(ChangeListener<? super Double> listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	@Override
	public Double getValue() {
		return cur;
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
