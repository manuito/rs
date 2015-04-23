package com.elecomte.rs.ble;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

public class TestProcessor {

	private BluezBadSystemBLEProcessor process = new BluezBadSystemBLEProcessor();

	@Test
	public void testScan() throws Exception {

		List<DeviceIdentifier> devices = process.bleScan();

		assertFalse(devices.size() == 0);

	}}
