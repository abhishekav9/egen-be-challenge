package com.iot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iot.business.IotConstants;
import com.iot.business.MetricBusinessLogic;
import com.iot.pojo.SensorMetric;

/**
 * the metrics api that is exposed as a service endpoint
 * to create, read and read by time range
 * @author abhishekashwathnarayanvenkat
 *
 */
@RestController
@RequestMapping("/metricserviceapi")
public class MetricServiceAPI {

	@Autowired
	MetricBusinessLogic metricBusinessLogic;

	/**
	 * read – reads all the metrics stored in your database
	 * 
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ResponseEntity<?> read() {
		List<SensorMetric> metricList;
		try {
			metricList = metricBusinessLogic.readMetrics();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (metricList != null && metricList.size() == 0)
			return new ResponseEntity<String>(IotConstants.NOTFOUND, HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SensorMetric>>(metricList, HttpStatus.OK);
	}

	/**
	 * 1. Create Metric in the database 2. Trigger underweight and overweight
	 * rules from business service 3. if created successfully then return 201 4.
	 * In case of any exception return 404
	 * 
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody SensorMetric metric) {

		try {
			metricBusinessLogic.createMetric(metric);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Successfully Created!", HttpStatus.CREATED);
	}

	/**
	 * 1. Read metrics data within time range from business service 2. if no
	 * content fount return httpstatus code 204 3. if content found then return
	 * 200 4. In case of any exception return 404
	 * 
	 * @return
	 */
	@RequestMapping(value = "/readByTimeRange/{starttime}/{endtime}", method = RequestMethod.GET)
	public ResponseEntity<?> readByTimeRange(@PathVariable Long starttime, @PathVariable Long endtime) {
		Optional<Long> startTimeOpt = Optional.ofNullable(starttime);
		Optional<Long> endTimeOpt = Optional.ofNullable(endtime);

		List<SensorMetric> metricList;
		try {
			metricList = metricBusinessLogic.readByTimeRange(startTimeOpt, endTimeOpt);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (metricList.size() == 0)
			return new ResponseEntity<String>(IotConstants.NOTFOUND, HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SensorMetric>>(metricList, HttpStatus.OK);
	}

}
