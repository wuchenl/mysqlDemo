package com.letters7.wuchen;

import com.letters7.wuchen.dao.UserDao;
import com.letters7.wuchen.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUserSelect(){
        User user=userDao.selectByUserId("jbt");
        userDao.updateLoginTimeByUserId("apiself");
        User user2=userDao.selectByUserId("jbt");
        Assert.assertNotNull(user);
    }
    @Test
    public void testTransactional(){
        userDao.updateLoginTimeWithError();
    }

}
