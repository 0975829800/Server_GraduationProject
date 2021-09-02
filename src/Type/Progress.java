package Type;

import Action.MessageSender;
import ID.MissionID;
import ID.TypeID;
import Tools.ToCSharpTool;

public class Progress {
  public static int SendSize = 20;

  public int PlayerID;
  public int missionID;
  public int state;
  public int information1;
  public int information2;

  public Progress(int PlayerID,int missionID, int state, int information1, int information2){
    this.missionID = missionID;
    this.PlayerID = PlayerID;
    this.state = state;
    this.information1 = information1;
    this.information2 = information2;
  }

  public Progress(int PlayerID,int missionID){
    this.missionID = missionID;
    this.PlayerID = PlayerID;
    this.information1 = 0;
    this.information2 = MissionID.missions[missionID].CompleteAmount;

    if(MissionID.missions[missionID].DemandType == TypeID.NOTHING){
      this.state = 1;     //完成
    }else {
      this.state = 0;     //未完成
    }
  }


  public byte[] getByte(){
    byte[] buf = new byte[SendSize];
    System.arraycopy(ToCSharpTool.ToCSharp(PlayerID),0,buf,0,4);
    System.arraycopy(ToCSharpTool.ToCSharp(missionID),0,buf,4,4);
    System.arraycopy(ToCSharpTool.ToCSharp(state),0,buf,8,4);
    System.arraycopy(ToCSharpTool.ToCSharp(information1),0,buf,12,4);
    System.arraycopy(ToCSharpTool.ToCSharp(information2),0,buf,16,4);
    return buf;
  }

  public static void KillMonster(PlayerInformation p, int MID){
    for(Progress progress : p.progress){
      if (MissionID.missions[progress.missionID].DemandType == TypeID.MONSTER  && progress.state == 0){
        if(MissionID.missions[progress.missionID].DemandBelong == MID){
          progress.information1++;

          if(progress.information1 >= progress.information2){
            progress.information1 = progress.information2;
            progress.state = 1;
          }

        }
      }
    }
    MessageSender.QuestUpdate(p);
  }
}
