package DBS;

import java.sql.SQLException;

public class test {
  public static void main(String[] args) {
    try {
      DBConnection con = new DBConnection();
//      System.out.println(Arrays.toString(c.getAccountInform("2")));
      try {
        Thread.sleep(7000);
//        System.out.println(con.register("456789","zzzzzz","e04"));
//        System.out.println(con.login("456789","zzzzzz"));
        System.out.println(con.setName(31240985,"????"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    System.out.println("456789zzzzzz");
  }
}
