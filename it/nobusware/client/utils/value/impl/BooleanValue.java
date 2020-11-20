package it.nobusware.client.utils.value.impl;

import java.util.Optional;

import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.parse.BooleanParser;

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
		booleanValue.value = Boolean.valueOf(((Boolean) booleanValue.value).booleanValue() ^ true);
	}

	public boolean isEnabled() {
		return ((Boolean) this.value).booleanValue();
	}

	public void setEnabled(boolean enabled) {
		this.value = Boolean.valueOf(enabled);
	}
}