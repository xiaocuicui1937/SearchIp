package com.example.searchiplibrary.client.handler;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.example.searchiplibrary.client.bean.DeviceBean;
import com.example.searchiplibrary.client.callback.ISearchDeviceCallback;
import com.example.searchiplibrary.constact.Constact;

import java.util.List;

public class DeviceFoundHandler extends Handler {
    private ISearchDeviceCallback iSearchDeviceCallback;

    public DeviceFoundHandler(ISearchDeviceCallback iSearchDeviceCallback) {
        this.iSearchDeviceCallback = iSearchDeviceCallback;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (iSearchDeviceCallback == null) {
            return;
        }
        switch (msg.what) {
            case Constact.FOUND_START:

                iSearchDeviceCallback.onSearchStart();
                break;
            case Constact.FOUND_NEW_DEVICE:
                iSearchDeviceCallback.onFoundNewDevices((List<DeviceBean>) msg.obj);
                break;
            case Constact.FOUND_FAIL:
                iSearchDeviceCallback.onFoundException((String) msg.obj);
                    break;
            case Constact.FOUND_FINISH:
                iSearchDeviceCallback.onFinishDevice();
                break;
            default:
                break;
        }
    }
}
