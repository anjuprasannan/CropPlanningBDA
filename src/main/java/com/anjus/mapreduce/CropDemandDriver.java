package com.anjus.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.LoggerFactory;

import com.anjus.utils.Constants;

/**
 * Driver Class for Crop Demand Calculation
 * 
 * @author Anju Prasannan
 * @date 2021/05/07
 *
 */

public class CropDemandDriver {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CropDemandDriver.class);

	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LOG.info("Inside driver");
			System.setProperty(Constants.HADOOP_HOME_KEY, Constants.HADOOP_HOME_VAL);
			Configuration conf = new Configuration();
			String inputFileName = Constants.BASE_DIR + Constants.INPUT_DIR;
			String outputFolder = Constants.BASE_DIR + Constants.OUTPUT_DIR;

			Path outputPath = new Path(outputFolder);

			@SuppressWarnings("deprecation")
			Job job = new Job(conf, Constants.JOB_NAME);
			FileSystem fs = FileSystem.get(outputPath.toUri(), job.getConfiguration());
			fs.delete(outputPath, true);
			job.setJarByClass(CropDemandDriver.class);
			job.setMapperClass(CropDemandMapper.class);
			job.setCombinerClass(CropDemandReducer.class);
			job.setReducerClass(CropDemandReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			FileInputFormat.addInputPath(job, new Path(inputFileName));
			FileOutputFormat.setOutputPath(job, new Path(outputFolder));
			System.exit(job.waitForCompletion(true) ? Constants.SUCCESS : Constants.FAILURE);
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
	}

}