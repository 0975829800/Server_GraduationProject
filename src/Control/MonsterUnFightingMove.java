package Control;


import ServerMainBody.Server;
import Type.MonsterType;

public class MonsterUnFightingMove extends Thread{

  public void run(){

    try {
      for(MonsterType m: Server.Monster){

        if(!m.Fighting){

        }
      }

      Thread.sleep(5000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
