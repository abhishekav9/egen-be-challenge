package com.iot.business;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.iot.dao.SensorAlertDAO;
import com.iot.dao.SensorAlertDAOImpl;
import com.iot.pojo.SensorAlert;

/**
 * business logic class which is used by the controller
 * @author abhishekashwathnarayanvenkat
 *
 */
@Component
public class AlertBusinessLogic {
	
	SensorAlertDAO sensorAlertDAOImpl = new SensorAlertDAOImpl();

	/**
	 * create alert called by our rules classes
	 * @param alert
	 * @return
	 */
	public ObjectId createAlert(SensorAlert alert) {

		// Persist into db
		ObjectId id = sensorAlertDAOImpl.createAlert(alert);

		return id;
	}

	/**
	 * read alerts called by user through alertserviceAPI
	 * @return
	 */
	public List<SensorAlert> readAlerts() {
		return sensorAlertDAOImpl.readAlerts();
	}
	
	/**
	 * read alerts by time range called by user through alertserviceAPI
	 * @return
	 */
	public List<SensorAlert> readByTimeRange(Optional<Long> starttime, Optional<Long> endtime) {
		Long sTime = starttime.orElse(0L);
		Long eTime = endtime.orElse(System.currentTimeMillis());
		return sensorAlertDAOImpl.readByTimeRange(sTime, eTime);
	}
}
