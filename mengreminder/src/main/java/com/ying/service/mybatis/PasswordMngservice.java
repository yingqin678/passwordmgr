package com.ying.service.mybatis;

import java.util.List;

public interface PasswordMngservice {

    void addItem(String openId, String appName, String userName, String password);

    List<String> queryAppName(String openId);

    List<String> queryUserName(String openId, String appName);

    String queryPassword(String openId, String appName, String userName);

    void modifyItem();


    void deleteItem();
}
