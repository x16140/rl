package io.arct.rl.exceptions

class InvalidDeviceException(device: String) :
    Exception("$device is not a valid Device!")