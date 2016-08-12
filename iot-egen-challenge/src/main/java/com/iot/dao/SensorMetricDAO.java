package com.iot.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.iot.pojo.SensorMetric;

/**
 * sensor metric dao
 * @author abhishekashwathnarayanvenkat
 *
 */
public interface SensorMetricDAO {

	public ObjectId createMetric(SensorMetric metric);

	public List<SensorMetric> readMetrics();

	public List<SensorMetric> readByTimeRange(Long startTime, Long endTime);

	public SensorMetric getBaseWeight();
}
