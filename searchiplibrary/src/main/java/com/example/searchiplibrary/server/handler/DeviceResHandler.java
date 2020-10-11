package com.example.searchiplibrary.server.handler;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.searchiplibrary.constact.Constact;
import com.example.searchiplibrary.server.callback.IDeviceResCallback;

import java.net.DatagramPacket;

public class DeviceResHandler extends Handler {
    IDeviceResCallback iDeviceResCallback;

    public DeviceResHandler(IDeviceResCallback iDeviceResCallback) {
        this.iDeviceResCallback = iDeviceResCallback;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (iDeviceResCallback == null) {
            return;
        }

        switch (msg.what) {
            case Constact.RES_START:
                iDeviceResCallback.onStartRes();
                break;
            case Constact.RES_SUCCESS:
                iDeviceResCallback.onRes((DatagramPacket) msg.obj);
                break;
            case Constact.RES_FAIL:
                iDeviceResCallback.onResException((String) msg.obj);
                break;
            default:
                break;
        }
    }
}
