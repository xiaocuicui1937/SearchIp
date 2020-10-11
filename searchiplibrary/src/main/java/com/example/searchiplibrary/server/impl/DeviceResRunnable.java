package com.example.searchiplibrary.server.impl;

import android.os.Build;
import android.os.Message;
import android.util.Log;

import com.example.searchiplibrary.constact.Constact;
import com.example.searchiplibrary.server.handler.DeviceResHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DeviceResRunnable implements Runnable {
    private DatagramSocket socket;
    private boolean openFlag;
    DeviceResHandler deviceResHandler;

    public DeviceResRunnable(DeviceResHandler deviceResHandler) {
        this.deviceResHandler = deviceResHandler;
    }

    @Override
    public void run() {
        execute();
    }

    public void destory() {
        if (socket != null) {
            socket.close();
            socket = null;
            openFlag = false;
        }
    }

    private void updateStatus(int type, Object value) {
        Message msg = new Message();
        msg.what = type;
        if (value != null) {
            msg.obj = value;
        }
        deviceResHandler.sendMessage(msg);
    }

    private void execute() {

        updateStatus(Constact.RES_START, null);
        try {
            //指定接收数据包的端口
            socket = new DatagramSocket(Constact.DEVICE_FOUND_PORT);
            byte[] buf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(buf, buf.length);
            openFlag = true;
            while (openFlag) {
                socket.receive(recePacket);
                //校验数据包是否是搜索包
                if (verifySearchData(recePacket)) {
                    //发送搜索应答包
                    byte[] sendData = packSearchRespData();
                    DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, recePacket.getSocketAddress());
                    socket.send(sendPack);
                    updateStatus(Constact.RES_SUCCESS, sendPack);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            updateStatus(Constact.RES_FAIL, e.getMessage());
            destory();
        }
    }

    /**
     * 生成搜索应答数据
     * 协议：$(1) + packType(1) + sendSeq(4) + dataLen(1) + [data]
     * packType - 报文类型
     * sendSeq - 发送序列
     * dataLen - 数据长度
     * data - 数据内容
     *
     * @return
     */
    private byte[] packSearchRespData() {
        byte[] data = new byte[1024];
        int offset = 0;
        data[offset++] = Constact.PACKET_PREFIX;
        data[offset++] = Constact.PACKET_TYPE_SEARCH_DEVICE_RSP;

        // 添加UUID数据
        byte[] uuid = getUuidData();
        data[offset++] = (byte) uuid.length;
        System.arraycopy(uuid, 0, data, offset, uuid.length);
        offset += uuid.length;
        byte[] retVal = new byte[offset];
        System.arraycopy(data, 0, retVal, 0, offset);
        return retVal;
    }

    /**
     * 校验搜索数据是否符合协议规范
     * 协议：$(1) + packType(1) + sendSeq(4) + dataLen(1) + [data]
     * packType - 报文类型
     * sendSeq - 发送序列
     * dataLen - 数据长度
     * data - 数据内容
     */
    private boolean verifySearchData(DatagramPacket pack) {
        if (pack.getLength() < 6) {
            return false;
        }

        byte[] data = pack.getData();
        int offset = pack.getOffset();
        int sendSeq;
        if (data[offset++] != '$' || data[offset++] != Constact.PACKET_TYPE_SEARCH_DEVICE_REQ) {
            return false;
        }
        sendSeq = data[offset++] & 0xFF;
        sendSeq |= (data[offset++] << 8) & 0xFF00;
        sendSeq |= (data[offset++] << 16) & 0xFF0000;
        sendSeq |= (data[offset++] << 24) & 0xFF000000;
        if (sendSeq < 1 || sendSeq > Constact.RECEIVE_TIME_OUT) {
            return false;
        }

        return true;
    }

    /**
     * 获取设备uuid
     *
     * @return byte[]
     */
    private byte[] getUuidData() {
        return (Build.PRODUCT + Build.ID).getBytes();
    }

}
