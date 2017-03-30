package com.bacon.client.others;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * 改用ConcurrentHash的情况下，几乎可以避免加锁的操作，性能大大提高
 * 但是在高并发的情况下有可能出现Connection被创建多次的现象
 * 这时最需要解决的问题就是当key不存在时，创建Connection的动作能放在connectionPool之后执行
 * 这正是FutureTask发挥作用的时机
 * Created by bacon on 2017/3/20.
 */
public class ExecutingOnceByConcurrentHashMap {
    private ConcurrentHashMap<String,FutureTask<Connection>>concurrentHashMapPool = new ConcurrentHashMap<>();

    public Connection getConnection(String key)throws Exception{
        FutureTask<Connection>connectionFutureTask = concurrentHashMapPool.get(key);
        if (connectionFutureTask!=null){
            return connectionFutureTask.get();
        }else {
            Callable<Connection>callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return createConnection();
                }
            };
            FutureTask<Connection>newTask = new FutureTask<Connection>(callable);
            connectionFutureTask=concurrentHashMapPool.putIfAbsent(key,newTask);
            if (connectionFutureTask==null)
            {
                connectionFutureTask = newTask;
                connectionFutureTask.run();
            }
            return connectionFutureTask.get();
        }
    }

    private Connection createConnection(){
        return null;
    }
}
