package com.elecomte.rs;

import java.util.HashMap;

class RollingSpiderCharDef {
	private static HashMap<String, String> attributes = new HashMap<>();

	static {
		// Parrot specific Services
		attributes.put("9a66fa00-0800-9191-11e4-012d1540cb8e", "Parrot Service - A00");
		attributes.put("9a66fb00-0800-9191-11e4-012d1540cb8e", "Parrot Service - B00"); // handle 0x92
		attributes.put("9a66fc00-0800-9191-11e4-012d1540cb8e", "Parrot Service - C00");
		attributes.put("9a66fd21-0800-9191-11e4-012d1540cb8e", "Parrot Service - D21");
		attributes.put("9a66fd51-0800-9191-11e4-012d1540cb8e", "Parrot Service - D51");

		// Parrot minidrone Rolling Spider specific characteristics
		attributes.put("9a66fa01-0800-9191-11e4-012d1540cb8e", "Tour the Stairs/Parrot - A01"); // + complete range to
																								// A1F (handle 0x22 -
																								// 0x7F)
		attributes.put("9a66fa02-0800-9191-11e4-012d1540cb8e", "   !!! S T O P    !!!");
		attributes.put("9a66fa0a-0800-9191-11e4-012d1540cb8e", "Parrot - Power Motors"); // handle 0x40
		attributes.put("9a66fa0b-0800-9191-11e4-012d1540cb8e", "Parrot - Date/Time"); // handle 0x43, 1:2014-10-29,
																						// 2:T223238+0100, 3:0x0
		attributes.put("9a66fa0c-0800-9191-11e4-012d1540cb8e", "Parrot - Emergency Stop"); // handle 0x46,
																							// send("02 00 04 00")

		attributes.put("9a66fa1e-0800-9191-11e4-012d1540cb8e", "Parrot - init count 1..20"); // handle 0x7C
		attributes.put("9a66fa1f-0800-9191-11e4-012d1540cb8e", "Parrot - A1F"); // handle 0x7F
		attributes.put("9a66fb01-0800-9191-11e4-012d1540cb8e", "Parrot - B01"); // + complete range to B1F (handle
																				// 0x92-0xEF)
		attributes.put("9a66fb0e-0800-9191-11e4-012d1540cb8e", "Parrot - B0E/BC-BD"); // handle 0xBC
		attributes.put("9a66fb0f-0800-9191-11e4-012d1540cb8e", "Parrot Battery - B0F/BF-C0"); // handle 0xBF-0xC0
		attributes.put("9a66fb1b-0800-9191-11e4-012d1540cb8e", "Parrot - B1B/E3-E4"); // handle 0xE3
		attributes.put("9a66fb1c-0800-9191-11e4-012d1540cb8e", "Parrot - B1C/E6-E7"); // handle 0xE6
		attributes.put("9a66fb1f-0800-9191-11e4-012d1540cb8e", "Parrot - B1F");
		attributes.put("9a66ffc1-0800-9191-11e4-012d1540cb8e", "Parrot - FC1"); // handle 0x102
		attributes.put("9a66fd22-0800-9191-11e4-012d1540cb8e", "Parrot - D22"); // handle 0x112
		attributes.put("9a66fd23-0800-9191-11e4-012d1540cb8e", "Parrot - D23"); // handle 0x115
		attributes.put("9a66fd24-0800-9191-11e4-012d1540cb8e", "Parrot - D24"); // handle 0x118
		attributes.put("9a66fd52-0800-9191-11e4-012d1540cb8e", "Parrot - D52"); // handle 0x122
		attributes.put("9a66fd53-0800-9191-11e4-012d1540cb8e", "Parrot - D53"); // handle 0x125
		attributes.put("9a66fd54-0800-9191-11e4-012d1540cb8e", "Parrot - D54"); // handle 0x128
	}

	public static String lookup(String uuid, String defaultName) {
		String name = attributes.get(uuid);
		return name == null ? defaultName : name;
	}
}
