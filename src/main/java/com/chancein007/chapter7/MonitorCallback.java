package com.chancein007.chapter7;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 实现org.eclipse.paho.client.mqttv3.MqttCallback接口
 * MQTT的订阅端接收到消息后，会主动调用其回调方法 messageArrived(String topic, MqttMessage message)
 * @author 朱清云
 *
 */
public class MonitorCallback implements MqttCallback {
	

	public MonitorCallback() {
	}

	public void connectionLost(Throwable cause) {

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	/**
	 * 输出MQTT的订阅段接收到的消息
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String msg = new String(message.getPayload());
		System.out.println(msg);
	}
}