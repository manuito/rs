package com.elecomte.rs.ble;

import java.util.List;

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
	public void setDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
