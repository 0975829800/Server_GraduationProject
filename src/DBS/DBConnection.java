package DBS;

import java.sql.*;

public class DBConnection {
  static Connection con = null;
  Encryption encryption = new Encryption();
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

  public int login(String account,String password) throws Exception {
    int PID = -1;
    String getPass = "";

    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from account where Account = " + account;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);
      password = encryption.Encryption(password);
      if (rs.next()) {
        PID = Integer.parseInt(rs.getString("PlayID"));
        getPass = rs.getString("Password");
      }
      else
        return -3;
      rs.close();
      if (!password.equals(getPass))
        return -2;
    }
    if(PID != -1)
      System.out.println("login success");
    else
      System.out.println("login fail");
    return PID;
  }

  public int register(String account,String password,String name) throws Exception {  //throws SQLException or encryption's exception
    int PID = -1;
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from account where Account = " + account;


      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      if (rs.next()){   //has same account
        return -2;
      }


      /*
      * account.PID -> player.PID
      * insert player data first
      * */
      do {  //insert new player
        PID = (int) (Math.random()*1000000000)+1;
        sql = "INSERT INTO `player` (`PlayID`, `Name`, `TeamID`) VALUES ('"+ PID + "', '"+ name + "', NULL)";
        System.out.println(sql);
      }while (statement.executeUpdate(sql) <= 0);

      //insert new account
      sql = "INSERT INTO `account` (`PlayID`, `Account`, `Password`) VALUES ('"+ PID + "', '"+ account + "', '"+encryption.Encryption(password) + "')";
      System.out.println(sql);

      if(statement.executeUpdate(sql) > 0)
        System.out.println("register success");
      else
        System.out.println("register fail");
      rs.close();
    }
    return PID;
  }
}
