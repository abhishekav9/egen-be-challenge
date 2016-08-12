package com.iot.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.iot.pojo.SensorAlert;

/**
 * a DAO interface to be implemented
 * @author abhishekashwathnarayanvenkat
 *
 */
public interface SensorAlertDAO {
	public ObjectId createAlert(SensorAlert alert);

	public List<SensorAlert> readAlerts();

	public List<SensorAlert> readByTimeRange(Long starttime, Long endtime);
}
