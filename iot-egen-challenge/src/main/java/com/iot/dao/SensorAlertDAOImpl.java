package com.iot.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.iot.pojo.SensorAlert;

/**
 * alertDAO implementation for mongodb
 * @author abhishekashwathnarayanvenkat
 *
 */
@Component
public class SensorAlertDAOImpl implements SensorAlertDAO {

	private final Datastore datastore;

	public SensorAlertDAOImpl() {
		// TODO Auto-generated constructor stub
		datastore = DbConfig.getInstance().getDatastore();
	}

	@Override
	public ObjectId createAlert(SensorAlert alert) {
		// TODO Auto-generated method stub
		datastore.save(alert);
		return alert.getId();
	}

	@Override
	public List<SensorAlert> readAlerts() {
		// TODO Auto-generated method stub
		return datastore.find(SensorAlert.class).asList();
	}

	/**
	 * read alerts by time range
	 */
	@Override
	public List<SensorAlert> readByTimeRange(Long starttime, Long endtime) {
		// TODO Auto-generated method stub
		Query<SensorAlert> query = datastore.createQuery(SensorAlert.class).field("timeStamp").greaterThan(starttime)
				.field("timeStamp").lessThan(endtime);
		return query.asList();
	}

}
