package com.example.searchiplibrary.client;

import com.example.searchiplibrary.client.callback.ISearchDeviceCallback;
import com.example.searchiplibrary.client.impl.DeviceFoundLogic;

public class DeviceFound {
    public DeviceFound(){}

    private static class Handler{
        private static DeviceFound INSTANCE = new DeviceFound();
    }

    public static DeviceFound getInstances(){
        return Handler.INSTANCE;
    }

    public void searchDevice(ISearchDeviceCallback searchDeviceCallback){
        new DeviceFoundLogic(searchDeviceCallback).execute();
    }
}
