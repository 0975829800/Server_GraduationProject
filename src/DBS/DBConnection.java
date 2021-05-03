package DBS;

import Type.*;

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
        String sql = "select * from account where Account = '" + account+ "'";
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
          sql = "INSERT INTO `player` (`PlayID`, `Name`) VALUES ('"+ PID + "','"+name+"')";
          System.out.println(sql);
        }while (statement.executeUpdate(sql) <= 0);

        //insert new account
        sql = "INSERT INTO `account` (`PlayID`, `Account`, `Password`) VALUES ('"+ PID + "', '"+ account + "', '"+Encryption.encryption(password) + "')";
        System.out.println(sql);

        if(statement.executeUpdate(sql) > 0){
          //initialize status
          if(this.addStatus(PID,100,100,100,100,1,1,1,1,1,0,0,0)){
            System.out.println("register success");
          }
          else{
            System.out.println("register fail");
            sql = "DELETE FROM `player` WHERE `player`.`PlayID` = '"+PID+"'"; //if insert account fail, del player too
            System.out.println(sql);
            statement.executeUpdate(sql);
            PID = -1;
          }
        }
        else{
          System.out.println("register fail");
          sql = "DELETE FROM `player` WHERE `player`.`PlayID` = '"+PID+"'"; //if insert account fail, del player too
          System.out.println(sql);
          statement.executeUpdate(sql);
          PID = -1;
        }


        rs.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return PID;
  }

  public String getName(int PID)  {  //throws SQLException or encryption's exception
    String name = null;
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT `player` WHERE `PlayID` = " + PID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()){   //has same account
          return rs.getString("Name");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
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


  public boolean setTeam(int PID,int TeamID)  {  //throws SQLException or encryption's exception
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



  public ArrayList <EquipmentBoxType> getEquipment_bag(int PID)throws SQLException {
    ArrayList <EquipmentBoxType> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "SELECT * FROM `equipment bag` WHERE PlayerID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        int EquipmentBoxID = Integer.parseInt(rs.getString("EquipmentBoxID"));
        int Equipment_ID = Integer.parseInt(rs.getString("Equipment ID"));
        int Rarity = Integer.parseInt(rs.getString("Rarity"));
        int Part = Integer.parseInt(rs.getString("Part"));
        int Level = Integer.parseInt(rs.getString("Level"));
        int Equipping = Integer.parseInt(rs.getString("Equipping"));
        int Skill_ID_1 = Integer.parseInt(rs.getString("Skill ID 1"));
        int Skill_ID_2 = Integer.parseInt(rs.getString("Skill ID 2"));
        result.add(new EquipmentBoxType(PID,EquipmentBoxID,Equipment_ID,Rarity,Part,Level,Equipping,Skill_ID_1,Skill_ID_2));
      }
//     System.out.println(result);
      rs.close();
      return result;
    }
    return null;
  }

  public boolean addEquipment_bag(int PlayerID,int EquipmentBoxID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `equipment bag` (`PlayerID`,`EquipmentBoxID`, `Equipment ID`, `Rarity`, `Part`, `Level`, `Equipping`, `Skill ID 1`, `Skill ID 2`) VALUES" +
              " ('"+PlayerID+"','"+EquipmentBoxID+"', '"+Equipment_ID+"', '"+Rarity+"', '"+Part+"', '"+Level+"', '"+Equipping+"', '"+Skill_ID_1+"', '"+Skill_ID_2+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean updateEquipment_bag(int PlayerID,int EquipmentBoxID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "UPDATE `equipment bag` SET `Equipment ID` = '"+Equipment_ID+"',`Rarity` = '"+Rarity+"', `Part` = '"+Part+"', `Level` = '"+Level+"', `Equipping` = '"+Equipping+"', `Skill ID 1` = '"+Skill_ID_1+"', `Skill ID 2` = '"+Skill_ID_2+"' WHERE `equipment bag`.`PlayerID` = "+PlayerID+" AND `equipment bag`.`EquipmentBoxID` = "+EquipmentBoxID+";";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean delEquipment_bag(int PlayerID, int EquipmentBoxID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `equipment bag` WHERE `equipment bag`.`PlayerID` = "+PlayerID+" AND `equipment bag`.`EquipmentBoxID` = "+EquipmentBoxID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean delEquipment_bag(int PlayerID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `equipment bag` WHERE `equipment bag`.`PlayerID` = "+PlayerID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public ArrayList <ItemType> getItem_bag(int PID)throws SQLException {
    ArrayList <ItemType> result = new ArrayList<>();
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "SELECT * FROM `item bag` WHERE PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        int Item_ID = Integer.parseInt(rs.getString("ItemID"));
        int ItemBox_ID = Integer.parseInt(rs.getString("ItemboxID"));
        int Rarity = Integer.parseInt(rs.getString("Rarity"));
        int Amount = Integer.parseInt(rs.getString("Amount"));
        result.add(new ItemType(PID,ItemBox_ID,Item_ID,Rarity,Amount));
      }
      rs.close();
      return result;
    }
    return null;
  }

  public boolean addItem_bag(int PlayerID,int ItemboxID, int ItemID, int Rarity, int Amount)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `item bag` (`PlayID`, `ItemID`, `Rarity`, `Amount`,`ItemboxID`) VALUES" +
              " ('"+PlayerID+"', '"+ItemID+"', '"+Rarity+"', '"+Amount+"', '"+ItemboxID+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean updateItem_bag(int PlayerID,int ItemboxID, int ItemID, int Rarity, int Amount)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "UPDATE `item bag` SET `ItemID` = '"+ItemID+"',`Rarity` = '"+Rarity+"', `Amount` = '"+Amount+"' WHERE `item bag`.`PlayID` = "+PlayerID+" AND `item bag`.`ItemboxID` = '"+ItemboxID+"';";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean delItem_bag(int PlayerID, int ItemboxID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `item bag` WHERE `item bag`.`PlayID` = "+PlayerID+" AND `item bag`.`ItemboxID` = "+ItemboxID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }
  public boolean delItem_bag(int PlayerID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `item bag` WHERE `item bag`.`PlayID` = "+PlayerID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public ArrayList<FriendType> getFriend(int PID) throws SQLException {
    ArrayList<FriendType> result = new ArrayList<FriendType>();
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from friend where PlayID = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

      int FID = -1;
      int State = -1;
      while (rs.next()){
        FID= Integer.parseInt(rs.getString("Friend ID"));
        State= Integer.parseInt(rs.getString("State"));
        result.add(new FriendType(PID,FID,State));
      }
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

  public boolean acceptFriend(int PID,int FID)  {
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `friend` SET `State` = '"+ 1 + "' WHERE `PlayID` = '"+PID+"' AND `Friend ID` = "+ FID;
        System.out.println(sql);
        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean addFriend(int PID,int FID,int State) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `friend` (`PlayID`, `Friend ID`, `State`) VALUES ('"+PID+"', '"+FID+"', '"+State+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public boolean delFriend(int PID,int FID) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `friend` WHERE `friend`.`PlayID` = '"+PID+"' AND `friend`.`Friend ID` = '"+FID+"'";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }


  public Status getStatus(int PID) throws SQLException {
    Status result = null;
    if(con != null && !con.isClosed()){
      Statement statement = con.createStatement();
      String sql = "select * from `player status` WHERE `PlayID` = " + PID;
      System.out.println(sql);
      ResultSet rs = statement.executeQuery(sql);

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
        int coin= Integer.parseInt(rs.getString("Coin"));
        int skill_point= Integer.parseInt(rs.getString("Skill Point"));
        int state = Integer.parseInt(rs.getString("State"));
        result = new Status(PID, HP, MAX_HP, MP, MAX_MP, STR, MG, AGI,  LUC, level,coin, skill_point,state);
      }
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

  public boolean addStatus(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int Level,int coin, int skill_point,int state)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "INSERT INTO `player status` (`PlayID`, `HP`, `MAX HP`, `MP`, `MAX MP`, `STR`, `MG`, `AGI`, `LUC`, `Level`, `Coin`, `Skill Point`,`State`) VALUES ('"+playID+"', '"+HP+"', '"+MAX_HP+"', '"+MP+"', '"+MAX_MP+"', '"+STR+"', '"+MG+"', '"+AGI+"', '"+LUC+"', '"+Level+"','"+coin+"', '"+skill_point+"',"+state+");";
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delStatus(int playID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "DELETE FROM `player status` WHERE `player status`.`PlayID` = "+playID;
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateStatus(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int Level, int skill_point,int state,int Coin)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `player status` SET `HP` = '"+HP+"', `MAX HP` = '"+MAX_HP+"', `MP` = '"+MP+"', `MAX MP` = '"+MAX_MP+"', `STR` = '"+STR+"'," +
                " `MG` = '"+MG+"', `AGI` = '"+AGI+"', `LUC` = '"+LUC+"', `Level` = '"+Level+"', `Skill Point` = '"+skill_point+"', `State` = '"+state+"', `Coin` = '"+Coin+"' WHERE `player status`.`PlayID` = "+playID+";";
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

  public ArrayList<Progress> getProgress(int PID)  {  //throws SQLException or encryption's exception
    ArrayList<Progress> progress = new ArrayList<>();
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "select * from `progress` WHERE `PlayID` = " + PID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
          int PlayID = Integer.parseInt(rs.getString("PlayID"));
          int MID = Integer.parseInt(rs.getString("Mission ID"));
          int status = Integer.parseInt(rs.getString("Progress Status"));
          if (status == 0)
            progress.add(new Progress(PlayID,MID,false));
          else
            progress.add(new Progress(PlayID,MID,true));
        }
        return progress;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
