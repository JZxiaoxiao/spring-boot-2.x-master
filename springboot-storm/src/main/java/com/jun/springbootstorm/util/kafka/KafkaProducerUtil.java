package com.jun.springbootstorm.util.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;


/**
 *
 * @Title: KafkaProducerUtil
 * @Description:
 * kafka消息生产者
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月6日
 */
public final class KafkaProducerUtil {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerUtil.class);
	/**
	 * 向kafka发送单条消息
	 * @param msg 发送的消息
	 * @param url 发送的地址
	 * @param topicName 消息名称
	 * @return
	 * @throws Exception
	 */
	public static boolean sendMessage(String msg,String url,String topicName) {
		KafkaProducer<String, String> producer=null;
		boolean falg=false;
		try{
			Properties props=init(url);
			producer= new KafkaProducer<String, String>(props);
			producer.send(new ProducerRecord<String, String>(topicName,msg));
			falg=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			producer.close();
		}
		return falg;
	}


	/**
	 * 向kafka发送批量消息
	 * @param listMsg 发送的消息
	 * @param url 发送的地址
	 * @param topicName 消息名称
	 * @return
	 * @throws Exception
	 */
	public static boolean sendMessage(List<String> listMsg,String url,String topicName) {
		KafkaProducer<String, String> producer=null;
		boolean falg=false;
		try{
			Properties props=init(url);
			producer= new KafkaProducer<String, String>(props);
			for(String msg:listMsg){
				producer.send(new ProducerRecord<String, String>(topicName,msg));
			}
			falg=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			producer.close();
		}
		return falg;
	}

	/**
	 * 向kafka发送批量消息(回调监控发送是否成功)
	 * @param listMsg 发送的消息
	 * @param url 发送的地址
	 * @param topicName 消息名称
	 * @return
	 * @throws Exception
	 */
	public boolean sendMessageWithCallBack(List<String> listMsg,String url,String topicName) {
		KafkaProducer<String, String> producer=null;
		boolean falg=false;
		try{
			Properties props=init(url);
			producer= new KafkaProducer<String, String>(props);
			for(String msg:listMsg){
				producer.send(new ProducerRecord<String, String>(topicName,msg),new SendCallBack());
			}
			falg=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			producer.close();
		}
		return falg;
	}

	/**
	 * 初始化配置
	 * @param url kafka地址,多个地址则用‘,’隔开
	 * @return
	 */
	private static Properties init(String url){
		Properties props = new Properties();
		props.put("bootstrap.servers", url);
		//acks=0：如果设置为0，生产者不会等待kafka的响应。
		//acks=1：这个配置意味着kafka会把这条消息写到本地日志文件中，但是不会等待集群中其他机器的成功响应。
		//acks=all：这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。这是最强的可用性保证。
		props.put("acks", "all");
		//配置为大于0的值的话，客户端会在消息发送失败时重新发送。
		props.put("retries", 0);
		//当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这会提高client和生产者的效率
		props.put("batch.size", 16384);
		//发送时间间隔，默认0ms
		//props.put("linger.ms", 1);
		//一次性批量发送大小，默认是16384Bytes
		//props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		return props;
	}

	private class SendCallBack implements Callback {

		@Override
		public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			if(null != recordMetadata){
				logger.info("offset={},partition={},topic={},timestamp={}",
						recordMetadata.offset(),recordMetadata.partition(),recordMetadata.topic(),recordMetadata.timestamp());
			}else {
				logger.error("发送消息异常！");
			}
		}
	}
}
