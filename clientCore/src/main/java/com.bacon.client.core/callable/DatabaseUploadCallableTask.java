package com.bacon.client.core.callable;

import com.bacon.client.common.entity.RelationParam;
import com.bacon.client.core.avro.DbHandler;
import com.bacon.client.core.base.DbUploadCode;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.core.element.DbConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by bacon on 2017/4/27.
 */
public class DatabaseUploadCallableTask implements Callable<CompleteReturnBack> {
    Logger logger = LoggerFactory.getLogger(DatabaseUploadCallableTask.class);
    private RelationParam relationParam;

    public DatabaseUploadCallableTask(RelationParam relationParam) {
        this.relationParam = relationParam;
    }

    @Override
    public CompleteReturnBack call() throws Exception {
        System.out.println("Here is database upload task...");
        String url = relationParam.getDbName();
        String username = relationParam.getUsername();
        String password = relationParam.getPassword();
        String dbType =  relationParam.getJdbc();

        logger.info("url: "+url+" username: "+username+" password: "+password );
        logger.info("data processing and uploading");
        CompleteReturnBack completeReturnBack = new CompleteReturnBack();
        DbUploadCode dbUploadCode;
        //上传整个库
        if (relationParam.getIsWholeDb()==0){
            dbUploadCode = DbHandler.getAllDbData(dbType, relationParam);
        }else {
            //上传单个表
            String tableName = relationParam.getTableName();
            dbUploadCode = DbHandler.getTableData(dbType, tableName, relationParam);
        }
        completeReturnBack.setDbUploadCode(dbUploadCode);
        completeReturnBack.setReturnInfo("info");
        return completeReturnBack;
    }
}
