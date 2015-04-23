package com.elecomte.rs.ble;

public class CharacteristicIdentifier {

	private final String handle;
	private final String properties;
	private final String valueHandle;
	private final String uuid;

	CharacteristicIdentifier(String handle, String properties, String valueHandle, String uuid) {
		this.handle = handle;
		this.properties = properties;
		this.valueHandle = valueHandle;
		this.uuid = uuid;
	}

	public String getHandle() {
		return this.handle;
	}

	public String getProperties() {
		return this.properties;
	}

	public String getValueHandle() {
		return this.valueHandle;
	}

	public String getUuid() {
		return this.uuid;
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "handle = " + this.handle + ", char properties = " + this.properties + ", char value handle = "
				+ this.handle + ", uuid = " + this.uuid;
	}

}
