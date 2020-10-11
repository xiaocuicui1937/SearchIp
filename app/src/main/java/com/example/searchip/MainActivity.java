package com.example.searchip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.searchiplibrary.client.DeviceFound;
import com.example.searchiplibrary.client.bean.DeviceBean;
import com.example.searchiplibrary.client.callback.ISearchDeviceCallback;
import com.example.searchiplibrary.server.DeviceResponse;
import com.example.searchiplibrary.server.callback.IDeviceResCallback;

import java.net.DatagramPacket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerServer();
    }

    private void registerServer(){
        DeviceResponse.getInstance().execute(new IDeviceResCallback() {
            @Override
            public void onStartRes() {
                //注册服务端开始
            }

            @Override
            public void onRes(DatagramPacket res) {
                //服务端响应
                //注意：res不能再main线程中调用，否则会报异常
            }

            @Override
            public void onResException(String exceptionMsg) {
                //异常信息
            }
        });
    }

    private void searchDevice(){
        DeviceFound.getInstances().searchDevice(new ISearchDeviceCallback() {
            @Override
            public void onSearchStart() {
                //开始搜索设备
                //此处可以添加等待动画效果
            }

            @Override
            public void onFoundNewDevices(List<DeviceBean> devices) {
                //返回所有的设备信息
            }

            @Override
            public void onFoundException(String exceptionMsg) {
                //捕获异常信息
            }

            @Override
            public void onFinishDevice() {
                //搜索完成
                //关闭等待动画效果
            }
        });
    }

    public void searchDevice(View view) {
        searchDevice();
    }
}