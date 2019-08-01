package com.jun.springbootstorm.service.business.impl;

import com.jun.springbootstorm.service.business.interfaces.HbaseService;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Title: HbaseServiceImpl
 * Description:
 * Hbase操作实现类
 * Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月16日
 */
@Service("hbaseServiceImpl")
public class HbaseServiceImpl implements HbaseService {

    @Autowired
    HbaseTemplate hbaseTemplate;

    private static final Logger logger = LoggerFactory.getLogger(HbaseServiceImpl.class);

    /**
     *
     * @param tableName 表名
     * @param rowKey 指定行
     * @param familyName 列簇
     * @param qualifier 列值
     * @return 获取表中指定行，列簇，列的 值
     */
    @Override
    public String get(String tableName, String rowKey, String familyName,
                      String qualifier) {
        return hbaseTemplate.get(tableName, rowKey, familyName, qualifier, new RowMapper<String>() {
            public String mapRow(Result result, int rowNum)
                    throws Exception {
                List<Cell> ceList = result.listCells();
                String res = "";
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        res = Bytes.toString(cell.getValueArray(),
                                cell.getValueOffset(),
                                cell.getValueLength());
                    }
                }
                return res;
            }
        });
    }

    /**
     *
     * @param tableName 表名
     * @param rowKey 指定行
     * @param familyName 列簇
     * @return 获取表中指定行，列簇 的列和值
     */
    @Override
    public Map<String, String> get(String tableName, String rowKey, String familyName) {
        return hbaseTemplate.get(tableName, rowKey, new RowMapper<Map<String, String>>() {
            public Map<String, String> mapRow(Result result, int rowNum) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                if(result.listCells()!=null && result.listCells().size()>0){
                    Map<byte[], byte[]> mapColumn = result.getFamilyMap(Bytes.toBytes(familyName));
                    for(Map.Entry<byte[], byte[]> entry : mapColumn.entrySet()){
                        map.put(Bytes.toString(entry.getKey()),Bytes.toString(entry.getValue()));
                    }
                }
                return map;
            }
        });
    }

    /**
     *
     * @param tableName 表名
     * @param rowKey 指定行
     * @param familyName 指定列簇
     * @param column 列名
     * @param value 值
     */
    @Override
    public void put(String tableName, String rowKey, String familyName,
                    String column, String value) {
        hbaseTemplate.put(tableName, rowKey, familyName, column, Bytes.toBytes(value));
    }

    /**
     *
     * @param tableName 表名
     * @param rowKey 指定行
     * @param familyName 指定列簇
     * @param rowMap 列名，值的Map
     */
    @Override
    public void put(String tableName, String rowKey, String familyName,
                    Map<String,String> rowMap) {
        for (Map.Entry<String, String> en : rowMap.entrySet()) {
            hbaseTemplate.put(tableName, rowKey, familyName, en.getKey(), Bytes.toBytes(en.getValue()));
        }
    }

    /**
     *
     * @param tableName 表名
     * @param rowKey 指定行
     * @param familyName 指定列簇
     * 删除指定表名指定行指定列簇的值
     */
    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        hbaseTemplate.delete(tableName,rowKey,familyName);
    }

    /**
     *
     * @param tableName 表名
     * @param familyNameList 指定列簇List
     * 建表
     */
    @Override
    public void createTable(String tableName, List<String> familyNameList) {
        synchronized(this) {
            try {
                HBaseAdmin hBaseAdmin = new HBaseAdmin(hbaseTemplate.getConfiguration());
                if (hBaseAdmin.tableExists(tableName)) {
                    //如果存在要创建的表
                    logger.debug(tableName + "已经存在，无需创建");
                    //hBaseAdmin.disableTable(tableName);
                    //hBaseAdmin.deleteTable(tableName);
                } else {
                    HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
                    for (String familyName : familyNameList) {
                        tableDescriptor.addFamily(new HColumnDescriptor(familyName));
                    }
                    hBaseAdmin.createTable(tableDescriptor);
                }
            } catch (IOException e) {
                logger.error("Hbase 表：" + tableName + "建表失败", e);
            }
        }
    }
}
