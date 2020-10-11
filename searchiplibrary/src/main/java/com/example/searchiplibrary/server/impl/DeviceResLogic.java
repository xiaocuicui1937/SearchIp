package com.example.searchiplibrary.server.impl;

import com.example.searchiplibrary.server.callback.IDeviceResCallback;
import com.example.searchiplibrary.server.handler.DeviceResHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceResLogic {
    private ExecutorService executorService;
    private IDeviceResCallback iDeviceResCallback;
    private DeviceResRunnable deviceResRunnable;

    public DeviceResLogic(IDeviceResCallback iDeviceResCallback) {
        this.iDeviceResCallback = iDeviceResCallback;
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(1);
        }
        if (deviceResRunnable == null) {
            deviceResRunnable = new DeviceResRunnable(new DeviceResHandler(iDeviceResCallback));
        }
    }

    public void execute() {
        if (deviceResRunnable != null) {
            executorService.execute(deviceResRunnable);
        }
    }

    public void close() {
        if (deviceResRunnable != null) {
            deviceResRunnable.destory();
        }
    }
}
