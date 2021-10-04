package Action;

import ID.MonsterID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.MapType;
import Type.MonsterType;
import Type.PlayerInformation;

public class CreateMonster {
  public static void create(PlayerInformation p, byte[] data){
    int ID = ByteArrayTransform.ToInt(data,0);
    double lat = ByteArrayTransform.ToDouble(data,4);
    double lon = ByteArrayTransform.ToDouble(data,12);
    MonsterType newMonster = new MonsterType();
    for(MonsterType m : MonsterID.MonsterInformation){
      if(m.MonsterID == ID){
        newMonster.MonsterCopy(m);
        newMonster.State = 0; //common
        newMonster.HP = m.MAX_HP;

        newMonster.Latitude = lat;
        newMonster.Longitude = lon;
        MapType New = new MapType(newMonster);
        newMonster.MapObjectID = New.MapObjectID;
        New.setName(m.MonsterName);
        Server.Map.add(New);
        Server.Monster.add(newMonster);
      }
    }
  }
}
