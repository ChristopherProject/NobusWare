package it.nobusware.client.utils.value.parse;

import it.nobusware.client.utils.factory.MathUtils;
import it.nobusware.client.utils.value.impl.NumberValue;

public class NumberHelper {
	
	public static void decrecement(NumberValue value) {
		if (value.getValue() instanceof Integer) {
			int inc = ((Integer) value.getValue()).intValue();
			inc -= value.getInc().intValue();
			inc = (int) MathUtils.round(inc, 1);
			if (inc >= value.getMinimum().intValue())
				value.setValue(Integer.valueOf(inc));
		} else if (value.getValue() instanceof Double) {
			double inc = ((Double) value.getValue()).doubleValue();
			inc -= value.getInc().doubleValue();
			inc = MathUtils.round(inc, 1);
			if (inc >= value.getMinimum().doubleValue())
				value.setValue(Double.valueOf(inc));
		} else if (value.getValue() instanceof Float) {
			float inc = ((Float) value.getValue()).floatValue();
			inc -= value.getInc().floatValue();
			inc = (float) MathUtils.round(inc, 1);
			if (inc >= value.getMinimum().floatValue())
				value.setValue(Float.valueOf(inc));
		} else if (value.getValue() instanceof Long) {
			long inc = ((Long) value.getValue()).longValue();
			inc -= value.getInc().longValue();
			inc = (long) MathUtils.round(inc, 1);
			if (inc >= value.getMinimum().longValue())
				value.setValue(Long.valueOf(inc));
		}
	}

	public static void increment(NumberValue value) {
		if (value.getValue() instanceof Integer) {
			int inc = ((Integer) value.getValue()).intValue();
			inc += value.getInc().intValue();
			inc = (int) MathUtils.round(inc, 1);
			if (inc <= value.getMaximum().intValue())
				value.setValue(Integer.valueOf(inc));
		} else if (value.getValue() instanceof Double) {
			double inc = ((Double) value.getValue()).doubleValue();
			inc += value.getInc().doubleValue();
			inc = MathUtils.round(inc, 1);
			if (inc <= value.getMaximum().doubleValue())
				value.setValue(Double.valueOf(inc));
		} else if (value.getValue() instanceof Float) {
			float inc = ((Float) value.getValue()).floatValue();
			inc += value.getInc().floatValue();
			inc = (float) MathUtils.round(inc, 1);
			if (inc <= value.getMaximum().floatValue())
				value.setValue(Float.valueOf(inc));
		} else if (value.getValue() instanceof Long) {
			long inc = ((Long) value.getValue()).longValue();
			inc += value.getInc().longValue();
			inc = (long) MathUtils.round(inc, 1);
			if (inc <= value.getMaximum().longValue())
				value.setValue(Long.valueOf(inc));
		}
	}
}