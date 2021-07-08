package Control;


import ServerMainBody.Server;
import Type.MonsterType;

public class MonsterUnFightingMove extends Thread{

  public void run(){
    for(MonsterType m: Server.Monster){

      if(!m.Fighting){

      }
    }
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
