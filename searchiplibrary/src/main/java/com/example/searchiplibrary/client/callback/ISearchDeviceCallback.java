package com.example.searchiplibrary.client.callback;

import com.example.searchiplibrary.client.bean.DeviceBean;

import java.util.List;

public interface ISearchDeviceCallback {
    /**
     * 开始搜索设备
     */
    void onSearchStart();

    /**
     * 搜索到新设备使用集合的方式
     */
    void onFoundNewDevices(List<DeviceBean> devices);

    /**
     * 搜索过程中出现异常
     */
    void onFoundException(String exceptionMsg);
    /**
     * 搜索完成
     */
    void onFinishDevice();
}
