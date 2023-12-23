package com.shinwa.datacollect.service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.CheckRule;
import com.shinwa.datacollect.entity.UserInfo;

import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckRuleService {
//    public static List<CheckRule2> queryAllRules() throws UnknownHostException, SQLException {
//        DataSource ds = AppCache.dataSource;
//        if(ds==null){
//            throw new UnknownHostException();
//        }
//        List<Entity> entities = Db.use(ds).find(Entity.create("check_rule"));
//        List<CheckRule2> list = new ArrayList<>();
//        for(Entity entity:entities){
//            CheckRule2 checkRule2 = new CheckRule2();
//            checkRule2.setId(entity.getInt("id"));
//            checkRule2.setRuleName(entity.getStr("rule_name"));
//            checkRule2.setRuleContent(entity.getStr("rule_content"));
//            list.add(checkRule2);
//        }
//        return list;
//    }
    public static List<CheckRule> queryData(String ruleName) throws UnknownHostException, SQLException {
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
        String sql = "select * from check_rule where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        if(ruleName!=null && !"".equals(ruleName)){
            sb.append(" and rule_name like '%");
            sb.append(ruleName);
            sb.append("%'");
        }
        sb.append(" order by create_time ");
        List<Entity> entities = Db.use(ds).query(sb.toString());

        List<CheckRule> list = new ArrayList<>();
        for(Entity entity:entities){
            CheckRule checkRule2 = new CheckRule();
            checkRule2.setId(entity.getInt("id"));
            checkRule2.setRuleName(entity.getStr("rule_name"));
            checkRule2.setRuleContent(entity.getStr("rule_content"));
            list.add(checkRule2);
        }
        return list;
    }

    public static int saveData(String id,String ruleName,String ruleContent) throws UnknownHostException, SQLException {
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
        LocalDateTime dateTime = LocalDateTime.now();
        UserInfo userInfo = AppCache.loginCache.get("userInfo");
        int i=0;
        //id为空时插入数据，id不为空时修改数据
        if(null==id||"".equals(id)){

            i = Db.use(ds).insert(Entity.create("check_rule")
                    .set("create_time", dateTime)
                    .set("creator", userInfo.getName())
                    .set("update_time", dateTime)
                    .set("updater", userInfo.getName())

                    .set("rule_name", ruleName)
                    .set("rule_content", ruleContent)
            );
        }else {
            i=Db.use(ds).update(Entity.create().set("rule_name",ruleName).set("rule_content",ruleContent).set("update_time",dateTime).set("updater",userInfo.getUsername()),
                    Entity.create("check_rule").set("id",id));
        }
        return i;
    }
}
