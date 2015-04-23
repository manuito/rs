package com.elecomte.rs.ble;

import java.util.List;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
public class DeviceSpec {

	private final List<CharacteristicIdentifier> chars;

	public DeviceSpec(List<CharacteristicIdentifier> chars) {
		super();
		this.chars = chars;
	}

	/**
	 * @return the chars
	 */
	public List<CharacteristicIdentifier> getChars() {
		return this.chars;
	}

}
