package Control;

import ServerMainBody.Server;
import Type.MonsterType;

//分區域 若該區域怪獸低於50 則生成直到=50
public class MonsterCreate extends Thread{

  public void run() {
    int[] LocationSum = new int[8];

    for(MonsterType m: Server.Monster){
      LocationSum[m.Location]++;
    }

    for(int i = 1; i < 8; i++){
      if(LocationSum[i] < 50){
        CreateMonster(i);
      }
    }
  }

  //怪物生成數值賦予(需在map上同步
  public void CreateMonster(int Location){

  }
}
