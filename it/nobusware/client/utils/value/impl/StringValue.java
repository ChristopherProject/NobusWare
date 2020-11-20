package it.nobusware.client.utils.value.impl;

import it.nobusware.client.utils.value.Value;

public class StringValue extends Value<String> {
	
	public StringValue(String label, String value) {
		super(label, value);
	}

	public void setValue(String value) {
		this.value = value;
	}
}