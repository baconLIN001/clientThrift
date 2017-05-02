package com.bacon.client.core.runnable;

import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RelationParam;
import com.bacon.client.core.element.DbConstant;
import com.bacon.client.core.avro.DbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bacon on 2017/3/17.
 */
public class DatabaseUploadTask implements Runnable{

    Logger logger = LoggerFactory.getLogger(DatabaseUploadTask.class);

    private Parameter parameter;
    private RelationParam dbParam;

    public DatabaseUploadTask(Parameter parameter){
        this.parameter = parameter;
    }
    public DatabaseUploadTask(RelationParam param){
        this.dbParam = param;
    }

    @Override
    public void run() {
        System.out.println("Here is database upload task...");
//        String url = parameter.getDbUrl();
//        String username = parameter.getDbUserName();
//        String password = parameter.getDbPassword();
        String url = dbParam.getDbName();
        String username = dbParam.getUsername();
        String password = dbParam.getPassword();

        if (dbParam.getIsWholeDb()==0){
            DbHandler.getAllDbData(DbConstant.MYSQL,dbParam);
        }else{
            String tablename = dbParam.getTableName();
            if (dbParam.getIsWholeTable()==1)
            {
                logger.info("url: "+url+" username: "+username+" password: "+password );
                DbHandler.getTableData(DbConstant.MYSQL,tablename,dbParam);
                logger.info("data processing and uploading");
            }
        }
    }
}
