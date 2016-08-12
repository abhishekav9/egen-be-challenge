package com.iot.ruleengine;

import org.easyrules.core.BasicRule;

import com.iot.business.IotConstants;
import com.iot.dao.SensorAlertDAO;
import com.iot.dao.SensorAlertDAOImpl;
import com.iot.pojo.SensorAlert;
import com.iot.pojo.SensorMetric;

/**
 * RuleEngine for handling underweight scenarios
 * @author abhishekashwathnarayanvenkat
 *
 */
public class UnderWeightRule extends BasicRule {

	private SensorMetric sensorMetric;
	private int baseWeight;

	SensorAlertDAO sensorAlertDao = new SensorAlertDAOImpl();

	public UnderWeightRule(SensorMetric sensorMetric, int baseWeight) {
		super("UnderWeightRule", "Create alert if weight of the person drops below 10% of his base weight");
		this.sensorMetric = sensorMetric;
		this.baseWeight = baseWeight;
	}

	/**
	 * check if current weight is < baseWeight-10%
	 */
	@Override
	public boolean evaluate() {
		return Double
				.parseDouble(sensorMetric.getValue()) < (this.baseWeight - (this.baseWeight * IotConstants.THRESHOLD));
	}

	/**
	 * triggered if evaluate returns true - we save the alert record
	 */
	@Override
	public void execute() {
		SensorAlert alert = new SensorAlert();
		alert.setTimeStamp(sensorMetric.getTimeStamp());
		alert.setValue(sensorMetric.getValue());
		alert.setStatus(IotConstants.UWSTATUS);
		sensorAlertDao.createAlert(alert);
	}
}
