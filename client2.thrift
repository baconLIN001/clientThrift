namespace java com.bacon.client2

enum RequestType{
    OFFLINE_DATA_UPLOAD,    //离线数据
    DATABASE_DATA_UPLOAD,   //关系型数据库数据
    FILELIST_VIEW,              //文件浏览请求
    OFFline_File_PREIVEW,  //离线文件预览
    GET_LIVE_CLIENTS,       //获取
}

struct WebRequest{
    1:required RequestType requestType;     //请求的数据类型, 必选
    2:required string parameter;                 //功能参数的json
    //3:required i32 securityLevel;           //安全等级，必选
    //4:optional string originStr;            //若用户选择上传整个string，使用默认的field OriginStr
    //5:optional string regex;
    //6:optional list<string> filedNameSplited;
    //7:optional list<string> filedNameAES;
    //8:optional string AESPriKey;
}

exception RequestException{
    1:required i32 code;
    2:optional string reason;
}

service ClientService{
    string receive(1:WebRequest webRequest) throws (1:RequestException ex);
}