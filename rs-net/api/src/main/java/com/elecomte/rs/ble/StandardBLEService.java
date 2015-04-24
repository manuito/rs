package com.elecomte.rs.ble;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardBLEService implements BLEService {

	@Autowired
	private BLEProcessor processor;

	/**
	 * @return
	 * @see com.elecomte.rs.ble.BLEService#isReady()
	 */
	@Override
	public boolean isReady() {
		return this.processor.isReady();
	}

	@Override
	public List<DeviceIdentifier> listDevices() throws BLEStackException {
		return this.processor.bleScan();
	}

	@Override
	public DeviceSpec getDeviceSpec(DeviceIdentifier id) {
		return new DeviceSpec(this.processor.getDeviceCharacteristics(id));
	}

	@Override
	public void setDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId, String value) {
		this.processor.writeCharacteristic(id, charId, value);
	}

	@Override
	public String getDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId) {
		return this.processor.readCharacteristic(id, charId);
	}

	@Override
	public void addDeviceCharacteristicListener(DeviceIdentifier id, CharacteristicIdentifier charId,
			Consumer<String> listener) {
		this.processor.launchCharacteristicListener(id, charId, listener);
	}

}
