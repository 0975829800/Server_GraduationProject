package DBS;

import java.sql.SQLException;
import java.util.Arrays;

public class test {
  public static void main(String[] args) {
    try {
      DBConnection c = new DBConnection();
      System.out.println(Arrays.toString(c.getAccountInform("2")));
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
}
