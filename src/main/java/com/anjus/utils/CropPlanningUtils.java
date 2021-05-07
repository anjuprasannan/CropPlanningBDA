package com.anjus.utils;

/**
 * Utilities Class for Crop Demand Calculation
 * 
 * @author Anju Prasannan
 * @date 2021/05/07
 *
 */
public abstract class CropPlanningUtils {

	/**
	 * Mthod used to convert values to scale 10
	 * 
	 * @param countValue
	 * @param max
	 * @return
	 */
	public static double normaliseValues(double value, double max) {
		double normValue = (value / max) * Constants.DECIMAL_TEN;
		return normValue;
	}
}
