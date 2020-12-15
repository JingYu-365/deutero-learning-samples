package com.github.laba.mockito.matcher;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * test for wildcard matcher
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class WildCardMatcherTest {

    @Mock
    private SimpleService simpleService;

    @Test
    public void testWildCardForReturnInt() {
        when(simpleService.intMethod(anyInt(), anyString(), anyCollection(), isA(Serializable.class))).thenReturn(100);
        int result = simpleService.intMethod(1, "zhang san", Collections.EMPTY_LIST, "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.intMethod(2, "li si", Collections.emptySet(), "Mockito for Java");
        assertThat(result, equalTo(100));
    }

    @Test
    public void testWildCardForReturnIntWithSpecialParam() {
        when(simpleService.intMethod(anyInt(), anyString(), anyCollection(), isA(Serializable.class))).thenReturn(-1);
        // 个性化的设置，需要在大范围后面设置，以免被覆盖
        when(simpleService.intMethod(anyInt(), eq("zhang san"), anyCollection(), isA(Serializable.class))).thenReturn(100);
        when(simpleService.intMethod(anyInt(), eq("li si"), anyCollection(), isA(Serializable.class))).thenReturn(200);

        int result = simpleService.intMethod(1, "zhang san", Collections.EMPTY_LIST, "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.intMethod(2, "li si", Collections.emptySet(), "Mockito for Java");
        assertThat(result, equalTo(200));

        result = simpleService.intMethod(2, "zhao liu", Collections.emptySet(), "Mockito for Java");
        assertThat(result, equalTo(-1));
    }

    @Test
    public void testWildcardForReturnNothing() {
        List emptyList = Collections.EMPTY_LIST;
        doNothing().when(simpleService).voidMethod(anyInt(), anyString(), anyCollection(), isA(Serializable.class));
        simpleService.voidMethod(1, "zhang san", emptyList, "Mockito");
        verify(simpleService, times(1)).voidMethod(1, "zhang san", emptyList, "Mockito");
        verify(simpleService, times(1)).voidMethod(anyInt(), anyString(), emptyList, "Mockito");
    }

    @After
    public void destroy() {
        reset(simpleService);
    }
}
