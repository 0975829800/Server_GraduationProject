package Action;

import Tools.ByteArrayTransform;
import Type.PlayerInformation;

import java.io.OutputStream;

public class UseItem {
  public static void useItem(OutputStream out, PlayerInformation p, byte[] data){
    int item = ByteArrayTransform.ToInt(data,0);
    int amount = ByteArrayTransform.ToInt(data,4);
    switch (item){
      case 1: case 2: case 3: case 4:
        HP_potion(out,p,item);
        break;
      case 5: case 6: case 7: case 8:
        MP_potion(out,p,item);
        break;
      case 9: case 10: case 11: case 12:
        MIX_potion(out,p,item);
        break;
      case 13: case 14: case 15: case 16:
        Stronger(out,p,item);
        break;
      case 17: case 18: case 19: case 20:
        Vaccine(out,p,item);
        break;
    }
  }


  public static void HP_potion(OutputStream out, PlayerInformation p,int item){
    int heal = 0;
    switch (item){
      case 1:
        heal = 10;
        break;
      case 2:
        heal = 50;
        break;
      case 3:
        heal = 200;
        break;
      case 4:
        heal = 600;
        break;
    }
    p.status.HP += heal;
    if(p.status.HP > p.status.MAX_HP){
      p.status.HP = p.status.MAX_HP;
    }
    MessageSender.Heal(p,heal);
    MessageSender.StatusUpdate(p);
  }
  public static void MP_potion(OutputStream out, PlayerInformation p,int item){
    int heal = 0;
    switch (item){
      case 5:
        heal = 10;
        break;
      case 6:
        heal = 50;
        break;
      case 7:
        heal = 200;
        break;
      case 8:
        heal = 600;
        break;
    }
    p.status.MP += heal;
    if(p.status.MP > p.status.MAX_MP){
      p.status.MP = p.status.MAX_MP;
    }
    MessageSender.HealMP(p,heal);
    MessageSender.StatusUpdate(p);
  }
  public static void MIX_potion(OutputStream out, PlayerInformation p,int item){
    int heal = 0;
    switch (item){
      case 5:
        heal = 10;
        break;
      case 6:
        heal = 50;
        break;
      case 7:
        heal = 200;
        break;
      case 8:
        heal = 600;
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
    MessageSender.Heal(p,heal);
    MessageSender.HealMP(p,heal);
    MessageSender.StatusUpdate(p);
  }
  public static void Stronger(OutputStream out, PlayerInformation p,int item){
    switch (item){
      case 13:
        p.status.STR++;
        break;
      case 14:
        p.status.MG++;
        break;
      case 15:
        p.status.AGI++;
        break;
      case 16:
        p.status.LUC++;
        break;
    }
    MessageSender.Stronger(p,item,1);
    MessageSender.StatusUpdate(p);
  }
  public static void Vaccine(OutputStream out, PlayerInformation p,int item){
    int BuffRange = 0;
    switch (item){
      case 17:
        BuffRange = 10;
        p.status.MAX_HP += BuffRange;
        break;
      case 18:
        BuffRange = 5;
        p.status.MAX_MP += BuffRange;
        break;
      case 19:
        BuffRange = 10;
        p.status.MAX_HP -= BuffRange;
        break;
      case 20:
        BuffRange = 5;
        p.status.MAX_MP -= BuffRange;
        break;
    }
    MessageSender.Stronger(p,item,BuffRange);
    MessageSender.StatusUpdate(p);
  }
}
