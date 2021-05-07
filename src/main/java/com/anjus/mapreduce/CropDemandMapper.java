package com.anjus.mapreduce;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.LoggerFactory;

import com.anjus.utils.Constants;

/**
 * Mapper Class for Crop Demand Calculation
 * 
 * @author Anju Prasannan
 * @date 2021/05/07
 *
 */
public class CropDemandMapper extends Mapper<Object, Text, Text, Text> {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CropDemandMapper.class);

	/**
	 * Map method implementation
	 * 
	 * @param Key
	 * @param Value
	 * @param Context
	 * 
	 */
	@Override
	protected void map(Object key, Text value, Context context) {
		try {
			LOG.info("Inside mapper");
			String crop = Constants.EMPTY_STRING;
			String demand = Constants.EMPTY_STRING;
			// 1, get data
			String line = value.toString();
			// 2, split data
			String[] data = line.split(Constants.COMMA);
			if ((data.length > Constants.DEMAND_INDEX)
					&& (StringUtils.isNotEmpty(data[Constants.DEMAND_INDEX].trim()))) {
				crop = data[Constants.CROP_INDEX].trim();
				demand = data[Constants.DEMAND_INDEX].trim();
				// 3, write out the data
				context.write(new Text(crop), new Text(demand));
			} else {
				LOG.error(line);
			}
		} catch (IOException | InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}
}
