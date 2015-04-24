package com.elecomte.rs;

import com.elecomte.rs.ble.DeviceSpec;

/**
 * Keep status for current control tasks. Associated to RS Address
 * 
 * @author Emmanuel Lecomte (elecomte)
 *
 */
class RollingSpiderContext {

	private boolean started = false;

	private int motorCounter = 1;

	private int settingsCounter = 1;

	private int emergencyCounter = 1;

	private byte battery = -1; // unknown

	private byte status = -1; // unknown

	private final DeviceSpec specs;

	/**
	 * @param specs
	 */
	RollingSpiderContext(DeviceSpec specs) {
		this.specs = specs;
	}

	/**
	 * @return the specs
	 */
	DeviceSpec getSpecs() {
		return this.specs;
	}

	/**
	 * @return the started
	 */
	boolean isStarted() {
		return this.started;
	}

	/**
	 * @param started
	 *            the started to set
	 */
	void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * @return the motorCounter
	 */
	int getMotorCounter() {
		return this.motorCounter;
	}

	/**
	 * @param motorCounter
	 *            the motorCounter to set
	 */
	void setMotorCounter(int motorCounter) {
		this.motorCounter = motorCounter;
	}

	/**
	 * @return the settingsCounter
	 */
	int getSettingsCounter() {
		return this.settingsCounter;
	}

	/**
	 * @param settingsCounter
	 *            the settingsCounter to set
	 */
	void setSettingsCounter(int settingsCounter) {
		this.settingsCounter = settingsCounter;
	}

	/**
	 * @return the emergencyCounter
	 */
	int getEmergencyCounter() {
		return this.emergencyCounter;
	}

	/**
	 * @param emergencyCounter
	 *            the emergencyCounter to set
	 */
	void setEmergencyCounter(int emergencyCounter) {
		this.emergencyCounter = emergencyCounter;
	}

	/**
	 * @return the battery
	 */
	byte getBattery() {
		return this.battery;
	}

	/**
	 * @param battery
	 *            the battery to set
	 */
	void setBattery(byte battery) {
		this.battery = battery;
	}

	/**
	 * @return the status
	 */
	byte getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(byte status) {
		this.status = status;
	}

}
