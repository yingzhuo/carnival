package com.github.yingzhuo.carnival.nsq.lookup;

import com.github.yingzhuo.carnival.nsq.config.LookupConfig;
import com.github.yingzhuo.carnival.nsq.config.Protocol;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class TestFinderImpl {

    private FinderImpl finder = new FinderImpl();

    @Before
    public void init() {
        finder.setNsqLookupdNodeList(Collections.singletonList(new LookupConfig(Protocol.HTTP,"192.168.99.114", 4161)));
    }

    @Test
    public void testFind() {
        val list = finder.find("test");
        list.forEach(System.out::println);
    }

}
