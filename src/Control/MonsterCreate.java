package Control;

import ID.MonsterID;
import ServerMainBody.Server;
import Type.MapType;
import Type.MonsterType;

//分區域 若該區域怪獸低於50 則生成直到=50
public class MonsterCreate extends Thread{

  public void run() {

    for(MonsterType m: Server.Monster){
      Server.LocationSum[m.Location]++;
    }

    for(int i = 1; i < 8; i++){
      while(Server.LocationSum[i] < 50){
        CreateMonster(i);
        Server.LocationSum[i]++;
      }
    }
  }

  //怪物生成數值賦予(需在map上同步
  public void CreateMonster(int Location){
    int rand = (int)Math.random()*MonsterID.length;
    MonsterType newMonster = null;

    for(MonsterType m : MonsterID.MonsterInformation){
      if(m.MonsterID == rand){
        newMonster = m;
        m.HP = m.MAX_HP;
        m.MP = m.MAX_MP;
        m.State = 0; //common
        m.Location = Location;
        Server.Monster.add(newMonster);
      }
    }

    Server.Map.add(new MapType(newMonster));
  }
}
