package com.example.searchiplibrary.constact;

public class Constact {
    /**
     * 搜索设备开始
     */
    public static final int FOUND_START = 0x01;
    /**
     * 响应开始
     */
    public static final int RES_START = 0x21;
    /**
     * 发现新设备
     */
    public static final int FOUND_NEW_DEVICE = 0x02;

    /**
     * 响应成功
     */
    public static final int RES_SUCCESS = 0x22;

    /**
     * 索索设备完成
     */
    public static final int FOUND_FINISH = 0x03;

    /**
     * 搜索失败
     */
    public static final int FOUND_FAIL = 0x04;

    /**
     * 响应失败
     */
    public static final int RES_FAIL = 0x23;


    /**
     * 用于设备搜索的端口
     */
    public static final int DEVICE_FOUND_PORT = 8100;
    /**
     * 用于接收命令的端口
     */
    public static final int COMMAND_RECEIVE_PORT = 60001;


    /**
     * udp数据包前缀
     */
    public static final int PACKET_PREFIX = '$';
    /**
     * udp数据包类型：搜索类型
     */
    public static final int PACKET_TYPE_SEARCH_DEVICE_REQ = 0x10;

    /**
     * udp数据包类型：搜索应答类型
     */
    public static final int PACKET_TYPE_SEARCH_DEVICE_RSP = 0x11;

    /**
     * IP地址最大搜索数
     */

    public static final int MAX_IP_COUNT = 250;

    /**
     * 接收超时时间
     */
    public static final int RECEIVE_TIME_OUT = 10 * 1000;
}
