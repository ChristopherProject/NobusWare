package it.nobusware.client.utils.value.impl;

import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.clamper.NumberClamper;
import it.nobusware.client.utils.value.parse.NumberParser;

public class NumberValue<T extends Number> extends Value<T> {
	
	private final T minimum;

	private final T maximum;

	private final T inc;

	public NumberValue(String label, T value, T minimum, T maximum, T inc) {
		super(label, value);
		this.minimum = minimum;
		this.maximum = maximum;
		this.inc = inc;
	}

	public T getMinimum() {
		return this.minimum;
	}

	public T getMaximum() {
		return this.maximum;
	}

	public T getInc() {
		return this.inc;
	}

	public T getValue() {
		return (T) this.value;
	}

	public void setValue(T value) {
		this.value = NumberClamper.clamp(value, this.minimum, this.maximum);
	}

	public T getSliderValue() {
		return (T) this.value;
	}

	public void setValue(String value) {
		try {
			setValue((T) NumberParser.parse(value, ((Number) this.value).getClass()));
		} catch (NumberFormatException numberFormatException) {
		}
	}
}