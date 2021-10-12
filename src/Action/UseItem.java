package Action;

import ID.ActionID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.ActionType;
import Type.ItemType;
import Type.PlayerInformation;

import java.io.OutputStream;

public class UseItem {
  public static void useItem(OutputStream out, PlayerInformation p, byte[] data){
    int order = ByteArrayTransform.ToInt(data,0);
    int item = 0;
    int amount = ByteArrayTransform.ToInt(data,4);
    for(ItemType i: p.item){
      if(order == i.ItemBox_ID){
        i.Amount -= amount;
        if(i.Amount < 1){
          p.item.removeIf(it->it == i);
        }
        item = i.Item_ID;
        break;
      }
    }
    switch (item){
      case 1: case 2: case 3: case 4:
        HP_potion(out,p,item,amount);
        break;
      case 5: case 6: case 7: case 8:
        MP_potion(out,p,item,amount);
        break;
      case 9: case 10: case 11: case 12:
        MIX_potion(out,p,item,amount);
        break;
      case 13: case 14: case 15: case 16:
        Stronger(out,p,item,amount);
        break;
      case 17: case 18: case 19: case 20:
        Vaccine(out,p,item,amount);
        break;
    }
    MessageSender.ItemBoxUpdate(p);
  }

  public static void HP_potion(OutputStream out, PlayerInformation p,int item,int amount){
    int heal = 0;
    switch (item){
      case 1:
        heal = 10*amount;
        break;
      case 2:
        heal = 50*amount;
        break;
      case 3:
        heal = 200*amount;
        break;
      case 4:
        heal = 600*amount;
        break;
    }
    p.status.HP += heal;
    if(p.status.HP > p.status.MAX_HP){
      p.status.HP = p.status.MAX_HP;
    }
    if(p.Dead){
      p.Dead = false;
    }
    MessageSender.Heal(p,heal);
    Server.Action.add(new ActionType(ActionID.PLAYER_HEAL,p.MapID,p.PID,0,0,heal,0));
    MessageSender.StatusUpdate(p);
  }
  public static void MP_potion(OutputStream out, PlayerInformation p,int item,int amount){
    int heal = 0;
    switch (item){
      case 5:
        heal = 10*amount;
        break;
      case 6:
        heal = 50*amount;
        break;
      case 7:
        heal = 200*amount;
        break;
      case 8:
        heal = 600*amount;
        break;
    }
    p.status.MP += heal;
    if(p.status.MP > p.status.MAX_MP){
      p.status.MP = p.status.MAX_MP;
    }
    MessageSender.HealMP(p,heal);
    MessageSender.StatusUpdate(p);
  }
  public static void MIX_potion(OutputStream out, PlayerInformation p,int item,int amount){
    int heal = 0;
    switch (item){
      case 5:
        heal = 10*amount;
        break;
      case 6:
        heal = 50*amount;
        break;
      case 7:
        heal = 200*amount;
        break;
      case 8:
        heal = 600*amount;
        break;
    }
    p.status.HP += heal;
    if(p.status.HP > p.status.MAX_HP){
      p.status.HP = p.status.MAX_HP;
    }
    p.status.MP += heal;
    if(p.status.MP > p.status.MAX_MP){
      p.status.MP = p.status.MAX_MP;
    }
    if(p.Dead){
      p.Dead = false;
    }
    MessageSender.Heal(p,heal);
    Server.Action.add(new ActionType(ActionID.PLAYER_HEAL,p.MapID,p.PID,0,0,heal,0));
    MessageSender.HealMP(p,heal);
    MessageSender.StatusUpdate(p);
  }
  public static void Stronger(OutputStream out, PlayerInformation p,int item,int amount){
    switch (item){
      case 13:
        p.status.STR += amount;
        break;
      case 14:
        p.status.MG += amount;
        break;
      case 15:
        p.status.AGI += amount;
        break;
      case 16:
        p.status.LUC += amount;
        break;
    }
    MessageSender.Stronger(p,item,amount);
    MessageSender.StatusUpdate(p);
  }
  public static void Vaccine(OutputStream out, PlayerInformation p,int item,int amount){
    int BuffRange = 0;
    switch (item){
      case 17:
        BuffRange = 10;
        p.status.MAX_HP += BuffRange*amount;
        break;
      case 18:
        BuffRange = 5;
        p.status.MAX_MP += BuffRange*amount;
        break;
      case 19:
        BuffRange = 10;
        p.status.MAX_HP -= BuffRange*amount;
        if(p.status.MAX_HP < 1){
          p.status.MAX_HP = 1;
        }
        if(p.status.MAX_HP < p.status.HP){
          p.status.HP = p.status.MAX_HP;
        }
        break;
      case 20:
        BuffRange = 5;
        p.status.MAX_MP -= BuffRange*amount;
        if(p.status.MAX_MP < 1){
          p.status.MAX_MP = 1;
        }
        if(p.status.MAX_MP < p.status.MP){
          p.status.MP = p.status.MAX_MP;
        }
        break;
    }
    MessageSender.Stronger(p,item,BuffRange*amount);
    MessageSender.StatusUpdate(p);
  }
}
