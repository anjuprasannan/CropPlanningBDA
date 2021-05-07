package com.anjus.utils;

/**
 * Constants for Crop Demand Calculation
 * 
 * @author Anju Prasannan
 * @date 2021/05/07
 *
 */
public abstract class Constants {

	public static final String EMPTY_STRING = "";
	public static final String COMMA = ",";

	public static final String HADOOP_HOME_VAL = "C:\\Program Files\\hadoop\\hadoop-3.2.1";
	public static final String HADOOP_HOME_KEY = "hadoop.home.dir";

	public static final String BASE_DIR = "C:\\Users\\kbipi\\eclipse-workspace\\CropPlanningBDA\\src\\main\\resources\\";
	public static final String INPUT_DIR = "input";
	public static final String OUTPUT_DIR = "output";

	public static final String JOB_NAME = "Crop & Demand";

	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;

	public static final int CROP_INDEX = 4;
	public static final int DEMAND_INDEX = 6;

	public static final String DECIMAL_FORMAT = "##.00";

	public static final int ZERO = 0;

	public static final double DECIMAL_TEN = 10.0;
	public static final double DECIMAL_ZERO = 0.0;
}
