package zpassdkt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by joao on 2014/06/10.
 */
public class Encoding {
  public static String genPass(String salt, String input, String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    final String res = "%s@%s:%s";
    MessageDigest hash = null;
    hash = MessageDigest.getInstance("SHA-256");

    byte[] theDigest = hash.digest(String.format(res, salt, input, pass).getBytes("UTF-8"));
    // Convert the hash to base62 so that it becomes a shorter string
    String base64 = Base64.getEncoder().encodeToString(theDigest);
    // Destructively convert base64 to base62.
    // This is ok since we don't care about reverting back to the original string.
    return base64.replaceAll("\\+", "Z").replaceAll("/", "z").replaceAll("=", "").substring(0, 16);
  }

}
