package com.anjus.mapreduce;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.LoggerFactory;

import com.anjus.utils.Constants;
import com.anjus.utils.CropPlanningUtils;

/**
 * Reducer Class for Crop Demand Calculation
 * 
 * @author Anju Prasannan
 * @date 2021/05/07
 *
 */
public class CropDemandReducer extends Reducer<Text, Text, Text, Text> {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CropDemandReducer.class);
	private DoubleWritable result = new DoubleWritable();
	DecimalFormat f = new DecimalFormat(Constants.DECIMAL_FORMAT);
	double max = Constants.ZERO;
	List<String> valueList = new ArrayList<String>();

	/**
	 * Reduce method implementation
	 * 
	 * @param Key
	 * @param values
	 * @param Context
	 * 
	 */
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) {
		try {
			double sum = Constants.ZERO;
			LOG.info("Inside reducer");
			// 1, find the max of crop production
			for (Text count : values) {
				double countValue = Double.parseDouble(count.toString());
				max = Math.max(max, countValue);
				double normValue = CropPlanningUtils.normaliseValues(countValue, max);
				if (countValue > Constants.DECIMAL_ZERO) {
					valueList.add(String.valueOf(f.format(normValue)));
				}
			}
			result.set(max);

			if (valueList.size() > 0) {
				for (String value : valueList) {
					sum += Double.parseDouble(value);
				}
				// 2, write out the crop and the max of production
				context.write(key, new Text(String.valueOf(f.format(sum))));
			}
		} catch (IOException | InterruptedException e) {
			LOG.error(e.getMessage());
		}

	}
}
