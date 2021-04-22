package Action;

import ID.TypeID;
import ServerMainBody.Server;
import Type.*;
import Type.MonsterType;

public class Attack {

  public void Attack(int PID, int MOID, int SkillID,Status status){
    MonsterType monster;
    for(MonsterType m: Server.Monster){
      if(m.MapObjectID == MOID){
        if(!m.Fighting) m.Fighting = true;
        monster = m;
      }
    }


  }
}
