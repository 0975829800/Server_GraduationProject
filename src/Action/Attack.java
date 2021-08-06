package Action;

import Control.MonsterFighting;
import ID.ActionID;
import ID.SkillID;
import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.*;
import Type.MonsterType;

import java.io.OutputStream;
import java.util.Map;

public class Attack {

  public static void attack(PlayerInformation playerInformation,byte[] Data){
    int MapID = ByteArrayTransform.ToInt(Data,0);
    int skill = ByteArrayTransform.ToInt(Data,4);
    MonsterType tmp = null;

    System.out.println("PID: " + playerInformation.PID + " Attack " + MapID);

    for (MonsterType i : Server.Monster){
      if(i.MapObjectID == MapID){
        if(!i.Fighting){
          System.out.println("MID: " + i.MapObjectID + " is Fighting");
          i.DamagePID[0] = playerInformation.PID;
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

          MapType update = new MapType(i);
          MapType.MapIDCounter--;
          for(MapType mapType:Server.Map){
            if(mapType.MapObjectID == i.MapObjectID){
              mapType.MapCopy(update);
            }
          }
          MessageSender.StatusUpdate(playerInformation); //MP更新
          Server.Action.add(new ActionType(ActionID.PLAYER_ATTACK,playerInformation.MapID,playerInformation.PID,i.MapObjectID,i.MonsterID,damage,skill));
        }

        tmp = i;
      }
    }


    if(tmp == null){//治療
      for (MapType m : Server.Map){
        if(m.MapObjectID == MapID){
          if(m.TypeID == TypeID.PLAYER){   //是否為玩家
            for (PlayerInformation p: Server.Information){
              if(p.PID == m.BelongID){     //哪個玩家
                for(SkillType s : SkillID.SkillInformation){
                  if(s.SkillID == skill){  //哪個技能
                    playerInformation.status.MP -= s.MP;

                    double heal = playerInformation.status.MG*s.DamagePercent;
                    p.status.HP += heal;
                    if(p.status.HP > p.status.MAX_HP){
                      p.status.HP = p.status.MAX_HP;
                    }
                    Server.Action.add(new ActionType(ActionID.PLAYER_HEAL,playerInformation.MapID,playerInformation.PID,p.MapID,p.PID,heal,s.SkillID));
                    MessageSender.Heal(p,(int)heal);
                    MessageSender.StatusUpdate(p);
                    MessageSender.StatusUpdate(playerInformation);
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

    if(tmp != null){
      System.out.println(tmp.ToString());
    }
  }



  public static double Skill_Damage(PlayerInformation playerInformation, int Skill, MonsterType monster){
    double damage = 0;

    for(SkillType s: SkillID.SkillInformation){
      if(s.SkillID == Skill){
        playerInformation.status.MP -= s.MP;
        if(s.DamageSource.compareTo("ATK") == 0){
          damage = (((double)playerInformation.status.STR+(double)playerInformation.status.ESTR)*0.5+((double)playerInformation.status.AGI+(double)playerInformation.status.EAGI))*s.DamagePercent;
          damage -= (double)monster.Defence;
        }
        else if(s.DamageSource.compareTo("MATK") == 0){
          damage = ((double)playerInformation.status.MG+(double)playerInformation.status.EMG)*s.DamagePercent;
          damage -= (double)(monster.MagicDefence);
        }

      }
    }
    if(damage < 0){
      damage = 0;
    }
    System.out.println("Deal Damage : " + damage);
    return damage;
  }
}
