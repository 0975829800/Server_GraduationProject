package Control;


import ID.ActionID;
import ID.MessageID;
import ID.SkillID;
import ServerMainBody.Server;
import Tools.ToCSharpTool;
import Type.ActionType;
import Type.MonsterType;
import Type.PlayerInformation;
import Type.SkillType;

public class MonsterFighting extends Thread{
  MonsterType monster;
  double sleepTime;
  public MonsterFighting(MonsterType m){
    monster = m;
    sleepTime = m.AttackSpeed*1000;
  }

  public void run(){
    if(monster.HP <= 0){ //怪獸死亡
      Reward();
      removeMonster();
      System.exit(0);
    }
    try {
      int max = 0;
      int pos = -1;
      //找仇恨最大值
      for(int i = 0; i < monster.DamagePID.length; i++){
        if(monster.DamageStatistic[i] > max){
          max = monster.DamageStatistic[i];
          pos = i;
        }
      }

      //若無仇恨對象
      if(pos == -1){
        monster.Fighting = false;
        System.exit(0);
      }

      //設定仇恨玩家
      PlayerInformation player = null;
      for(PlayerInformation p: Server.Information){
        if(p.PID == monster.DamageStatistic[max]){
          player = p;
        }
      }
      //攻擊仇恨對象
      double damage = MonsterDamage(player);
      if(damage > player.status.HP){ //若玩家死亡
        player.status.HP = 0;
        CleanDamage(player.PID);
        PlayerDead(player);
      }else{                        //正常攻擊
        player.status.HP-=damage;
        MonsterAttack(player,damage,monster.Skills[0]);
      }

      sleep((long) sleepTime);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }




  public void Reward(){
    byte[] buf;
    try {
      for(int i = 0; i < 10; i++){
        if (monster.DamagePID[i] != 0){
          for (PlayerInformation p: Server.Information){
            //金幣發送
            p.status.coin+=monster.coin;
            buf = new byte[12];
            System.arraycopy(ToCSharpTool.ToCSharp(MessageID.COIN),0,buf,0,4);
            System.arraycopy(ToCSharpTool.ToCSharp(monster.MonsterID),0,buf,4,4);
            System.arraycopy(ToCSharpTool.ToCSharp(monster.coin),0,buf,8,4);
            p.mss.getOutputStream().write(buf);

            //經驗發送
            //LevelControl()

            //裝備發送
          }
        }
      }

    }catch (Exception e){

    }

  }

  public void removeMonster(){
    Server.Action.add(new ActionType(ActionID.MONSTER_DEAD,monster.MapObjectID,monster.MonsterID,0,0,0,0)); //通知怪獸消失動畫
    Server.Monster.removeIf(m -> m == monster);
  }

  public void MonsterAttack(PlayerInformation p,double damage,int SkillID){
    Server.Action.add(new ActionType(ActionID.MONSTER_ATTACK,monster.MapObjectID,monster.MonsterID,p.MapID,p.PID,damage,(double) SkillID)); //通知怪獸攻擊動畫
  }

  public void CleanDamage(int PID){
    boolean flag = false;
    for(int i = 0; i < monster.DamagePID.length; i++){
      if(monster.DamagePID[i] == PID){
        flag = true;
        continue;
      }
      if(flag){
        monster.DamagePID[i-1] = monster.DamagePID[i];
        monster.DamageStatistic[i-1] = monster.DamageStatistic[i];
      }
    }
    monster.DamageStatistic[monster.DamageStatistic.length-1] = 0;
    monster.DamagePID[monster.DamagePID.length-1] = 0;
  }

  public double MonsterDamage(PlayerInformation p){
    double damage = 0;
    for (SkillType s: SkillID.SkillInformation){
      if(s.SkillID == monster.Skills[0]){
        if(s.DamageSource.compareTo("ATK") == 0){
          damage = (double) ((p.status.AGI + p.status.EAGI) + (p.status.STR + p.status.ESTR)/2) - monster.Defence;
          damage *= s.DamagePercent;
        }else {
          damage = (double) (p.status.MG+p.status.EMG) - monster.MagicDefence;
          damage *= s.DamagePercent;
        }
      }
    }
    if(damage < 0){
      damage = 0;
    }

    return damage;
  }

  public void PlayerDead(PlayerInformation p){
    Server.Action.add(new ActionType(ActionID.PLAYER_DEAD,p.MapID,p.PID,0,0,0,0));
    p.Dead = true;
  }
}
