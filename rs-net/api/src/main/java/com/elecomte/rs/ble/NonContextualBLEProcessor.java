package com.elecomte.rs.ble;

/**
 * @author Emmanuel Lecomte (elecomte)
 *
 */
abstract class NonContextualBLEProcessor implements BLEProcessor {

	@Override
	public boolean supportConnection() {
		return false;
	}

}
