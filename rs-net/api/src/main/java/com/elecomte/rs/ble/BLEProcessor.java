package com.elecomte.rs.ble;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
interface BLEProcessor {

	boolean supportConnection();

	boolean isReady() throws BLEStackException;

	List<DeviceIdentifier> bleScan() throws BLEStackException;

	List<CharacteristicIdentifier> getDeviceCharacteristics(DeviceIdentifier id) throws BLEStackException;

	String readCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId) throws BLEStackException;

	boolean writeCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId, String newValue)
			throws BLEStackException;

	boolean launchCharacteristicListener(DeviceIdentifier id, CharacteristicIdentifier charId, Consumer<String> listener)
			throws BLEStackException;

	boolean stopAll();
}
