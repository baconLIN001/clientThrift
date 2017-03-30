/**
  * Copyright 2017 bejson.com 
  */
package com.bacon.client.pojo;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2017-03-22 17:8:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Parameter {

    private String path;
    private String topic;
    @JsonProperty("isAllString")
    private boolean isallstring;
    @JsonProperty("originStr")
    private String originstr;
    @JsonProperty("isWholeDb")
    private boolean iswholedb;
    @JsonProperty("dbName")
    private String dbname;
    @JsonProperty("isWholeTable")
    private boolean iswholetable;
    @JsonProperty("tableName")
    private String tablename;
    @JsonProperty("isPart")
    private boolean ispart;
    @JsonProperty("whiteList")
    private List<String> whitelist;
    @JsonProperty("blackList")
    private List<String> blacklist;
    @JsonProperty("isSplited")
    private boolean issplited;
    @JsonProperty("splitSymbol")
    private String splitsymbol;
    @JsonProperty("filedNameSplitedList")
    private List<String> filednamesplitedlist;
    @JsonProperty("isRegexed")
    private boolean isregexed;
    @JsonProperty("filedName")
    private List<String> filedname;
    @JsonProperty("fieldRegex")
    private List<String> fieldregex;
    @JsonProperty("isEncrypted")
    private boolean isencrypted;
    @JsonProperty("AESPriKey")
    private String aesprikey;
    @JsonProperty("filedNameEncryptedList")
    private List<String> filednameencryptedlist;
    @JsonProperty("securityLevel")
    private int securitylevel;
    public void setPath(String path) {
         this.path = path;
     }
     public String getPath() {
         return path;
     }

    public void setTopic(String topic) {
         this.topic = topic;
     }
     public String getTopic() {
         return topic;
     }

    public void setIsallstring(boolean isallstring) {
         this.isallstring = isallstring;
     }
     public boolean getIsallstring() {
         return isallstring;
     }

    public void setOriginstr(String originstr) {
         this.originstr = originstr;
     }
     public String getOriginstr() {
         return originstr;
     }

    public void setIswholedb(boolean iswholedb) {
         this.iswholedb = iswholedb;
     }
     public boolean getIswholedb() {
         return iswholedb;
     }

    public void setDbname(String dbname) {
         this.dbname = dbname;
     }
     public String getDbname() {
         return dbname;
     }

    public void setIswholetable(boolean iswholetable) {
         this.iswholetable = iswholetable;
     }
     public boolean getIswholetable() {
         return iswholetable;
     }

    public void setTablename(String tablename) {
         this.tablename = tablename;
     }
     public String getTablename() {
         return tablename;
     }

    public void setIspart(boolean ispart) {
         this.ispart = ispart;
     }
     public boolean getIspart() {
         return ispart;
     }

    public void setWhitelist(List<String> whitelist) {
         this.whitelist = whitelist;
     }
     public List<String> getWhitelist() {
         return whitelist;
     }

    public void setBlacklist(List<String> blacklist) {
         this.blacklist = blacklist;
     }
     public List<String> getBlacklist() {
         return blacklist;
     }

    public void setIssplited(boolean issplited) {
         this.issplited = issplited;
     }
     public boolean getIssplited() {
         return issplited;
     }

    public void setSplitsymbol(String splitsymbol) {
         this.splitsymbol = splitsymbol;
     }
     public String getSplitsymbol() {
         return splitsymbol;
     }

    public void setFilednamesplitedlist(List<String> filednamesplitedlist) {
         this.filednamesplitedlist = filednamesplitedlist;
     }
     public List<String> getFilednamesplitedlist() {
         return filednamesplitedlist;
     }

    public void setIsregexed(boolean isregexed) {
         this.isregexed = isregexed;
     }
     public boolean getIsregexed() {
         return isregexed;
     }

    public void setFiledname(List<String> filedname) {
         this.filedname = filedname;
     }
     public List<String> getFiledname() {
         return filedname;
     }

    public void setFieldregex(List<String> fieldregex) {
         this.fieldregex = fieldregex;
     }
     public List<String> getFieldregex() {
         return fieldregex;
     }

    public void setIsencrypted(boolean isencrypted) {
         this.isencrypted = isencrypted;
     }
     public boolean getIsencrypted() {
         return isencrypted;
     }

    public void setAesprikey(String aesprikey) {
         this.aesprikey = aesprikey;
     }
     public String getAesprikey() {
         return aesprikey;
     }

    public void setFilednameencryptedlist(List<String> filednameencryptedlist) {
         this.filednameencryptedlist = filednameencryptedlist;
     }
     public List<String> getFilednameencryptedlist() {
         return filednameencryptedlist;
     }

    public void setSecuritylevel(int securitylevel) {
         this.securitylevel = securitylevel;
     }
     public int getSecuritylevel() {
         return securitylevel;
     }

}