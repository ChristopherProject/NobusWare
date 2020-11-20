package it.nobusware.client.utils.value.parse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BooleanParser {

	private static final Map<String, Boolean> PARSE = new HashMap<String, Boolean>() {};

	public static Optional<Boolean> parse(String input) {
		return Optional.ofNullable(PARSE.get(input.toLowerCase()));
	}
}