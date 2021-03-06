package Action;

import Control.MonsterFighting;
import ID.SkillID;
import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.*;
import Type.MonsterType;

import java.io.OutputStream;
import java.util.Map;

public class Attack {

  public void Attack(OutputStream out, PlayerInformation playerInformation,byte[] Data){
    int MapID = ByteArrayTransform.ToInt(Data,0);
    int skill = ByteArrayTransform.ToInt(Data,4);
    MonsterType tmp = null;

    for (MonsterType i : Server.Monster){
      if(i.MapObjectID == MapID){
        if(!i.Fighting){
          Thread t = new Thread(new MonsterFighting(i));
          t.start();
          i.Fighting = true;
        }

        if(i.HP > 0){
          double damage = Skill_Damage(playerInformation, skill, i);
          i.HP -= damage;

          //累計傷害
          boolean flag = false;
          int length = 0;
          for (int j = 0; j < i.DamagePID.length; j++){
            if(i.DamagePID[j] == playerInformation.PID){
              flag = true;
              i.DamageStatistic[j] += damage;
            }

            if(i.DamagePID[j] == 0){
              break;
            }
            length++;
          }

          if(!flag){
            i.DamagePID[length] = playerInformation.PID;
            i.DamageStatistic[length] += damage;
          }

          //Server.Action.add(new ActionType());
        }

        tmp = i;
      }
    }


    if(tmp == null){//治療
      for (MapType m : Server.Map){
        if(m.MapObjectID == MapID){
          if(m.TypeID == TypeID.PLAYER){  //是否為玩家
            for (PlayerInformation p: Server.Information){
              if(p.PID == m.BelongID){    //哪個玩家
                for(SkillType s : SkillID.SkillInformation){
                  if(s.SkillID == skill){ //哪個技能
                    playerInformation.status.MP -= s.MP;

                    p.status.HP += playerInformation.status.MG*s.DamagePercent;
                    if(p.status.HP > p.status.MAX_HP){
                      p.status.HP = p.status.MAX_HP;
                    }
                    //Server.Action.add(); //治療動作發送
                    break;
                  }
                }
                break;
              }
            }
          }else{
            System.out.println("err");
          }
          break;
        }
      }
    }


  }



  public double Skill_Damage(PlayerInformation playerInformation, int Skill, MonsterType monster){
    double damage = 0;

    for(SkillType s: SkillID.SkillInformation){
      if(s.SkillID == Skill){
        playerInformation.status.MP -= s.MP;

        if(s.DamageSource.compareTo("ATK") == 0){
          damage = (double)((playerInformation.status.STR+playerInformation.status.ESTR)*0.5+(playerInformation.status.AGI+playerInformation.status.EAGI))*s.DamagePercent;
          damage -= (double)(monster.Defence);
        }
        else if(s.DamageSource.compareTo("MATK") == 0){
          damage = (double)(playerInformation.status.MG+playerInformation.status.EMG)*s.DamagePercent;
          damage -= (double)(monster.MagicDefence);
        }

      }
    }

    return damage;
  }
}
