package com.jun.springbootstorm.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.jun.springbootstorm.SpringBootStormApplication;
import com.jun.springbootstorm.base.constant.Constants;
import com.jun.springbootstorm.config.ApplicationConfiguration;
import com.jun.springbootstorm.dao.mapper.bo.TestUser;
import com.jun.springbootstorm.service.atom.interfaces.TestUserAtomSV;
import com.jun.springbootstorm.util.SpringBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JZxiaoxiao
 * @Title: InsertMysqlBolt
 * @Description: 写入数据的bolt
 * @Version:1.0.0
 * @date 2019年5月6日
 */
public class InsertRedisBolt extends BaseRichBolt {

    private static final long serialVersionUID = 6542256546124282695L;

    private static final Logger logger = LoggerFactory.getLogger(InsertRedisBolt.class);

    private OutputCollector collector;

    private ApplicationConfiguration app;

    private StringRedisTemplate stringRedisTemplate;


    @SuppressWarnings("rawtypes")
    @Override
    public void prepare(Map map, TopologyContext arg1, OutputCollector collector) {
        SpringBootStormApplication.run(); //集群模式设置spring 上下文，单机环境注释
        this.collector = collector;
        stringRedisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
        app = SpringBeanUtil.getBean(ApplicationConfiguration.class);
    }


    @Override
    public void execute(Tuple tuple) {
        //String msg=tuple.getString(0);
        String msg=tuple.getStringByField(Constants.Spout.INSERT_SPOUT);
        //拆包
        logger.info("接收到数据："+msg);
        List<String> list = new ArrayList<>();
        if(!StringUtils.isBlank(msg)) {
            list = JSON.parseObject(msg, List.class);
            for(String record:list) {
                try {
                    TestUser user = JSON.parseObject(record, TestUser.class);
                    stringRedisTemplate.opsForHash().put("user", user.getUserId().toString(), JSON.toJSONString(user));
                }catch (Exception e){
                    logger.error("");
                    e.printStackTrace();
                }
            }
            this.collector.ack(tuple);
        }
    }

    /**
     * cleanup是IBolt接口中定义,用于释放bolt占用的资源。
     * Storm在终止一个bolt之前会调用这个方法。
     */
    @Override
    public void cleanup() {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer arg0) {

    }


}
