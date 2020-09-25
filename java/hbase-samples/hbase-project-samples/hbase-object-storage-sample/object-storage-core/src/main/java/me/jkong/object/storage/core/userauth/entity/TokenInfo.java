package me.jkong.object.storage.core.userauth.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import me.jkong.object.storage.core.utils.RandomUtil;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TokenInfo {

  private String token;
  private int expireTime;
  private Date refreshTime;
  private Date createTime;
  private boolean active;
  private String creator;

  public TokenInfo() {

  }

  public TokenInfo(String creator) {
    this.token = RandomUtil.getUUID();
    this.expireTime = 7;
    Date date = new Date();
    this.refreshTime = date;
    this.createTime = date;
    this.active = true;
    this.creator = creator;
  }

}
