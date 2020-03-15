package me.jkong.spring.ioc.overview.domain;

import me.jkong.spring.ioc.overview.annotation.Super;

/**
 * @author JKong
 * @version v1.0
 * @description 超级用户
 * @date 2020-03-15 08:40.
 */
@Super
public class SuperUser extends User{
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}