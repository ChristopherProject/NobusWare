package it.nobusware.client.utils.value.parse;

public final class NumberParser {
	
	public static <T extends Number> T parse(String input, Class<T> numberType) throws NumberFormatException {
		return NumberCaster.cast(numberType, Double.valueOf(Double.parseDouble(input)));
	}
}
