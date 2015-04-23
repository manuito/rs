package com.elecomte.rs;

import com.elecomte.rs.ble.DeviceIdentifier;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
public class RollingSpider {

	private String surname;

	private final DeviceIdentifier dev;

	private boolean initDone;
	
	public RollingSpider(DeviceIdentifier dev) {
		super();
		this.dev = dev;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the dev
	 */
	public DeviceIdentifier getDev() {
		return this.dev;
	}

}
