package DBS;

import java.sql.*;
import java.util.ArrayList;

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


  public String[] getAccountInform(int PID) throws SQLException {
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


  public int login(String account,String password) {
    int PID = -1;
    String getPass = "";

    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "select * from account where Account = " + account;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        password = Encryption.encryption(password);
        if (rs.next()) {
          PID = Integer.parseInt(rs.getString("PlayID"));
          getPass = rs.getString("Password");
        }
        else  //no account
          return -3;
        rs.close();
        if (!password.equals(getPass)) //wrong password
          return -2;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(PID != -1)
      System.out.println("login success");
    else
      System.out.println("login fail");
    return PID;
  }


  public int register(String account,String password,String name)  {  //throws SQLException or encryption's exception
    int PID = -1;
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "select * from account where Account = '" + account+"'";


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
          sql = "INSERT INTO `player` (`PlayID`) VALUES ('"+ PID + "')";
          System.out.println(sql);
        }while (statement.executeUpdate(sql) <= 0);

        //insert new account
        sql = "INSERT INTO `account` (`PlayID`, `Account`, `Password`) VALUES ('"+ PID + "', '"+ account + "', '"+Encryption.encryption(password) + "')";
        System.out.println(sql);

        if(statement.executeUpdate(sql) > 0)
          System.out.println("register success");
        else
          System.out.println("register fail");

        rs.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return PID;
  }


  public boolean setName(int PID,String name)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `player` SET `Name` = '"+ name + "' WHERE `player`.`PlayID` = " + PID;
        //insert new account
        System.out.println(sql);

        if(statement.executeUpdate(sql) > 0){
          System.out.println("Set name success");
          return true;
        }

        else {
          System.out.println("Set name fail");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean setTeam(int PID,String TeamID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `player` SET `TeamID` = '"+ TeamID + "' WHERE `player`.`PlayID` = " + PID;
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }



  public ArrayList <Equipment_bag> getEquipment_bag(int PID)throws SQLException {
    ArrayList <Equipment_bag> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "SELECT * FROM `equipment bag` WHERE PlayerID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        int Equipment_ID = Integer.parseInt(rs.getString("Equipment ID"));
        int Rarity = Integer.parseInt(rs.getString("Rarity"));
        int Part = Integer.parseInt(rs.getString("Part"));
        int Level = Integer.parseInt(rs.getString("Level"));
        boolean Equipping = Boolean.parseBoolean(rs.getString("Equipping"));
        int Skill_ID_1 = Integer.parseInt(rs.getString("Skill ID 1"));
        int Skill_ID_2 = Integer.parseInt(rs.getString("Skill ID 2"));
        result.add(new Equipment_bag(PID,Equipment_ID,Rarity,Part,Level,Equipping,Skill_ID_1,Skill_ID_2));
      }
//     System.out.println(result);
      rs.close();
      return result;
    }
    return null;
  }

  public boolean setEquipment_bag(int PlayerID, int Equipment_ID, int Rarity, int Part, int Level, boolean Equipping, int Skill_ID_1, int Skill_ID_2)throws SQLException {
    ArrayList <Equipment_bag> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `equipment bag` (`PlayerID`, `Equipment ID`, `Rarity`, `Part`, `Level`, `Equipping`, `Skill ID 1`, `Skill ID 2`) VALUES" +
              " ('"+PlayerID+"', '"+Equipment_ID+"', '"+Rarity+"', '"+Part+"', '"+Level+"', '"+Equipping+"', '"+Skill_ID_1+"', '"+Skill_ID_2+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }




  public ArrayList <Item_bag> getItem_bag(int PID)throws SQLException {
    ArrayList <Item_bag> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "SELECT * FROM `item bag` WHERE PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        int Item_ID = Integer.parseInt(rs.getString("Item ID"));
        int Rarity = Integer.parseInt(rs.getString("Rarity"));
        int Amount = Integer.parseInt(rs.getString("Amount"));
        result.add(new Item_bag(PID,Item_ID,Rarity,Amount));
      }
      rs.close();
      return result;
    }
    return null;
  }

  public boolean setItem_bag(int PlayerID, int ItemID, int Rarity, int Amount)throws SQLException {
    ArrayList <Equipment_bag> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `item bag` (`PlayerID`, `ItemID`, `Rarity`, `Amount`) VALUES" +
              " ('"+PlayerID+"', '"+ItemID+"', '"+Rarity+"', '"+Amount+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }




  public ArrayList<Integer> getFriend(int PID) throws SQLException {
    ArrayList<Integer> result = new ArrayList<Integer>();
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from friend where PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      String FID=null;
      while (rs.next()){
        FID=rs.getString("Friend ID");
        result.add(Integer.parseInt(FID));
      }
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

  public boolean addFriend(int PID,int FID) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `friend` (`PlayID`, `Friend ID`) VALUES ('"+PID+"', '"+FID+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean delFriend(int PID,int FID) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `friend` WHERE `PlayID` = '"+PID+"' AND 'Friend ID' = '"+FID;
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }



  public Status getStatus(int PID) throws SQLException {
    Status result = null;
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from player status where PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      String FID=null;
      while (rs.next()){
        int HP= Integer.parseInt(rs.getString("HP"));
        int MAX_HP= Integer.parseInt(rs.getString("MAX HP"));
        int MP= Integer.parseInt(rs.getString("MP"));
        int MAX_MP= Integer.parseInt(rs.getString("MAX MP"));
        int STR= Integer.parseInt(rs.getString("STR"));
        int MG= Integer.parseInt(rs.getString("MG"));
        int AGI= Integer.parseInt(rs.getString("AGI"));
        int LUC= Integer.parseInt(rs.getString("LUC"));
        int level= Integer.parseInt(rs.getString("Level"));
        int skill_point= Integer.parseInt(rs.getString("Skill Point"));

        result = new Status(PID, HP, MAX_HP, MP, MAX_MP, STR, MG, AGI,  LUC, level, skill_point);
      }
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

  public boolean setStatus(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int Level, int skill_point)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "INSERT INTO `player status` (`PlayID`, `HP`, `MAX HP`, `MP`, `MAX MP`, `STR`, `MG`, `AGI`, `LUC`, `Level`, `Skill Point`) VALUES ('"+playID+"', '"+HP+"', '"+MAX_HP+"', '"+MP+"', '"+MAX_MP+"', '"+STR+"', '"+MG+"', '"+AGI+"', '"+LUC+"', '"+Level+"', '"+skill_point+"');";
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }




  public boolean addProgress(int PID,int MID) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `progress` (`PlayID`, `Mission ID`, `Progress Status`) VALUES ('"+PID+"', '"+MID+"', '0');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean setProgress(int PID,int MID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `progress` SET `Progress Status` = '"+ 1 + "' WHERE `PlayID` = '"+PID+"' AND `Mission ID` = "+ MID;
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
