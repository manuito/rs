package com.elecomte.rs.ble;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfiguration.class })
public class TestService {

	@Autowired
	private BLEService service;

	@Test
	public void testScan() {

		assertTrue(this.service.isReady());

		List<DeviceIdentifier> ids = this.service.listDevices();

		assertFalse(ids.size() == 0);

		DeviceIdentifier rs = null;
		for (DeviceIdentifier dev : ids) {
			if (dev.getName().startsWith("RS_")) {
				rs = dev;
			}
		}

		assertNotNull(rs);

		DeviceSpec specs = this.service.getDeviceSpec(rs);

		assertNotNull(specs);
		assertFalse(specs.getChars().size() == 0);
	}

}
