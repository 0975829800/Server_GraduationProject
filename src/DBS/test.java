package DBS;

import java.sql.SQLException;

public class test {
  public static void main(String[] args) {
    try {
      DBConnection con = new DBConnection();
//      System.out.println(Arrays.toString(c.getAccountInform("2")));
      try {
        System.out.println(con.register("456789","zzzzzzz","e04"));
        System.out.println(con.login("456789","zzzzzzz"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
}
