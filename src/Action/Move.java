package Action;

import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.ActionType;
import Type.MapType;

public class Move {
  public static void move(int PID, byte[] Data){
    //send action that player move
    ActionType act = new ActionType();
    act.Information1 = ByteArrayTransform.ToDouble(Data,0);
    act.Information2 = ByteArrayTransform.ToDouble(Data,8);
    for(MapType m: Server.Map){
      if(m.TypeID == TypeID.PLAYER && m.BelongID == PID){
        act.MoverMapID = m.MapObjectID;
      }
    }
    act.MoverID = PID;
    act.TargetMapID = 0;
    act.TargetID = 0;
    Server.Action.add(act);

    //change play location in map
    for(MapType m: Server.Map){
      if(m.TypeID == TypeID.PLAYER && m.BelongID == PID){
        m.Longitude = ByteArrayTransform.ToDouble(Data,0);
        m.Latitude = ByteArrayTransform.ToDouble(Data,8);
      }
    }

  }
}
