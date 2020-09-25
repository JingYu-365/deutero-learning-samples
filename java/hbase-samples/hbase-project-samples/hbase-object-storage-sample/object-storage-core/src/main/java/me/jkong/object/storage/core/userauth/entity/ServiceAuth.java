package me.jkong.object.storage.core.userauth.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ServiceAuth {
  private String bucketName;
  private String targetToken;
  private Date authTime;
}
