package com.elecomte.rs.ble;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
public class DeviceSpec {

	private final Map<String, CharacteristicIdentifier> chars = new LinkedHashMap<>();

	public DeviceSpec(List<CharacteristicIdentifier> chars) {
		super();
		chars.forEach(ch -> this.chars.put(ch.getUuid(), ch));
	}

	/**
	 * @return the chars
	 */
	public Collection<CharacteristicIdentifier> getChars() {
		return this.chars.values();
	}

	/**
	 * @param uuid
	 * @return
	 */
	public CharacteristicIdentifier getIdentifier(String uuid) {
		return this.chars.get(uuid);
	}
}