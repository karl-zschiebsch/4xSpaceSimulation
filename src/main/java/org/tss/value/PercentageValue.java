package org.tss.value;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PercentageValue implements ObservableValue<Double> {

	private ExpressionHelper<Double> helper = null;

	private DoubleBinding relation = new DoubleBinding() {
		@Override
		protected double computeValue() {
			return max == 0 ? 0 : cur / max;
		}
	};

	private double cur, min, max;

	public PercentageValue(double cur, double min, double max) {
		this.cur = cur;
		this.min = min;
		this.max = max;
	}

	public PercentageValue(double value) {
		this(value, 0, value);
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
		if (temp != cur) {
			fireValueChangedEvent();
			relation.invalidate();
		}
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

	public void setMin(double value) {
		min = value;
	}

	public double getMin() {
		return min;
	}

	public DoubleBinding getAssoziatedBinding() {
		return relation;
	}
}
