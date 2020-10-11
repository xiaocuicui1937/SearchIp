package com.example.searchiplibrary.client.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.searchiplibrary.client.bean.DeviceBean;
import com.example.searchiplibrary.constact.Constact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceFoundRunnable implements Runnable {
    private static final int MAX_SEARCH_COUNT = 3;
    Handler handler;

    public DeviceFoundRunnable(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        deviceFound();
    }

    private void updateStatus(int type, Object value) {
        Message msg = new Message();
        msg.what = type;
        if (value != null) {
            msg.obj = value;
        }
        handler.sendMessage(msg);
    }

    private void deviceFound() {
        updateStatus(Constact.FOUND_START, null);
        //用于存放应答设备
        HashMap<String, DeviceBean> deviceHashMap = new HashMap<>();
        try (DatagramSocket socket = new DatagramSocket()) {

            //设置等待时长
            socket.setSoTimeout(Constact.RECEIVE_TIME_OUT);
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            //使用广播形式(目标地址设为255.255.255.255)的udp数据包
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), Constact.DEVICE_FOUND_PORT);

            int current = 1;
            for (int i = 0; i < MAX_SEARCH_COUNT; i++) {
                sendPacket.setData(packSearchData(i + 1));
                //发送UDP数据包
                socket.send(sendPacket);

                while (current < Constact.MAX_IP_COUNT) {
                    socket.receive(receivePacket);
                    DeviceBean deviceBean = parseRespData(receivePacket);
                    Log.e("deviceBean.toString()", deviceBean.toString());
                    if (deviceHashMap.get(deviceBean.getName()) == null) {
                        deviceHashMap.put(deviceBean.getName(), deviceBean);
                    }
                    current++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException && !deviceHashMap.isEmpty()){
               return;
            }
            updateStatus(Constact.FOUND_FAIL, e.getMessage());
        } finally {
            toDeviceArray(deviceHashMap);
            updateStatus(Constact.FOUND_FINISH, null);
        }
    }

    private void toDeviceArray(HashMap<String, DeviceBean> deviceHashMap) {
        if (!deviceHashMap.isEmpty()) {
            List<DeviceBean> deviceBeans = new ArrayList<>();
            for (Map.Entry<String, DeviceBean> param : deviceHashMap.entrySet()) {
                deviceBeans.add(param.getValue());
            }
            for (int i = 0; i < deviceBeans.size(); i++) {
                Log.e("deviceBeans", deviceBeans.get(i).toString());
            }
            updateStatus(Constact.FOUND_NEW_DEVICE, deviceBeans);
        }
    }

    /**
     * 校验和解析应答的数据包
     *
     * @param pack udp数据包
     * @return
     */
    private DeviceBean parseRespData(DatagramPacket pack) {
        if (pack.getLength() < 2) {
            return null;
        }
        byte[] data = pack.getData();
        int offset = pack.getOffset();
        //检验数据包格式是否符合要求
        if (data[offset++] != Constact.PACKET_PREFIX || data[offset++] != Constact.PACKET_TYPE_SEARCH_DEVICE_RSP) {
            return null;
        }
        int length = data[offset++];
        String uuid = new String(data, offset, length);
        return new DeviceBean(pack.getAddress().getHostAddress(), pack.getPort(), uuid);
    }


    /**
     * 生成搜索数据包
     * 格式：$(1) + packType(1) + sendSeq(4) + dataLen(1) + [data]
     * packType - 报文类型
     * sendSeq - 发送序列
     * dataLen - 数据长度
     * data - 数据内容
     *
     * @param seq
     * @return
     */
    private byte[] packSearchData(int seq) {
        byte[] data = new byte[6];
        int offset = 0;
        data[offset++] = Constact.PACKET_PREFIX;
        data[offset++] = Constact.PACKET_TYPE_SEARCH_DEVICE_REQ;
        data[offset++] = (byte) seq;
        data[offset++] = (byte) (seq >> 8);
        data[offset++] = (byte) (seq >> 16);
        data[offset++] = (byte) (seq >> 24);
        return data;
    }

}
