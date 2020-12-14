package com.github.laba.mockito.howtorun;

import com.github.laba.mockito.common.UserDao;
import com.github.laba.mockito.common.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * mockito run with runner
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoRunWithRunnerTest {

    @Test
    public void testMockitoRunWithRunner() {
        UserDao userDao = mock(UserDao.class);
        when(userDao.queryUserInfoById("123")).thenReturn(new UserInfo("123", "laba", 18, "mela"));
        UserInfo userInfo = userDao.queryUserInfoById("123");
        assertNotNull(userInfo);
        assertThat(userInfo.toString(), equalTo("UserInfo{id='123', name='laba', age=18, gender='mela'}"));
    }
}
