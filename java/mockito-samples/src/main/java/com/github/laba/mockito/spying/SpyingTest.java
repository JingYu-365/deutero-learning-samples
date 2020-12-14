package com.github.laba.mockito.spying;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * TODO
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class SpyingTest {

    @Test
    public void testSpy(){
        ArrayList<String> realList = new ArrayList<>();
        ArrayList<String> spyList = spy(realList);
        spyList.add("Mockito");
        spyList.add("PowerMock");

        // 通过 spy 操作 mock 出来的对象，会调用真实的方法
        assertThat(spyList.get(0), equalTo("Mockito"));
        assertThat(spyList.get(1), equalTo("PowerMock"));
        assertThat(spyList.isEmpty(), equalTo(false));

        // 通过 stubbing mock 出来的数据，不会调用真实的方法
        when(spyList.isEmpty()).thenReturn(true);
        when(spyList.size()).thenReturn(0);

        assertThat(spyList.isEmpty(),equalTo(true));
        assertThat(spyList.size(),equalTo(0));
    }
}
