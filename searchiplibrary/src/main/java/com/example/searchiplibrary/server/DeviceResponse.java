package com.example.searchiplibrary.server;

import com.example.searchiplibrary.server.callback.IDeviceResCallback;
import com.example.searchiplibrary.server.impl.DeviceResLogic;

public class DeviceResponse {

    private DeviceResLogic deviceResLogic;

    private static class Handler {
        private static final DeviceResponse INSTANCE = new DeviceResponse();
    }

    public static DeviceResponse getInstance() {
        return Handler.INSTANCE;
    }


    public void execute(IDeviceResCallback deviceResCallback) {
        deviceResLogic = new DeviceResLogic(deviceResCallback);
        deviceResLogic.execute();
    }

    public void close() {
        if (deviceResLogic == null) {
            throw new RuntimeException("please call execute");
        }
        deviceResLogic.close();
    }
}
