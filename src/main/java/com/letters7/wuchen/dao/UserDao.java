package com.letters7.wuchen.dao;

import com.letters7.wuchen.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于
 */
public interface UserDao {
    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    User selectByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID修改用户最后登录时间
     * @param userId
     * @return
     */
    int updateLoginTimeByUserId(@Param("userId") String userId);

    /**
     * 测试批量更新时的事务处理
     * @return
     */
    int updateLoginTimeWithError();
}
