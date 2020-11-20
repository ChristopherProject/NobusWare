package it.nobusware.client.utils.value.impl;

import it.nobusware.client.utils.value.Value;

public class ColorValue extends Value<Integer> {
	
	public ColorValue(String label, int value) {
		super(label, Integer.valueOf(value));
	}

	public void setValue(String value) {
	}
}