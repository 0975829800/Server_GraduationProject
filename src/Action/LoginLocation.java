package Action;

import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.*;

public class LoginLocation {
  //when login add Map information to Server.Map
  public static void Login_Location (byte[] data, Status status){
    MapType new_information = new MapType();
    new_information.Longitude = ByteArrayTransform.ToDouble(data,0);
    new_information.Latitude = ByteArrayTransform.ToDouble(data,8);
    new_information.Level = status.Level;
    new_information.TypeID = TypeID.PLAYER;
    new_information.BelongID = status.PlayID;
    new_information.HP = status.HP;
    new_information.MP = status.MP;
    new_information.state = status.State;
    Server.Map.add(new_information);
  }

  public static void Login_Location (double Lo, double La, Status status){
    MapType new_information = new MapType();
    new_information.Longitude = Lo;
    new_information.Latitude = La;
    new_information.Level = status.Level;
    new_information.TypeID = TypeID.PLAYER;
    new_information.BelongID = status.PlayID;
    new_information.HP = status.HP;
    new_information.MP = status.MP;
    new_information.state = status.State;
    Server.Map.add(new_information);
  }
}
