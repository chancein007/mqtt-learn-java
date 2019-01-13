package com.chancein007.chapter7;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {

	private static int QOS = 2;
	private static String  MQTT_SERVER = "tcp://127.0.0.1:1883";
	private static String WILL_TOPIC_NAME = "senor/status";
	private static String WILL_MQTT_MESSAGE = "{location: 'B1-F2-R13-001',online:'false' }";
	private static String CLIENT_ID = "B1-F2-R13-001";

	/**
	 * 建立MQTT的通信连接
	 * 建立MQTT的连接，主要要输入MQTT服务器的连接地址，最后遗言的主题，最后遗言的消息内容，
	 * 最后遗言的消息质量，已经客户端的ID
	 * @param clientId
	 * @return
	 * @throws MqttException
	 */
	private MqttClient connect(String clientId) throws MqttException {
		MemoryPersistence persistence = new MemoryPersistence();
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setConnectionTimeout(10);
		connOpts.setKeepAliveInterval(20);
		connOpts.setWill(WILL_TOPIC_NAME, WILL_MQTT_MESSAGE.getBytes(), QOS, true);
		
		MqttClient mqttClient = new MqttClient(MQTT_SERVER, clientId, persistence);
		mqttClient.connect(connOpts);
		return mqttClient;
	}

	/**
	 * 发送MQTT的消息
	 * @param messsage
	 * @param clientId
	 * @param topic
	 * @throws MqttException
	 */
	public void publish(String messsage, String clientId, String topic) throws MqttException {

		MqttClient mqttClient = this.connect(clientId);
		if (mqttClient != null) {
			MqttMessage mqttMessage = new MqttMessage(messsage.getBytes());
			mqttMessage.setQos(QOS);
			mqttMessage.setRetained(false);
			mqttClient.publish(topic, mqttMessage);
			System.out.println("Publish MQTT message Body-->" + messsage);
		}
        /*
         * 如果要测试和模拟最后遗言的效果，则下面的3行代码必须注释掉。
         * 否则当MQTT消息发送端发送完成后，其会自动把连接正常关闭掉，这个时候是不会
         * 出发最后的遗言的。
		if (mqttClient != null) {
			mqttClient.disconnect();
		}*/
	}

	/**
	 * MQTT 发送端的入口调用程序
	 * @param args
	 */
	public static void main(String[] args) {
		MqttPublisher mqttPublisher = new MqttPublisher();
		try {
			String message="{location: 'B1-F2-R13-001',online:'true' }";
			mqttPublisher.publish(message, CLIENT_ID, WILL_TOPIC_NAME);
		} catch (MqttException mqttException) {
			mqttException.printStackTrace();
		}
	}
}
