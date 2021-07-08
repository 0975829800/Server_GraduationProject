package Control;


import Type.MonsterType;

public class MonsterFighting extends Thread{
  MonsterType monster;
  double sleepTime;
  boolean exit = false;
  public MonsterFighting(MonsterType m){
    monster = m;
    sleepTime = m.AttackSpeed*1000;
  }

  public void run(){
    if(!exit){
      if(monster.HP <= 0){
        Reward();
        exit = true;
      }
      try {



        sleep((long) sleepTime);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public void Reward(){

  }
}
