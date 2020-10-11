package com.example.searchiplibrary.client.impl;

import com.example.searchiplibrary.client.callback.ISearchDeviceCallback;
import com.example.searchiplibrary.client.handler.DeviceFoundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceFoundLogic {
    private ExecutorService mExecutorService;
    private ISearchDeviceCallback iSearchDeviceCallback;

    public DeviceFoundLogic(ISearchDeviceCallback iSearchDeviceCallback) {
        if (mExecutorService == null) {
            mExecutorService = Executors.newFixedThreadPool(1);
        }
        this.iSearchDeviceCallback = iSearchDeviceCallback;
    }

    public void execute() {
        mExecutorService.execute(new DeviceFoundRunnable(new DeviceFoundHandler(iSearchDeviceCallback)));
    }


}
