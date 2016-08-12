package com.iot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iot.business.AlertBusinessLogic;
import com.iot.business.IotConstants;
import com.iot.pojo.SensorAlert;

/**
 * the alerts api that is exposed as a service endpoint
 * to read alerts
 * @author abhishekashwathnarayanvenkat
 *
 */
@RestController
@RequestMapping("/alertserviceapi")
public class AlertServiceAPI {

	@Autowired
	AlertBusinessLogic alertBusinessLogic;

	/**
	 * read all alerts
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ResponseEntity<?> read() {
		List<SensorAlert> alertList;
		try {
			alertList = alertBusinessLogic.readAlerts();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (alertList != null && alertList.size() == 0)
			return new ResponseEntity<String>(IotConstants.NOTFOUND, HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SensorAlert>>(alertList, HttpStatus.OK);
	}

	/**
	 * read alerts by timestamp range
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@RequestMapping(value = "/readByTimeRange/{starttime}/{endtime}", method = RequestMethod.GET)
	public ResponseEntity<?> readByTimeRange(@PathVariable Long starttime, @PathVariable Long endtime) {
		Optional<Long> startTimeOpt = Optional.ofNullable(starttime);
		Optional<Long> endTimeOpt = Optional.ofNullable(endtime);
		
		List<SensorAlert> alertList;
		try {
			alertList = alertBusinessLogic.readByTimeRange(startTimeOpt, endTimeOpt);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (alertList.size() == 0)
			return new ResponseEntity<String>(IotConstants.NOTFOUND, HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SensorAlert>>(alertList, HttpStatus.OK);
	}
}
