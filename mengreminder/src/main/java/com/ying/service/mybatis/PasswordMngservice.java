package com.ying.service.mybatis;

import com.ying.domain.Password;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PasswordMngservice {

    void addItem(Password password);

    List<String> queryAppName(@Param("openId") String openId);

    List<String> queryUserName(@Param("openId") String openId, @Param("appName") String appName);

    String queryPassword(@Param("openId") String openId, @Param("appName") String appName, @Param("userName") String userName);

    void modifyItem();


    void deleteItem();
}
