package com.example.searchiplibrary.client.bean;

public class DeviceBean {
    private String uuid;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备ip
     */
    private int port;

    public DeviceBean(String name, int port,String uuid) {
        this.uuid = uuid;
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int ip) {
        this.port = ip;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", port=" + port +
                '}';
    }
}
