namespace java com.bacon.client

enum RequestType{
    OFFLINE_DATA_UPLOAD,    //离线数据
    DATABASE_DATA_UPLOAD,   //关系型数据库数据
    FILELIST_VIEW,              //文件浏览请求
    OFFline_File_PREIVEW,  //离线文件预览
    GET_LIVE_CLIENTS,       //获取
}

struct WebRequest{
    1:required RequestType requestType;     //请求的数据类型, 必选
    2:required i32 taskId;                  //请求的task的ID
    3:required string parameter;                 //功能参数的json
}

exception RequestException{
    1:required i32 code;
    2:optional string reason;
}

service ClientService{
    string receive(1:WebRequest webRequest) throws (1:RequestException ex);
}