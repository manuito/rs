package com.elecomte.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elecomte.rs.ble.BLEService;

@Service
public class RollingSpiderControl {

	@Autowired
	private BLEService service;

	/**
	 * @param rs
	 * @return
	 */
	public boolean isAvailable(RollingSpider rs) {
		return true;
	}

	public void getUp(RollingSpider rs) {
	}

	public void land(RollingSpider rs) {
	}

	public int getBaterryPercent(RollingSpider rs) {
		return 0;
	}

}
