package Action;

import DBS.DBConnection;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;

public class Login {

  public static int login (OutputStream out, byte[] data) throws SQLException{
    try {
      DBConnection con = new DBConnection();
      byte[] buf = new byte[4];

      String info = new String(data);
      String account = info.substring(0,9).trim();
      String password = info.substring(10,19).trim();

      System.out.println(account +"."+ password);
      int get = con.login(account,password);
      switch (get){
        case -1:
          //connect fail

          break;
        case -2:
          //wrong password

          break;
        case -3:
          //no account

          break;
        default:
          System.out.println(get + " login");
          ByteBuffer.wrap(buf).putInt(get);
          out.write(buf);
          return get;
      }
      ByteBuffer.wrap(buf).putInt(get);
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }
    return -1;
  }
}
