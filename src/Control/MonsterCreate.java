package Control;

import ID.MonsterID;
import ServerMainBody.Server;
import Type.MapType;
import Type.MonsterType;

//分區域 若該區域怪獸低於50 則生成直到=50
public class MonsterCreate extends Thread{

  public void run() {

    while (true){
      for(int i = 1; i < Server.LocationSum.length; i++){
        while(Server.LocationSum[i] < 20){
          CreateMonster(i);
          Server.LocationSum[i]++;
        }
      }
    }

  }

  //怪物生成數值賦予(需在map上同步
  public void CreateMonster(int Location){
    MonsterType newMonster;
    for(MonsterType m : MonsterID.MonsterInformation){
      if(m.MonsterID == Location){
        newMonster = m;
        newMonster.MapObjectID = MapType.MapIDCounter + 1;
        newMonster.HP = m.MAX_HP;
        newMonster.MP = m.MAX_MP;
        newMonster.State = 0; //common
        newMonster.Location = Location;
        Server.Map.add(new MapType(newMonster));
      }
    }
  }
}
