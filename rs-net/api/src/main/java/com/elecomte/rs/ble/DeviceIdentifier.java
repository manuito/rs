package com.elecomte.rs.ble;

public class DeviceIdentifier {

    	private final String name;
    	private final String address;

    protected DeviceIdentifier(String name, String address){
		this.name = name;
		this.address = address;
	}

	public String getName(){
		return this.name;
	}

	public String getAddress(){
		return this.address;
	}
}
