package Action;

import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.EquipmentBoxType;
import Type.PlayerInformation;

import java.io.IOException;


//未啟用
public class EquipmentLevelUp {
  public void LevelUp(PlayerInformation p, byte[] data){
    byte[] buf = null;
    boolean flag = false;
    int order = ByteArrayTransform.ToInt(data,0);
    for(EquipmentBoxType e: p.equipment){
      if(e.EquipmentBox_ID == order){
        p.status.coin -= 100 * Math.pow(1.1,e.Level-1);
        e.Level += 1;
        buf = ToCSharpTool.ToCSharp(1);
        flag = true;
        break;
      }
    }
    if(!flag){
      buf = ToCSharpTool.ToCSharp(2);
    }

    try {
      p.sc.getOutputStream().write(buf);
    } catch (IOException e) {
      e.printStackTrace();
    }
    MessageSender.StatusUpdate(p);
    MessageSender.EquipBoxUpdate(p);
  }
}
