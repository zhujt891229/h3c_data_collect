package com.shinwa.datacollect.common;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.log.level.Level;
import com.shinwa.datacollect.entity.CheckRule;
import com.shinwa.datacollect.entity.UserInfo;
import com.shinwa.datacollect.service.CheckRuleService;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppCache {
    //public static UserInfo userInfo = new UserInfo();
    public static Cache<String,UserInfo> loginCache = CacheUtil.newTimedCache(24*60*60*1000);

    public static DataSource dataSource;

    static {
        try {
            dataSource = DBSource.getDs();
            DbUtil.setShowSqlGlobal(true,false,true, Level.DEBUG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<CheckRule> checkRuleList;

    static {
        try {
            checkRuleList = new ArrayList<>();
            CheckRule checkRule = new CheckRule();
            checkRule.setRuleName("不校验格式");
            checkRule.setRuleContent("");
            checkRuleList.add(checkRule);
            List<CheckRule> checkRules = CheckRuleService.queryData("");
            checkRuleList.addAll(checkRules);
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
        }
    }


//    public void setUserInfo(UserInfo userInfo){
//        this.userInfo = userInfo;
//    }

//    public void getCache(){
//        CacheBuilder.newBuilder().build(new CacheLoader<String, UserInfo>() {
//
//            @Override
//            public UserInfo load(String key) throws Exception {
//                return userInfo;
//            }
//        });
//    }


}
