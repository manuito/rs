package com.elecomte.rs;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elecomte.rs.ble.BLEService;
import com.elecomte.rs.ble.DeviceIdentifier;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
@Service
class RollingSpiderServiceAdapter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RollingSpiderServiceAdapter.class);

	@Autowired
	private BLEService bleService;

	Collection<RollingSpider> getAvailables() {
		return this.bleService.listDevices().stream().filter(dev -> isRollingSpider(dev))
				.map(dev -> new RollingSpider(dev)).collect(Collectors.toList());
	}

	RollingSpiderContext connect(RollingSpider rs) {
		return new RollingSpiderContext(this.bleService.getDeviceSpec(rs.getDev()));
	}

	void setCharacteristic(RollingSpider rs, RollingSpiderContext ctxt, String uuid, String value) {
		this.bleService.setDeviceCharacteristic(rs.getDev(), ctxt.getSpecs().getIdentifier(uuid), value);
		waitForSwallow();
	}

	void addCharacteristicListener(RollingSpider rs, RollingSpiderContext ctxt, String uuid) {
		addCharacteristicListener(rs, ctxt, uuid, st -> ignore(uuid, st));
	}

	void addCharacteristicListener(RollingSpider rs, RollingSpiderContext ctxt, String uuid, Consumer<String> listener) {
		this.bleService.addDeviceCharacteristicListener(rs.getDev(), ctxt.getSpecs().getIdentifier(uuid), listener);
		waitForSwallow();
	}

	String getDeviceCharacteristic(RollingSpider rs, RollingSpiderContext ctxt, String uuid) {
		return this.bleService.getDeviceCharacteristic(rs.getDev(), ctxt.getSpecs().getIdentifier(uuid));
	}

	/**
	 * @param dv
	 * @return
	 */
	private static boolean isRollingSpider(DeviceIdentifier dv) {
		return dv.getName().startsWith("RS_");
	}

	/**
	 * After all write / listener add action
	 */
	private static void waitForSwallow() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void ignore(String uuid, String st) {
		LOGGER.debug("Ignored listener update on uuid {}. New value is {}", uuid, st);
	}
}
