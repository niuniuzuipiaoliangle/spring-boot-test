package com.servyou.test.demo.test.kafkaProConfig;

import com.servyou.test.demo.test.entity.User;
import com.servyou.test.demo.test.mapper.UserMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 聪聪只爱笨笨
 */
@Component()
@Slf4j
public class FpszjkXtcsManager{

    @Resource(name = "mysqlDataSource")
    private DataSource szjkDataSource;
    @Resource(name = "mysqlSqlSessionFactory")
    private SqlSessionFactory szjkFactory;
    @Autowired
    YjxxMsgProducer<User> yjxxMsgProducer;

    int i =0;
//    假数据
//    @PostConstruct  //启动时执行一次
//    @Async("taskExecutor")
    public void testData() throws FpszjkException {
        try {
            //查询指标结果
            log.info("开始kafkaPro,生产");
            List<User> hbmds = new ArrayList<>();
            User user = new User();
            user.setId(""+(++i));
            user.setUsername(new Date().toString());
            user.setPassword(""+(++i));
            user.setCreateTime(new Date());
            hbmds.add(user);
            saveBatch(hbmds);
        } catch (Throwable e) {
            throw new FpszjkException("离线风险等级加载出错。", e);
        }
    }

//    读数据库
//    @PostConstruct  //启动时执行一次
//    @Async("taskExecutor")
    public void loadYjjb() throws FpszjkException {
        try {
            //查询指标结果
            log.info("开始kafkaPro,生产");
            JdbcTemplate queryTemp = new JdbcTemplate(szjkDataSource);
//            queryTemp.setFetchSize(2);
            String querySql = getSelectUser("");
            ResultSetExtractor extractor = new FpszjkXtcsManager.LoadHbmdsResExtrector("");
            queryTemp.query(querySql, extractor);
        } catch (Throwable e) {
            throw new FpszjkException("离线风险等级加载出错。", e);
        }
    }

    private String getSelectUser(String fxdj) {
        Map<String, Object> params = new HashMap<>();
        params.put("fxdj", fxdj);
        return szjkFactory.getConfiguration().getMappedStatement(UserMapper.class.getName() + ".selectAll")
                .getBoundSql(params).getSql();
    }
    private class LoadHbmdsResExtrector implements ResultSetExtractor {
        @Getter
        private String jobId;

        public LoadHbmdsResExtrector(String jobId) {
            this.jobId = jobId;
        }

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {

            List<User> zbymxs = new ArrayList<>();
            int count = 0;
            System.out.println("查询到推送到kafka数据");
            while (rs.next()) {
                User mx = new User();
                mx.setId(rs.getString("id"));
                mx.setUsername(rs.getString("username"));
                mx.setPassword(rs.getString("password"));
                mx.setCreateTime(rs.getDate("create_time"));
                zbymxs.add(mx);
                count++;
                if (count % 2 == 0) {
                    saveBatch(zbymxs);
                    zbymxs.clear();
                    log.info("加载灰白名单：" + count + "行");
                }
            }

            if (!CollectionUtils.isEmpty(zbymxs)) {
                saveBatch(zbymxs);
                zbymxs.clear();
            }
            log.info("加载灰白名单：" + count + "行");
            return null;
        }

//        private void saveBatch(List<User> hbmds) {
//            //保存数据库
//            try {
//                yjxxMsgProducer.pushKafka(hbmds);
//                log.info("批量保存user:"+hbmds.size());
//            } catch (Exception e) {
//                //没有提交的数据可以回滚
//                log.error("批量保存user:" + e.getMessage(), e);
//            }
//        }
    }
    private void saveBatch(List<User> hbmds) {
        //保存数据库
        try {
            yjxxMsgProducer.pushKafka(hbmds);
            log.info("批量保存user:"+hbmds.size());
        } catch (Exception e) {
            //没有提交的数据可以回滚
            log.error("批量保存user:" + e.getMessage(), e);
        }
    }
}
