package com.iot.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.iot.pojo.SensorMetric;

/**
 * sensor metric dao implementation for mongodb
 * @author abhishekashwathnarayanvenkat
 *
 */
@Component
public class SensorMetricDAOImpl implements SensorMetricDAO {

	private final Datastore datastore;

	public SensorMetricDAOImpl() {
		// TODO Auto-generated constructor stub
		datastore = DbConfig.getInstance().getDatastore();
	}

	@Override
	public ObjectId createMetric(SensorMetric metric) {
		// TODO Auto-generated method stub
		datastore.save(metric);
		return metric.getId();
	}

	@Override
	public List<SensorMetric> readMetrics() {
		// TODO Auto-generated method stub
		return datastore.find(SensorMetric.class).asList();
	}

	@Override
	public List<SensorMetric> readByTimeRange(Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		Query<SensorMetric> sensorQuery = datastore.createQuery(SensorMetric.class).field("timeStamp")
				.greaterThanOrEq(startTime).field("timeStamp").lessThanOrEq(endTime);
		return sensorQuery.asList();
	}

	/**
	 * first stored value in the metric collection is the base weight
	 */
	@Override
	public SensorMetric getBaseWeight() {
		// TODO Auto-generated method stub
		List<SensorMetric> list = datastore.find(SensorMetric.class).order("timeStamp").asList();
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
