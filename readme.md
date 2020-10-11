##简介

局域网内使用UDP组广播的方式，搜索注册过的服务端的所有设备ip、端口号信息

##用法

客户端：使用单例模式，搜索后并有回调的方式
 ```
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
```
服务端:使用单例模式，注册后会有回调方法，提供关闭功能
```
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
```

#使用远程依赖的方式快速使用本库

Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```
Tag:1.1.0
dependencies {
	        implementation 'com.github.xiaocuicui1937:SearchIp:Tag'
}
```
