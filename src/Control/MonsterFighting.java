package Control;


import Action.MessageSender;
import ID.ActionID;
import ID.LocationID;
import ID.MessageID;
import ID.SkillID;
import ServerMainBody.Server;
import Tools.LevelTool;
import Tools.ToCSharpTool;
import Type.*;

public class MonsterFighting extends Thread{
  MonsterType monster;
  double sleepTime;
  public MonsterFighting(MonsterType m){
    monster = m;
    sleepTime = m.AttackSpeed*1000;
  }

  public void run(){
    try {
      sleep((long) sleepTime);
    }catch (Exception e){
      e.printStackTrace();
    }

    while (true){

      for(int i = 0; i < 10; i++){ //確保攻擊目標是否離線
        boolean flag = true; //代表是否離線
        if(monster.DamagePID[i] != 0){
          for(PlayerInformation p: Server.Information){
            if(p.PID == monster.DamagePID[i]){
              flag = false;
              break;
            }
          }
          if(flag){
            monster.DamagePID[i] = 0;
            monster.DamageStatistic[i] = 0;
          }
        }
      }

      if(monster.HP <= 0){ //怪獸死亡
        Reward();
        removeMonster();
        break;
      }
      try {
        int max = -1;
        int pos = -1;
        //找仇恨最大值
        for(int i = 0; i < monster.DamagePID.length; i++){
          if(monster.DamageStatistic[i] > max && monster.DamagePID[i] != 0){
            max = monster.DamageStatistic[i];
            pos = i;
          }
        }

        //若無仇恨對象
        if(pos == -1){
          monster.Fighting = false;
          break;
        }
        //設定仇恨玩家
        PlayerInformation player = null;
        for(PlayerInformation p: Server.Information){
          if(p.PID == monster.DamagePID[pos]){
            player = p;
          }
        }
        //攻擊仇恨對象
        if(player != null){
          double damage = MonsterDamage(player);
          if(damage > player.status.HP){ //若玩家死亡
            player.status.HP = 0;

            if(player.MapAddress!=null){
              player.MapAddress.HP = 0;
            }

            CleanDamage(player.PID);
            PlayerDead(player);
          }else{                        //正常攻擊
            System.out.println("Monster " + monster.MapObjectID + " Attack " + player.PID);
            player.status.HP-=damage;

            if(player.MapAddress != null){
              player.MapAddress.HP = player.status.HP;
            }

            MonsterAttack(player,damage,monster.Skills[0]);
          }
        }


        sleep((long) sleepTime);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    System.out.println("Monster " + monster.MapObjectID + " process is close!");
  }




  public void Reward(){
    byte[] buf;
    try {
      for(int i = 0; i < 10; i++){
        if (monster.DamagePID[i] != 0){
          for (PlayerInformation p: Server.Information){
            //金幣發送
            p.status.coin+=monster.coin;
            MessageSender.Coin(p,monster.MonsterID,monster.coin);

            //經驗發送
            MessageSender.EXP(p,monster.MonsterID,monster.exp);
            // 升等
            if (LevelTool.expControl(p,monster.exp) == 1){
              MessageSender.LevelUp(p);
            }else {
              MessageSender.StatusUpdate(p);
            }

            //裝備發送

          }
        }
      }

    }catch (Exception e){
      e.printStackTrace();
    }

  }

  public void removeMonster(){
    Server.Action.add(new ActionType(ActionID.MONSTER_DEAD,monster.MapObjectID,monster.MonsterID,0,0,0,0)); //通知怪獸消失動畫
    for(LocationType l: LocationID.location){
      if(l.locationID == monster.Location){
        l.Sum--;
      }
    }
    Server.Monster.removeIf(m -> m == monster);
    Server.Map.removeIf(m -> m.MapObjectID == monster.MapObjectID);
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
          damage = (double) ((p.status.AGI + p.status.EAGI) + (p.status.STR + p.status.ESTR)/2)*s.DamagePercent - monster.Defence;
        }else {
          damage = (double) (p.status.MG+p.status.EMG)*s.DamagePercent - monster.MagicDefence;
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
    MessageSender.PlayerDead(p);
    p.Dead = true;
  }
}
