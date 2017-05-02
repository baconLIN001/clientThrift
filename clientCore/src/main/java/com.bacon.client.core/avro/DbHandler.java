package com.bacon.client.core.avro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b3434.Factory.ProcessorFactory;
import com.b3434.Processor.Processor;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RelationParam;
import com.bacon.client.core.base.DbUploadCode;
import com.bacon.client.core.element.DbConstant;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bacon on 2017/4/24.
 */
public class DbHandler {

    public static Connection getDbConnection(String dbType, String url, String username, String password){
        Connection connection = null;
        try{
            if (dbType.equals(DbConstant.MYSQL)) {
                Class.forName(DbConstant.mysqlDriver);
            }
            else if (dbType.equals(DbConstant.ORACLE)){
                Class.forName(DbConstant.oracleDriver);
            }else {
                System.out.println("Error db type");
            }
            connection = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static DbUploadCode getTableData(String dbType, String tablename, RelationParam dbParam){

        AvroProducer avroProducer = new AvroProducer();

        String url = dbParam.getDbName();
        String username = dbParam.getUsername();
        String password = dbParam.getPassword();
        Connection connection = getDbConnection(dbType,url,username,password);
        String sql = "Select * from " + tablename;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int col = resultSet.getMetaData().getColumnCount();
            resultSet.first();
            JSONObject jsonObject = new JSONObject();
            for (int i = 1; i<=col; i++) {
                jsonObject.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
            }
            System.out.println("demo: " + JSON.toJSONString(jsonObject));

            Processor dbProcessor = ProcessorFactory.createRelationshipProcessor(JSON.toJSONString(dbParam),JSON.toJSONString(jsonObject));
            GenericRecord firstData =  dbProcessor.process(JSON.toJSONString(jsonObject));
            System.out.println("before process: " + jsonObject);
            System.out.println("after process: "+ firstData);
            String key = "key";
            String topic = dbParam.getTopic();
            try {
//                    avroProducer.sendData(dataMap,topic,key);
            }catch (SerializationException e){
                System.out.println("Serialization Error");
                e.printStackTrace();
            }
            resultSet.first();
            while (resultSet.next()){
                JSONObject dataJson = new JSONObject();
                for (int i = 1; i<=col; i++) {
                    dataJson.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
                GenericRecord dataMap =  dbProcessor.process(JSON.toJSONString(dataJson));
//                System.out.println("before process: " + dataJson);
//                System.out.println("after process: "+ dataMap);
                try {
//                    avroProducer.sendData(dataMap,topic,key);
                }catch (SerializationException e){
                    System.out.println("Serialization Error");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return DbUploadCode.SQL_EXCEPTION;
        }
        return DbUploadCode.UPLOAD_SUCCESS;
    }

    public static String getTableSchema(String dbType, String tablename, String dbParamStr){
        RelationParam dbParam = JSON.parseObject(dbParamStr,RelationParam.class);
        com.b3434.Bean.Parameter parameter = JSON.parseObject(dbParamStr,com.b3434.Bean.Parameter.class);
        String url = dbParam.getDbName();
        String username = dbParam.getUsername();
        String password = dbParam.getPassword();
        Connection connection = getDbConnection(dbType,url,username,password);
        String sql = "Select * from " + tablename +" limit 2";
        PreparedStatement preparedStatement;
        String schema = null;
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int col = resultSet.getMetaData().getColumnCount();
            resultSet.first();
            JSONObject demoObject = new JSONObject();
            for (int i = 1; i<=col; i++) {
                demoObject.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
            }
            System.out.println("demo: " + JSON.toJSONString(demoObject));

            Processor dbProcessor = ProcessorFactory.createRelationshipProcessor(JSON.toJSONString(dbParam),JSON.toJSONString(demoObject));
            schema = ProcessorFactory.createRelationshipSchema(parameter,demoObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schema;
    }

    public static DbUploadCode getAllDbData(String dbType, RelationParam dbParam){
        String url = dbParam.getDbName();
        String username = dbParam.getUsername();
        String password = dbParam.getPassword();
        try {
            List<String> tables = getDbTables(dbType,url,username,password);
            for (String table : tables){
                getTableData(dbType,table,dbParam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return DbUploadCode.SQL_EXCEPTION;
        }
        return DbUploadCode.UPLOAD_SUCCESS;
    }

    public static List<String> getDbTables(String dbType, String url, String username, String password) throws SQLException {
        List tables = new ArrayList();
        Connection connection = getDbConnection(dbType,url,username,password);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(),username,null,new String[]{"TABLE"});
        while (resultSet.next()){
            tables.add(resultSet.getString("TABLE_NAME"));
            System.out.println(resultSet.getString("TABLE_NAME"));
        }
        return tables;
    }

}
