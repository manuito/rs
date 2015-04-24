package com.elecomte.rs;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Keep in memory only...
 * 
 * @author Emmanuel Lecomte (elecomte)
 *
 */
@Service
public class RollingSpiderAccess {

	@Autowired
	private RollingSpiderServiceAdapter service;

	private Map<String, String> surnames = new ConcurrentHashMap<>();

	/**
	 * @return
	 */
	public Collection<RollingSpider> getAvailables() {
		return this.service.getAvailables();
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
	public RollingSpider getByAddress(String address) throws RollingSpiderNotFoundException {

		for (RollingSpider rs : getAvailables()) {
			if (rs.getDev().getAddress().equals(address)) {
				return rs;
			}
		}
		throw new RollingSpiderNotFoundException("No device found for specified address " + address);
	}

	/**
	 * @param rsAddress
	 * @param surname
	 */
	public void specifyAndRemindSurname(String rsAddress, String surname) {
		this.surnames.put(rsAddress, surname);
	}
}
