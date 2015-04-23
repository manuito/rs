package com.elecomte.rs;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elecomte.rs.ble.BLEService;
import com.elecomte.rs.ble.DeviceIdentifier;

/**
 * Keep in memory only...
 * 
 * @author Emmanuel Lecomte (elecomte)
 *
 */
@Service
public class RollingSpiderAccess {

	@Autowired
	private BLEService bleService;

	private Map<String, String> surnames = new ConcurrentHashMap<>();

	/**
	 * @return
	 */
	public Collection<RollingSpider> getAvailables() {
		return this.bleService.listDevices().stream().filter(dev -> isRollingSpider(dev))
				.map(dev -> new RollingSpider(dev)).collect(Collectors.toList());
	}

	/**
	 * @param surname
	 * @return
	 * @throws RollingSpiderNotFoundException
	 */
	public RollingSpider getBySurname(String surname) throws RollingSpiderNotFoundException {

		String address = this.surnames.get(surname);

		if (address == null)
			throw new RollingSpiderNotFoundException("No record found for surname " + surname);

		return getByAddress(address);
	}

	/**
	 * @param address
	 * @return
	 */
	public RollingSpider getByAddress(String address) {

		for (RollingSpider rs : getAvailables()) {
			if (rs.getDev().getAddress().equals(address)) {
				return rs;
			}
		}
		return null;
	}

	/**
	 * @param rsAddress
	 * @param surname
	 */
	public void specifyAndRemindSurname(String rsAddress, String surname) {
		this.surnames.put(rsAddress, surname);
	}

	/**
	 * @param dv
	 * @return
	 */
	private static boolean isRollingSpider(DeviceIdentifier dv) {
		return dv.getName().startsWith("RS_");
	}
}
