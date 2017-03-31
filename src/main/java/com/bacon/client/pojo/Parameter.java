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
    private boolean isAllString;
    private boolean isWholeDb;
    private String dbName;
    private boolean isWholeTable;
    private String tableName;
    private boolean isPart;
    private List<String> whiteList;
    private List<String> blackList;
    private boolean isSplited;
    private String splitSymbol;
    private List<String> filedNameSplitedList;
    private boolean isRegexed;
    private List<String> filedNameRegexed;
    private List<String> fieldRegex;
    private boolean isEncrypted;
    private String AESPriKey;
    private List<String> filedNameEncryptedList;
    private int securityLevel;

    public Parameter() {}

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getTopic()
    {
        return this.topic;
    }

    public void setIsAllString(boolean isAllString)
    {
        this.isAllString = isAllString;
    }

    public boolean getIsAllString()
    {
        return this.isAllString;
    }

    public void setIsWholeDb(boolean isWholeDb)
    {
        this.isWholeDb = isWholeDb;
    }

    public boolean getIsWholeDb()
    {
        return this.isWholeDb;
    }

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public String getDbName()
    {
        return this.dbName;
    }

    public void setIsWholeTable(boolean isWholeTable)
    {
        this.isWholeTable = isWholeTable;
    }

    public boolean getIsWholeTable()
    {
        return this.isWholeTable;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return this.tableName;
    }

    public void setIsPart(boolean isPart)
    {
        this.isPart = isPart;
    }

    public boolean getIsPart()
    {
        return this.isPart;
    }

    public void setWhiteList(List<String> whiteList)
    {
        this.whiteList = whiteList;
    }

    public List<String> getWhiteList()
    {
        return this.whiteList;
    }

    public void setBlackList(List<String> blackList)
    {
        this.blackList = blackList;
    }

    public List<String> getBlackList()
    {
        return this.blackList;
    }

    public void setIsSplited(boolean isSplited)
    {
        this.isSplited = isSplited;
    }

    public boolean getIsSplited()
    {
        return this.isSplited;
    }

    public void setSplitSymbol(String splitSymbol)
    {
        this.splitSymbol = splitSymbol;
    }

    public String getSplitSymbol()
    {
        return this.splitSymbol;
    }

    public void setFiledNameSplitedList(List<String> filedNameSplitedList)
    {
        this.filedNameSplitedList = filedNameSplitedList;
    }

    public List<String> getFiledNameSplitedList()
    {
        return this.filedNameSplitedList;
    }

    public void setIsRegexed(boolean isRegexed)
    {
        this.isRegexed = isRegexed;
    }

    public boolean getIsRegexed()
    {
        return this.isRegexed;
    }

    public void setFiledNameRegexed(List<String> filedNameRegexed)
    {
        this.filedNameRegexed = filedNameRegexed;
    }

    public List<String> getFiledNameRegexed()
    {
        return this.filedNameRegexed;
    }

    public void setFieldRegex(List<String> fieldRegex)
    {
        this.fieldRegex = fieldRegex;
    }

    public List<String> getFieldRegex()
    {
        return this.fieldRegex;
    }

    public void setIsEncrypted(boolean isEncrypted)
    {
        this.isEncrypted = isEncrypted;
    }

    public boolean getIsEncrypted()
    {
        return this.isEncrypted;
    }

    public void setAESPriKey(String AESPriKey)
    {
        this.AESPriKey = AESPriKey;
    }

    public String getAESPriKey()
    {
        return this.AESPriKey;
    }

    public void setFiledNameEncryptedList(List<String> filedNameEncryptedList)
    {
        this.filedNameEncryptedList = filedNameEncryptedList;
    }

    public List<String> getFiledNameEncryptedList()
    {
        return this.filedNameEncryptedList;
    }

    public void setSecurityLevel(int securityLevel)
    {
        this.securityLevel = securityLevel;
    }

    public int getSecurityLevel()
    {
        return this.securityLevel;
    }

}