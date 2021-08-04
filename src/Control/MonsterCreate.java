package Control;

import ID.LocationID;
import ID.MonsterID;
import ServerMainBody.Server;
import Type.LocationType;
import Type.MapType;
import Type.MonsterType;

//分區域 若該區域怪獸低於50 則生成直到=50
public class MonsterCreate extends Thread{

  public void run() {

    while (true){
      for(LocationType l: LocationID.location){
        while (l.Sum < 5){
          l.Sum++;
          double[] tmp = l.CreateRandomPosition();
          CreateMonster(l.locationID,tmp[0],tmp[1]);
        }
      }

      try {
        sleep(5000);
      }catch (Exception e){
        e.printStackTrace();
      }
    }

  }

  //怪物生成數值賦予(需在map上同步
  public void CreateMonster(int Location,double x,double y){
    MonsterType newMonster = new MonsterType();
    for(MonsterType m : MonsterID.MonsterInformation){
      if(m.MonsterID == Location){
        newMonster.MonsterCopy(m);
        newMonster.State = 0; //common
        newMonster.HP = m.MAX_HP;

        newMonster.Location = Location;
        newMonster.Latitude = x;
        newMonster.Longitude = y;
        MapType New = new MapType(newMonster);
        newMonster.MapObjectID = New.MapObjectID;
        Server.Map.add(New);
        Server.Monster.add(newMonster);
      }
    }
  }
}
