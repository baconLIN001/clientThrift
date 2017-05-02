package com.bacon.client.common.entity;

import java.util.List;

/**
 * Created by Lee on 2017/4/24 0024.
 */
public class RelationParam {

    private String jdbc;

    private String username;

    private String password;

    private String topic;

    private int isWholeDb;

    private String dbName;

    private int isWholeTable;

    private String tableName;

    private int isPart;

    private List<String> whiteList;

    private List<String> blackList;

    private int isEncrypted;

    private String AESPriKey;

    private List<String> EncryptedList;

    private int securityLevel;

    public String getTopic() {
        return topic;
    }

    public RelationParam setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getIsWholeDb() {
        return isWholeDb;
    }

    public RelationParam setIsWholeDb(int isWholeDb) {
        this.isWholeDb = isWholeDb;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public RelationParam setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public int getIsWholeTable() {
        return isWholeTable;
    }

    public RelationParam setIsWholeTable(int isWholeTable) {
        this.isWholeTable = isWholeTable;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public RelationParam setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public RelationParam setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
        return this;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public RelationParam setBlackList(List<String> blackList) {
        this.blackList = blackList;
        return this;
    }

    public int getIsEncrypted() {
        return isEncrypted;
    }

    public RelationParam setIsEncrypted(int isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    public String getAESPriKey() {
        return AESPriKey;
    }

    public RelationParam setAESPriKey(String AESPriKey) {
        this.AESPriKey = AESPriKey;
        return this;
    }

    public String getJdbc() {
        return jdbc;
    }

    public RelationParam setJdbc(String jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RelationParam setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RelationParam setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getIsPart() {
        return isPart;
    }

    public RelationParam setIsPart(int isPart) {
        this.isPart = isPart;
        return this;
    }

    public List<String> getEncryptedList() {
        return EncryptedList;
    }

    public RelationParam setEncryptedList(List<String> encryptedList) {
        EncryptedList = encryptedList;
        return this;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public RelationParam setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
        return this;
    }
}
