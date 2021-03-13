package DBS;

import java.sql.*;

public class DBConnection {
  static Connection con = null;
  public DBConnection() throws ClassNotFoundException, SQLException {
    boolean connection = false;

    String url = "jdbc:mysql://220.132.211.121:3306/dungeondatabase";
    String user = "Admin";
    String password = "Zi8xkHTckRmweytR";

    Class.forName("com.mysql.jdbc.Driver");

    con = DriverManager.getConnection(url,user,password);

    if(!con.isClosed()){
      System.out.println("Succeeded connecting to the Database!");
      connection = true;

//      con.close();
    }

  }

  public boolean isConnected() throws SQLException {
    return con != null && !con.isClosed();
  }

  public String[] getAccountInform(String PID) throws SQLException {
    String[] result = null;
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from account where PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      String account=null;
      String password=null;
      while (rs.next()){
        account=rs.getString("Account");
        password=rs.getString("Password");
      }
      result = new String[]{account, password};
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

}
