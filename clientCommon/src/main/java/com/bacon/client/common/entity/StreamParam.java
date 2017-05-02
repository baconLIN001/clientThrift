package com.bacon.client.common.entity;

import java.util.List;

/**
 * Created by Lee on 2017/4/24 0024.
 */
public class StreamParam {

    private String topic;

    private List<String> whiteList;

    private List<String> blackList;

    private int isEncrypted;//1为是，0为否

    private String AESPriKey;

    private List<String> filedNameEncryptedList;

    private int securityLevel;

    public String getTopic() {
        return topic;
    }

    public StreamParam setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public StreamParam setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
        return this;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public StreamParam setBlackList(List<String> blackList) {
        this.blackList = blackList;
        return this;
    }

    public int getIsEncrypted() {
        return isEncrypted;
    }

    public StreamParam setIsEncrypted(int isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    public String getAESPriKey() {
        return AESPriKey;
    }

    public StreamParam setAESPriKey(String AESPriKey) {
        this.AESPriKey = AESPriKey;
        return this;
    }

    public List<String> getFiledNameEncryptedList() {
        return filedNameEncryptedList;
    }

    public StreamParam setFiledNameEncryptedList(List<String> filedNameEncryptedList) {
        this.filedNameEncryptedList = filedNameEncryptedList;
        return this;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public StreamParam setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
        return this;
    }
}
