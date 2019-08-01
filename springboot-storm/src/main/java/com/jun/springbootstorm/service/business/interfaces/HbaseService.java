package com.jun.springbootstorm.service.business.interfaces;

import java.util.List;
import java.util.Map;

public interface HbaseService {
    String get(String tableName, String rowKey, String familyName, String qualifier);

    Map<String, String> get(String tableName, String rowKey, String familyName);

    void put(String tableName, String rowKey, String familyName, String column, String value);

    void put(String tableName, String rowKey, String familyName, Map<String, String> rowMap);

    void delete(String tableName, String rowKey, String familyName);

    void createTable(String tableName, List<String> familyNameList);
}
