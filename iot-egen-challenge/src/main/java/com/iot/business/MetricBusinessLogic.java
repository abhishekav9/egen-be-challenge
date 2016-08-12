package com.iot.business;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iot.dao.SensorMetricDAOImpl;
import com.iot.pojo.SensorMetric;
import com.iot.ruleengine.OverWeightRule;
import com.iot.ruleengine.UnderWeightRule;

/**
 * business logic class which is used by the controller
 * @author abhishekashwathnarayanvenkat
 *
 */
@Component
public class MetricBusinessLogic {

	/**
	 * autowiring the dao
	 */
	@Autowired
	SensorMetricDAOImpl sensorMetricDAOImpl;

	public ObjectId createMetric(SensorMetric metric) {

		SensorMetric basemetric = sensorMetricDAOImpl.getBaseWeight();

		// Apply and execute rules
		if (basemetric != null) {
			Integer baseWeight = Integer.parseInt(basemetric.getValue());
			RulesEngine ruleEngine = RulesEngineBuilder.aNewRulesEngine().build();
			ruleEngine.registerRule(new UnderWeightRule(metric, baseWeight));
			ruleEngine.registerRule(new OverWeightRule(metric, baseWeight));
			ruleEngine.fireRules();
		}
		// Persist into db
		ObjectId id = sensorMetricDAOImpl.createMetric(metric);

		return id;
	}

	public List<SensorMetric> readMetrics() {
		return sensorMetricDAOImpl.readMetrics();
	}

	public List<SensorMetric> readByTimeRange(Optional<Long> starttime, Optional<Long> endtime) {
		Long sTime = starttime.orElse(0L);
		Long eTime = endtime.orElse(System.currentTimeMillis());
		return sensorMetricDAOImpl.readByTimeRange(sTime, eTime);
	}
}
