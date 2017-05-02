package com.bacon.client.common.entity;

import java.util.List;

/**
 * Created by Lee on 2017/4/24 0024.
 */
public class OfflineParam {
    private String path;

    private String topic;

    private int isAllString;//1为是，0为否

    private int isSplited;//1为是，0为否

    private String splitSymbol;

    private List<String> filedNameSplitedList;

    private int isRegexed;

    private List<String> filedNameRegexed;

    private List<String> fieldRegex;

    private int isEncrypted;

    private String AESPriKey;

    private List<String> EncryptedList;

    private int securityLevel;

    public String getPath() {
        return path;
    }

    public OfflineParam setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public OfflineParam setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getIsAllString() {
        return isAllString;
    }

    public OfflineParam setIsAllString(int isAllString) {
        this.isAllString = isAllString;
        return this;
    }

    public int getIsSplited() {
        return isSplited;
    }

    public OfflineParam setIsSplited(int isSplited) {
        this.isSplited = isSplited;
        return this;
    }

    public String getSplitSymbol() {
        return splitSymbol;
    }

    public OfflineParam setSplitSymbol(String splitSymbol) {
        this.splitSymbol = splitSymbol;
        return this;
    }

    public List<String> getFiledNameSplitedList() {
        return filedNameSplitedList;
    }

    public OfflineParam setFiledNameSplitedList(List<String> filedNameSplitedList) {
        this.filedNameSplitedList = filedNameSplitedList;
        return this;
    }

    public int getIsRegexed() {
        return isRegexed;
    }

    public OfflineParam setIsRegexed(int isRegexed) {
        this.isRegexed = isRegexed;
        return this;
    }

    public List<String> getFiledNameRegexed() {
        return filedNameRegexed;
    }

    public OfflineParam setFiledNameRegexed(List<String> filedNameRegexed) {
        this.filedNameRegexed = filedNameRegexed;
        return this;
    }

    public List<String> getFieldRegex() {
        return fieldRegex;
    }

    public OfflineParam setFieldRegex(List<String> fieldRegex) {
        this.fieldRegex = fieldRegex;
        return this;
    }

    public int getIsEncrypted() {
        return isEncrypted;
    }

    public OfflineParam setIsEncrypted(int isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    public String getAESPriKey() {
        return AESPriKey;
    }

    public OfflineParam setAESPriKey(String AESPriKey) {
        this.AESPriKey = AESPriKey;
        return this;
    }

    public List<String> getEncryptedList() {
        return EncryptedList;
    }

    public OfflineParam setEncryptedList(List<String> encryptedList) {
        EncryptedList = encryptedList;
        return this;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public OfflineParam setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
        return this;
    }
}