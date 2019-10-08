package thrift.service.impl;

import org.apache.thrift.TException;
import thrift.service.Apple;

public class AppleServiceImpl implements Apple.Iface {

    @Override
    public String appleString(String para) throws TException {

        return "apple print hello " + para;
    }

    @Override
    public int appleAdd(int para) throws TException {
        return para + 10;
    }

    @Override
    public int appleMult(int para1, int para2) throws TException {
        return para1 - para2;
    }

}