package com.iot.business;

/**
 * a constants repo for all non changing values
 * @author abhishekashwathnarayanvenkat
 *
 */
public interface IotConstants {
	public static final String NOTFOUND = "Nothing Found";
	
	public static final String DBHOST = "localhost";
	public static final int DBPORT = 27017;
	public static final String DBNAME = "iotDB";
	
	public static final Double THRESHOLD = 0.10;
	public static final String OWSTATUS = "OVERWEIGHT";
	public static final String UWSTATUS = "UNDERWEIGHT";
}
