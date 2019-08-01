package com.jun.springbootstorm.util.kafka;

import com.jun.springbootstorm.config.ApplicationConfiguration;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;


/**
 *
 * @Title: KafkaConsumerUtil
 * @Description:
 * kafka消息消费者
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月6日
 */
public class KafkaConsumerUtil {

	// kafka配置信息路径
	private static final String PATH = "kafka/kafka.properties";

	private static KafkaConsumer consumer;

	private static Properties props;

	public KafkaConsumerUtil(){
	}

	public KafkaConsumerUtil(Properties props){
		this.props = props;
	}

//	public Properties initProperties(){
//		Properties properties = new Properties();
//		try {
//			properties.load(KafkaConsumerUtil.class.getClassLoader().getResourceAsStream(PATH));
//		} catch (IOException e) {
//			logger.error("加载失败", e);
//		}
//		return properties;
//	}
	/**
	 * 初始化kafka配置
	 */
	public KafkaConsumer<String,String> getKafkaConsumer(ApplicationConfiguration app, String topic){
		if(null == props){
			props = new Properties();
			props.put("bootstrap.servers", app.getServersUrl());
			props.put("max.poll.records", app.getMaxPollRecords());
			props.put("enable.auto.commit", app.getAutoCommit());
			props.put("group.id", app.getGroupId());
			props.put("auto.offset.reset", app.getCommitRule());
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		}
		consumer = new KafkaConsumer<String, String>(props);
		this.consumer.subscribe(Arrays.asList(topic));
		return consumer;
	}

}
