package Progress;

import Action.MessageSender;
import ID.MissionID;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.PlayerInformation;
import Type.Progress;

import java.io.IOException;

public class TakeMission {
  public static void take(PlayerInformation p, byte[] data){
    int QuestID = ByteArrayTransform.ToInt(data,0);
    boolean flag = false;
    if(QuestID > MissionID.length){
      flag = true;
    }else {
      for(Progress progress: p.progress){
        if(progress.missionID == QuestID){
          flag = true;
          break;
        }
      }
    }

    byte[] buf;
    if(flag){
      buf = ToCSharpTool.ToCSharp(1);
    }else {
      p.progress.add(new Progress(p.PID,QuestID));
      buf = ToCSharpTool.ToCSharp(0);
    }

    try {
      p.sc.getOutputStream().write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    MessageSender.QuestUpdate(p);
  }
}
