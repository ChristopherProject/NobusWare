package it.nobusware.client.utils.value.impl;

import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.parse.BooleanParser;

import java.util.Optional;

public class BooleanValue extends Value<Boolean> {
	
	public BooleanValue(String label, Boolean value) {
		super(label, value);
	}

	public void setValue(String input) {
		Optional<Boolean> result = BooleanParser.parse(input);
		result.ifPresent(aBoolean -> this.value = aBoolean);
	}

	public void toggle() {
		BooleanValue booleanValue = this;
		booleanValue.value = !booleanValue.value;
	}

	public boolean isEnabled() {
		return this.value;
	}

	public void setEnabled(boolean enabled) {
		this.value = enabled;
	}
}