package zpassdkt;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static zpassdkt.Encoding.genPass;

/**
 * Created by joao on 2015/10/17.
 */
public class ZPassModel {
  private String salt;
  private String keyword;
  private String url;

  ZPassModel() {
    salt = "";
    keyword = "";
    url = "";
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  String getPass() {
    try {
      return genPass(this.salt, this.getUrl(), this.keyword);
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }
}
