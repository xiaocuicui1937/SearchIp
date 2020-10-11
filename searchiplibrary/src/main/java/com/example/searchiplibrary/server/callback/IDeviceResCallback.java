package com.example.searchiplibrary.server.callback;

import java.net.DatagramPacket;

public interface IDeviceResCallback {
    /**
     * 开始响应
     */
    void onStartRes();

    /**
     * 已响应
     *
     * @param res DatagramPacket
     */
    void onRes(DatagramPacket res);

    /**
     * 获取异常信息
     *
     * @param exceptionMsg 异常信息
     */
    void onResException(String exceptionMsg);
}
