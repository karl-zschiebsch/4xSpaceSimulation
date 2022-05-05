package org.tss.base;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.WritableDoubleValue;

public class ModalDoubleValue implements ObservableDoubleValue, WritableDoubleValue {

	private ExpressionHelper<Number> helper = null;

	private double cur, max;

	public ModalDoubleValue(double value) {
		cur = value;
		max = value;
	}

	public ModalDoubleValue() {
		this(0);
	}

	@Override
	public int intValue() {
		return (int) get();
	}

	@Override
	public long longValue() {
		return (long) get();
	}

	@Override
	public float floatValue() {
		return (float) get();
	}

	@Override
	public double doubleValue() {
		return get();
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

	@Override
	public double get() {
		return cur;
	}

	@Override
	public final void set(double value) {
		var temp = cur;
		cur = value > max ? max : value;
		if (temp != cur)
			fireValueChangedEvent();
	}

	@Override
	public void setValue(Number value) {
		set((value == null) ? 0 : value.doubleValue());
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
}
