package com.nikede.ibeacon.models

class BeaconModel(mac: String, rssi: String, val major: String, val minor: String, val distance: Double): DeviceModel(mac, rssi)