package Action;

import Tools.ByteArrayTransform;
import Type.PlayerInformation;

public class SetSkillPoint {
  public static void SkillPoint(PlayerInformation p, byte[] data){
    int MAX_HP = ByteArrayTransform.ToInt(data,0);
    int MAX_MP = ByteArrayTransform.ToInt(data,4);
    int STR    = ByteArrayTransform.ToInt(data,8);
    int MG     = ByteArrayTransform.ToInt(data,12);
    int AGI    = ByteArrayTransform.ToInt(data,16);
    int LUC    = ByteArrayTransform.ToInt(data,20);
    p.status.Skill_Point -= MAX_HP + MAX_MP + STR + MG + AGI + LUC;
    p.status.MAX_HP += MAX_HP * 10;
    p.status.MAX_MP += MAX_MP * 5;
    p.status.STR += STR;
    p.status.MG += MG;
    p.status.AGI += AGI;
    p.status.LUC += LUC;

    MessageSender.StatusUpdate(p);
    MessageSender.SetSkillPointSuccess(p);
  }
}
