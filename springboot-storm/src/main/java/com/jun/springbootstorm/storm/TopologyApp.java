package com.jun.springbootstorm.storm;

import com.jun.springbootstorm.base.constant.Constants;
import com.jun.springbootstorm.storm.bolt.InsertHbaseBolt;
import com.jun.springbootstorm.storm.bolt.InsertMysqlBolt;
import com.jun.springbootstorm.storm.bolt.InsertRedisBolt;
import com.jun.springbootstorm.storm.spout.InsertSpout;
import kafka.api.OffsetRequest;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.stereotype.Component;
import org.apache.storm.Config;

import java.util.UUID;

/**
 *
 * @Title: TopologyApp
 * @Description: TopologyApp
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月6日
 */
@Component
public class TopologyApp {

	private static final String topologyName = "";

	/**
	 * 根据kafka partition 策略设置
	 */
	private static final int DEFAULT_SPOT_EXECUTOR = 3;

	/**
	 * 本地模式
	 * 用于业务调试
	 */
	public void runLocalStorm(){
		try {
			// 定义拓扑
			TopologyBuilder builder = initTopologyBuilder();
			Config conf = new Config();
			//设置spout中的等待处理的数据量的大小
			conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 100000);
			conf.setNumWorkers(9);
			//建议设置一个应答者
			conf.setNumAckers(1);
			System.out.println("运行本地模式");
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyName, conf, builder.createTopology());
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("storm启动失败!程序退出!");
			System.exit(1);
		}
		System.out.println("storm启动成功...");
	}

	/**
	 * Storm 集群启动入口
	 * @param args
	 * args[0]:拓扑名称
	 * args[1]：拓扑worker 配置
	 * args[2]：拓扑Ackers 配置
	 * Executeor 可以热分配
	 */
	public static void main(String[] args) {
		try {
			if (args != null && args.length > 0) {
				//建议设置2个work(进程)
				System.out.println("拓扑名称 args[0]:"+args[0]);
				System.out.println("拓扑worker args[1]:"+args[1]);
				System.out.println("拓扑Ackers args[2]:"+args[2]);
				// 定义拓扑
				TopologyBuilder builder = initTopologyBuilder();
				Config conf = new Config();
				//设置spout中的等待处理的数据量的大小
				conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 100000);
				conf.setNumWorkers(Integer.parseInt(args[1]));
				conf.setNumAckers(Integer.parseInt(args[2]));
				System.out.println("运行远程模式");
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} else {
				throw new Exception("storm启动参数args为空！");
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("storm启动失败!程序退出!");
			System.exit(1);
		}
		System.out.println("storm启动成功...");
	}
	/**
	 * Storm 初始化拓扑
	 * @param args
	 */
	private static TopologyBuilder initTopologyBuilder(){
		TopologyBuilder builder = new TopologyBuilder();
		//initkafkaSpot(builder);
		//默认输入
		builder.setSpout(Constants.Spout.INSERT_SPOUT, new InsertSpout(), DEFAULT_SPOT_EXECUTOR);
		builder.setBolt(Constants.Bolt.INSERT_MYSQL_BOLT,
				new InsertMysqlBolt(), 2).setNumTasks(2).shuffleGrouping(Constants.Spout.INSERT_SPOUT);
		builder.setBolt(Constants.Bolt.INSERT_REDIS_BOLT,
				new InsertRedisBolt(), 1).setNumTasks(1).shuffleGrouping(Constants.Spout.INSERT_SPOUT);
		builder.setBolt(Constants.Bolt.INSERT_HBASE_BOLT,
				new InsertHbaseBolt(), 1).setNumTasks(1).shuffleGrouping(Constants.Spout.INSERT_SPOUT);
		return builder;
	}

	/**
	 * 默认KafkaSpout
	 * @param builder
	 */
	private static void initkafkaSpot(TopologyBuilder builder){
		BrokerHosts hosts = new ZkHosts("192.166.21.239:9092");
		SpoutConfig spoutConfig = new SpoutConfig(hosts, "test-storm", "/zkRoot", UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		//从Kafka最新输出日志读取
		spoutConfig.startOffsetTime = OffsetRequest.LatestTime();
		builder.setSpout(Constants.Spout.INSERT_SPOUT, new KafkaSpout(spoutConfig), 1);
	}

	public void test(){
		System.out.println("hhhhhhhhhhhhh");
	}
}
