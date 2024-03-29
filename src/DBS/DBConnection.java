package DBS;

import Type.*;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
  static Connection con = null;
  Encryption encryption = new Encryption();
  public static void  init(){
    try {
      boolean connection = false;

      String url = "jdbc:mysql://220.132.211.121:3306/dungeondatabase?useUnicode=true&characterEncoding=Big5";
      String user = "Admin";
      String password = "Zi8xkHTckRmweytR";

      Class.forName("com.mysql.jdbc.Driver");

      con = DriverManager.getConnection(url,user,password);

      if(!con.isClosed()){
        System.out.println("Succeeded connecting to the Database!");
        connection = true;

//      con.close();
      }
    }catch (Exception e){
      e.printStackTrace();
    }

  }

  public static boolean isConnected() throws SQLException {
    return con != null && !con.isClosed();
  }


  public static String[] getAccountInform(int PID) throws SQLException {
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


  public static int login(String account,String password) {
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


  public static int register(String account,String password,String name)  {  //throws SQLException or encryption's exception
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
          if(addStatus(PID,100,100,100,100,10,10,10,10,10,1000,0,0,0)){
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

  public static String getName(int PID)  {  //throws SQLException or encryption's exception
    String name = null;
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM `player` WHERE `PlayID` = " + PID;
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

  public static boolean hasAccount(int PID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM `player` WHERE `PlayID` = " + PID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {   //has same account
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean setName(int PID,String name)  {  //throws SQLException or encryption's exception
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

  public static String[] getTeam(int PID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM `player` WHERE `PlayID` = '" + PID+"'";
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()){
          sql = "SELECT * FROM `team` WHERE `TeamID` = '" + rs.getString("TeamID")+"'";
          rs = statement.executeQuery(sql);
          if(rs.next()){
            return new String[] {rs.getString("TeamID"),rs.getString("Name")};
          }
          return null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static int getTeamNum(int TID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT COUNT(TeamID) FROM player WHERE TeamID = "+TID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()){   //has same account
          return Integer.parseInt(rs.getString("COUNT(TeamID)"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  public static ArrayList<TeammateType> getTeamMem(int TID)  {  //throws SQLException or encryption's exception
    ArrayList<TeammateType> member = new ArrayList<>();
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM player WHERE TeamID = "+TID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
           member.add(new TeammateType(Integer.parseInt(rs.getString("PlayID"))));
        }
        return member;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return member;
  }

  public static boolean hasTeam(int TID)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM `team` WHERE `TeamID` = " + TID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {   //has same account
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean createTeam(int PID,String Name)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "INSERT INTO `team` (`TeamID`, `Name`) VALUES ('"+PID+"', '"+Name+"');";
        System.out.println(sql);
        if(statement.executeUpdate(sql) > 0){
          sql = "UPDATE `player` SET `TeamID` = '"+ PID + "' WHERE `player`.`PlayID` = " + PID;
          System.out.println(sql);
          return statement.executeUpdate(sql) > 0;
        }
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean delTeam(int TeamID)  {
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "DELETE FROM `team` WHERE `team`.`TeamID` = "+TeamID+" ";
        System.out.println(sql);
        if (statement.executeUpdate(sql) > 0){
          sql = "UPDATE `player` SET `TeamID`=NULL WHERE`player`.`TeamID` = "+TeamID+"";
          System.out.println(sql);
          return statement.executeUpdate(sql) > 0;
        }
        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean setTeam(int PID,int TeamID)  {  //TeamID = 0 to quit
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `player` SET `TeamID` = '"+ TeamID + "' WHERE `player`.`PlayID` = " + PID;
        if (TeamID == 0){
          sql = "UPDATE `player` SET `TeamID` = NULL WHERE `player`.`PlayID` = " + PID;
        }
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean replaceTeamLeader(int TeamID) throws SQLException {
    try {
      if (con != null && !con.isClosed()) {
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM `player` WHERE `TeamID` = '" + TeamID + "'";
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
          String newID = rs.getString("PlayID"); //get first result as new TeamID
          sql = "UPDATE `player` SET `TeamID`='"+newID+"' WHERE`player`.`TeamID` = '" + TeamID + "'";
          if(statement.executeUpdate(sql) > 0){
            sql = "UPDATE `team` SET `TeamID`='"+newID+"' WHERE`team`.`TeamID` = '" + TeamID + "'";
            return statement.executeUpdate(sql) > 0;
          }
        }
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }




  public static ArrayList <EquipmentBoxType> getEquipment_bag(int PID)throws SQLException {
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

  public static boolean addEquipment_bag(int PlayerID,int EquipmentBoxID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `equipment bag` (`PlayerID`,`EquipmentBoxID`, `Equipment ID`, `Rarity`, `Part`, `Level`, `Equipping`, `Skill ID 1`, `Skill ID 2`) VALUES" +
              " ('"+PlayerID+"','"+EquipmentBoxID+"', '"+Equipment_ID+"', '"+Rarity+"', '"+Part+"', '"+Level+"', '"+Equipping+"', '"+Skill_ID_1+"', '"+Skill_ID_2+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean updateEquipment_bag(int PlayerID,int EquipmentBoxID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "UPDATE `equipment bag` SET `Equipment ID` = '"+Equipment_ID+"',`Rarity` = '"+Rarity+"', `Part` = '"+Part+"', `Level` = '"+Level+"', `Equipping` = '"+Equipping+"', `Skill ID 1` = '"+Skill_ID_1+"', `Skill ID 2` = '"+Skill_ID_2+"' WHERE `equipment bag`.`PlayerID` = "+PlayerID+" AND `equipment bag`.`EquipmentBoxID` = "+EquipmentBoxID+";";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean delEquipment_bag(int PlayerID, int EquipmentBoxID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `equipment bag` WHERE `equipment bag`.`PlayerID` = "+PlayerID+" AND `equipment bag`.`EquipmentBoxID` = "+EquipmentBoxID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean delEquipment_bag(int PlayerID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `equipment bag` WHERE `equipment bag`.`PlayerID` = "+PlayerID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static ArrayList <ItemType> getItem_bag(int PID)throws SQLException {
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

  public static boolean addItem_bag(int PlayerID,int ItemboxID, int ItemID, int Rarity, int Amount)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `item bag` (`PlayID`, `ItemID`, `Rarity`, `Amount`,`ItemboxID`) VALUES" +
              " ('"+PlayerID+"', '"+ItemID+"', '"+Rarity+"', '"+Amount+"', '"+ItemboxID+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean updateItem_bag(int PlayerID,int ItemboxID, int ItemID, int Rarity, int Amount)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "UPDATE `item bag` SET `ItemID` = '"+ItemID+"',`Rarity` = '"+Rarity+"', `Amount` = '"+Amount+"' WHERE `item bag`.`PlayID` = "+PlayerID+" AND `item bag`.`ItemboxID` = '"+ItemboxID+"';";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean delItem_bag(int PlayerID, int ItemboxID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `item bag` WHERE `item bag`.`PlayID` = "+PlayerID+" AND `item bag`.`ItemboxID` = "+ItemboxID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }
  public static boolean delItem_bag(int PlayerID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `item bag` WHERE `item bag`.`PlayID` = "+PlayerID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static ArrayList<FriendType> getFriend(int PID) throws SQLException {
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

  public static boolean acceptFriend(int PID,int FID)  {
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

  public static boolean addFriend(int PID,int FID,int State) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `friend` (`PlayID`, `Friend ID`, `State`) VALUES ('"+PID+"', '"+FID+"', '"+State+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean delFriend(int PID,int FID) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `friend` WHERE `friend`.`PlayID` = '"+PID+"' AND `friend`.`Friend ID` = '"+FID+"'";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }


  public static Status getStatus(int PID) throws SQLException {
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
        int EXP = Integer.parseInt(rs.getString("EXP"));
        result = new Status(PID, HP, MAX_HP, MP, MAX_MP, STR, MG, AGI,  LUC, level,coin, skill_point,state,EXP);
      }
//      System.out.println(result);

      rs.close();
      return result;
    }
    return null;
  }

  public static boolean addStatus(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int Level,int coin, int skill_point,int state,int EXP)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "INSERT INTO `player status` (`PlayID`, `HP`, `MAX HP`, `MP`, `MAX MP`, `STR`, `MG`, `AGI`, `LUC`, `Level`, `Coin`, `Skill Point`,`State`,`EXP`) VALUES ('"+playID+"', '"+HP+"', '"+MAX_HP+"', '"+MP+"', '"+MAX_MP+"', '"+STR+"', '"+MG+"', '"+AGI+"', '"+LUC+"', '"+Level+"','"+coin+"', '"+skill_point+"','"+state+"','"+EXP+"')";
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean delStatus(int playID)  {  //throws SQLException or encryption's exception
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

  public static boolean updateStatus(int playID, int HP, int MAX_HP, int MP, int MAX_MP, int STR, int MG, int AGI, int LUC, int Level, int skill_point,int state,int Coin,int EXP)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `player status` SET `HP` = '"+HP+"', `MAX HP` = '"+MAX_HP+"', `MP` = '"+MP+"', `MAX MP` = '"+MAX_MP+"', `STR` = '"+STR+"'," +
                " `MG` = '"+MG+"', `AGI` = '"+AGI+"', `LUC` = '"+LUC+"', `Level` = '"+Level+"', `Skill Point` = '"+skill_point+"', `State` = '"+state+"', `Coin` = '"+Coin+"', `EXP` = '"+EXP+"' WHERE `player status`.`PlayID` = "+playID+";";
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  public static boolean addProgress(Progress progress) throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "INSERT INTO `progress` (`PlayID`, `ProgressID`, `State`, `Information1`, `Information2`) VALUES ('"
              +progress.PlayerID+"', '"+progress.missionID+"', '"+progress.state+"', '"+progress.information1+"','"+progress.information2+"');";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean delProgresses(int PlayerID)throws SQLException {
    if (con != null && !con.isClosed()) {
      Statement statement = con.createStatement();
      String sql = "DELETE FROM `progress` WHERE `progress`.`PlayID` = "+PlayerID+"";
      System.out.println(sql);
      return statement.executeUpdate(sql) > 0;
    }
    return false;
  }

  public static boolean setProgress(int PID,int ProgressID,int State,int Information1,int Information2)  {  //throws SQLException or encryption's exception
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "UPDATE `progress` SET `State`="+State+",`Information1`="+Information1+",`Information2`="+Information2+" WHERE `PlayID`="+ PID +" AND `ProgressID`="+ProgressID+"";
        //insert new account
        System.out.println(sql);

        return statement.executeUpdate(sql) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static ArrayList<Progress> getProgress(int PID)  {  //throws SQLException or encryption's exception
    ArrayList<Progress> progress = new ArrayList<>();
    try {
      if(con != null && !con.isClosed()){
        Statement statement = con.createStatement();
        String sql = "select * from `progress` WHERE `PlayID` = " + PID;
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
          int PlayID = Integer.parseInt(rs.getString("PlayID"));
          int ProgressID = Integer.parseInt(rs.getString("ProgressID"));
          int State = Integer.parseInt(rs.getString("State"));
          int Information1 = Integer.parseInt(rs.getString("Information1"));
          int Information2 = Integer.parseInt(rs.getString("Information2"));
          progress.add(new Progress(PlayID,ProgressID,State,Information1,Information2));
        }
        return progress;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
