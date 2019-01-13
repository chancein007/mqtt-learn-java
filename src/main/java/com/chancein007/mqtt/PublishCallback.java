package com.chancein007.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class  PublishCallback implements MqttCallback {
	private String threadId;

	public PublishCallback(String threadId) {
		this.threadId = threadId;
	}

	public void connectionLost(Throwable cause) {

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String msg = new String(message.getPayload());
		System.out.println(threadId + " " + msg);
	}
}