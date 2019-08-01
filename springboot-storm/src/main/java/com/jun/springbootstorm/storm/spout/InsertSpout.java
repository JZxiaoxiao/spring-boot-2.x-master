package com.jun.springbootstorm.storm.spout;

import com.alibaba.fastjson.JSON;
import com.jun.springbootstorm.SpringBootStormApplication;
import com.jun.springbootstorm.base.constant.Constants;
import com.jun.springbootstorm.config.ApplicationConfiguration;
import com.jun.springbootstorm.util.SpringBeanUtil;
import com.jun.springbootstorm.util.kafka.KafkaConsumerUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 
* @Title: InsertSpout
* @Description: 
* 从kafka获取原始数据
* @Version:1.0.0  
* @author JZxiaoxiao
* @date 2019年5月6日
 */
public class InsertSpout extends BaseRichSpout{
	private static final long serialVersionUID = -4655062509210041487L;

	private static final Logger logger = LoggerFactory.getLogger(InsertSpout.class);

	private SpoutOutputCollector collector;

	private KafkaConsumer<String, String> consumer;

	private ConsumerRecords<String, String> msgList;

	private ApplicationConfiguration app;

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map map, TopologyContext arg1, SpoutOutputCollector collector) {
		SpringBootStormApplication.run(); //集群模式设置spring 上下文，单机环境注释
		app = SpringBeanUtil.getBean(ApplicationConfiguration.class);
		logger.info("app.getTopicName():"+app.getKafkaTopicName());
		logger.info("app.getServers():"+app.getServersUrl());
		consumer = new KafkaConsumerUtil().getKafkaConsumer(app,app.getKafkaTopicName());
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		while (true) {
			try {
				msgList = consumer.poll(100);
				if (null != msgList && !msgList.isEmpty()) {
					String msg = "";
					List<String> list = new ArrayList<>();
					for (ConsumerRecord<String, String> record : msgList) {
						// 原始数据
						msg = record.value();
						if (null == msg || "".equals(msg.trim())) {
							continue;
						}
						list.add(msg);
						logger.info("接收到数据:["+msg+"]");
					}
					//发送到bolt中
					this.collector.emit(new Values(JSON.toJSONString(list)));
					consumer.commitAsync();
				}else{
					TimeUnit.SECONDS.sleep(60);
					logger.info("未拉取到数据...");
				}
			} catch (Exception e) {
				logger.error("消息队列处理异常!",e);
				try {
					TimeUnit.SECONDS.sleep(120);
				} catch (InterruptedException e1) {
					logger.error("暂停失败!");
				}
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.Spout.INSERT_SPOUT));
	}

	/**
	 * 每个tuple执行失败后调用
	 * @param msgId
	 */
	@Override
	public void fail(Object msgId) {

	}
}
