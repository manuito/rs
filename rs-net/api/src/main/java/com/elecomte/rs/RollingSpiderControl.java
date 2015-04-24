package com.elecomte.rs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Model : http://robotika.cz/robots/jessica/en from Martin Dlouhý
 * 
 * @author Emmanuel Lecomte (elecomte)
 */
@Service
public class RollingSpiderControl {

	@Autowired
	private RollingSpiderServiceAdapter adapter;

	private Map<String, RollingSpiderContext> contexts = new ConcurrentHashMap<>();

	/**
	 * @param rs
	 * @return
	 */
	public boolean isAvailable(RollingSpider rs) {
		return true;
	}

	private RollingSpiderContext getContext(RollingSpider rs) throws RollingSpiderNotInitializedException {
		if (rs == null || !this.contexts.containsKey(rs.getKey())) {
			throw new RollingSpiderNotInitializedException(
					"Rolling spider is not yet connected and context is not yet loaded");
		}

		return this.contexts.get(rs.getKey());
	}

	/**
	 * @param rs
	 * @return
	 */
	private RollingSpiderContext connectAndInitContext(RollingSpider rs) {

		RollingSpiderContext ctxt = this.adapter.connect(rs);

		this.contexts.put(rs.getKey(), ctxt);

		return ctxt;
	}

	/**
	 * @param rs
	 */
	public void makeItReady(RollingSpider rs) {

		RollingSpiderContext ctxt = connectAndInitContext(rs);

		// note at at least BD characteristics notification is "must have" otherwise it does not start
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fb0f-0800-9191-11e4-012d1540cb8e");// 0xCO
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fb0e-0800-9191-11e4-012d1540cb8e"); // 0xBD
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fb1b-0800-9191-11e4-012d1540cb8e"); // 0xE4
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fb1c-0800-9191-11e4-012d1540cb8e"); // 0xE7
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd22-0800-9191-11e4-012d1540cb8e"); // 0x113
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd23-0800-9191-11e4-012d1540cb8e"); // 0x116
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd24-0800-9191-11e4-012d1540cb8e"); // 0x119
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd52-0800-9191-11e4-012d1540cb8e"); // 0x123
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd53-0800-9191-11e4-012d1540cb8e"); // 0x126
		this.adapter.addCharacteristicListener(rs, ctxt, "9a66fd54-0800-9191-11e4-012d1540cb8e"); // 0x129

		// TODO start all available notifications
		// setAllNotification(true);

		for (int i = 0; i < 20; i++) {
			byte[] value = new byte[3];
			value[0] = (byte) (0x1);
			value[1] = (byte) (i + 1);
			value[2] = (byte) (i + 1);
			this.adapter.setCharacteristic(rs, ctxt, "9a66fa1e-0800-9191-11e4-012d1540cb8e", new String(value));
		}

	}

	/**
	 * @param rs
	 * @throws RollingSpiderNotInitializedException
	 */
	public void takeOff(RollingSpider rs) throws RollingSpiderNotInitializedException {
		RollingSpiderContext ctxt = getContext(rs);
		byte[] arr = { 4, (byte) ctxt.getSettingsCounter(), 2, 0, 1, 0 };
		this.adapter.setCharacteristic(rs, ctxt, "9a66fa0b-0800-9191-11e4-012d1540cb8e", new String(arr));
		ctxt.setSettingsCounter(ctxt.getSettingsCounter() + 1);
	}

	/**
	 * @param rs
	 * @throws RollingSpiderNotInitializedException
	 */
	public void land(RollingSpider rs) throws RollingSpiderNotInitializedException {
		RollingSpiderContext ctxt = getContext(rs);
		byte[] arr = { 4, (byte) ctxt.getSettingsCounter(), 2, 0, 3, 0 };
		this.adapter.setCharacteristic(rs, ctxt, "9a66fa0b-0800-9191-11e4-012d1540cb8e", new String(arr));
		ctxt.setSettingsCounter(ctxt.getSettingsCounter() + 1);
	}

	/**
	 * @param rs
	 * @return
	 * @throws RollingSpiderNotInitializedException
	 */
	public int getBaterryPercent(RollingSpider rs) throws RollingSpiderNotInitializedException {
		return getContext(rs).getBattery();
	}
}
