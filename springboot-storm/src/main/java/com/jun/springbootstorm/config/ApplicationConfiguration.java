package com.jun.springbootstorm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @Title: ApplicationConfiguration
 * @Description:
 * 获取application文件的信息
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月6日
 */
@Component("applicationConfiguration")
public class ApplicationConfiguration {
	@Value("${kafka.topic.name}")
    private String kafkaTopicName;
		
	@Value("${kafka.servers.url}")
    private String serversUrl;

	@Value("${kafka.maxPollRecords}")
    private int maxPollRecords; 
		
	@Value("${kafka.commitRule}")
    private String commitRule;
	
	@Value("${kafka.autoCommit}")
	private String autoCommit;

	@Value("kafka.group.id")
    private String groupId;
	/**  
	 * 获取topicName  
	 * @return  topicName  
	 */
	public String getKafkaTopicName() {
		return kafkaTopicName;
	}

	/**  
	 * 获取servers  
	 * @return  servers  
	 */
	public String getServersUrl() {
		return serversUrl;
	}

	/**  
	 * 获取maxPollRecords  
	 * @return  maxPollRecords  
	 */
	public int getMaxPollRecords() {
		return maxPollRecords;
	}

	/**  
	 * 获取commitRule  
	 * @return  commitRule  
	 */
	public String getCommitRule() {
		return commitRule;
	}
	
	/**
	 * 获取autoCommit  
	 * @return  autoCommit  
	 */
	public String getAutoCommit() {
		return autoCommit;
	}

	/**  
	 * 获取groupId  
	 * @return  groupId  
	 */
	public String getGroupId() {
		return groupId;
	}
	
}