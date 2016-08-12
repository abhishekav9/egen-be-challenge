package com.iot.ruleengine;

import org.easyrules.core.BasicRule;

import com.iot.business.IotConstants;
import com.iot.dao.SensorAlertDAO;
import com.iot.dao.SensorAlertDAOImpl;
import com.iot.pojo.SensorAlert;
import com.iot.pojo.SensorMetric;

/**
 * RuleEngine for handling overweight scenarios
 * @author abhishekashwathnarayanvenkat
 *
 */
public class OverWeightRule extends BasicRule {

	private SensorMetric sensorMetric;
	private int baseWeight;

	
	SensorAlertDAO sensorAlertDao = new SensorAlertDAOImpl();

	public OverWeightRule(SensorMetric metric, int baseWeight) {
		super("OverWeightRule", "Create alert if weight of the person drops above 10% of his base weight");
		this.sensorMetric = metric;
		this.baseWeight = baseWeight;
	}

	/**
	 * evaluate called when fireRules execute and checks if the condition
	 * satisfies
	 * 
	 * @return
	 */
	@Override
	public boolean evaluate() {
		return Double
				.parseDouble(sensorMetric.getValue()) > (this.baseWeight + (this.baseWeight * IotConstants.THRESHOLD));
	}

	/**
	 * execute called when evaluate returns true
	 */
	@Override
	public void execute() {
		SensorAlert alert = new SensorAlert();
		alert.setTimeStamp(sensorMetric.getTimeStamp());
		alert.setValue(sensorMetric.getValue());
		alert.setStatus(IotConstants.OWSTATUS);
		sensorAlertDao.createAlert(alert);
	}
}