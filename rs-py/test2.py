#!bin/python

from gattlib import GATTRequester

req = GATTRequester("E0:14:88:54:3D:51")
name = req.read_by_uuid("00002a01-0000-1000-8000-00805f9b34fb")[0]
steps = req.read_by_handle(0x15)[0]
