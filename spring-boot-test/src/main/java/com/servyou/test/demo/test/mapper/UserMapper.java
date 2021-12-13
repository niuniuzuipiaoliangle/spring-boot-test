package com.servyou.test.demo.test.mapper;

import com.servyou.test.demo.test.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int djxt2Sbh();

    List<User> selectWts();

    void updateTsbzSuc(@Param("nsrsbhs") List<User> nsrsbhs);

    int countWts();

    /**
     * 所有有风险的数据
     */
    List<User> selectAll();

    List<User> selectByFxdj(@Param("fxdj") String fxdj);
}
