package com.bacon.client.others;

import org.apache.commons.collections.map.HashedMap;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 带key的连接池，当key存在时，即直接返回key对应的对象
 * 当key不存在时，则创建连接
 * 通过加锁确保高并发环境下的线程安全，也确保了connection只创建一次
 * 然而牺牲了性能
 * Created by bacon on 2017/3/20.
 */
public class ExecutingOnceByHashMap {
    private Map<String, Connection> connectionPool = new HashMap<String, Connection>();
    private ReentrantLock lock = new ReentrantLock();

    public Connection getConnection(String key){
        try {
            lock.lock();
            if (connectionPool.containsKey(key)){
                return connectionPool.get(key);
            }
            else {
                //创建connection
                Connection connection = createConnection();
                connectionPool.put(key,connection);
                return connection;
            }
        }finally {
            lock.unlock();
        }
    }

    private Connection createConnection(){
        return null;
    }
}
