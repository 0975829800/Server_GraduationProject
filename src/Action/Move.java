package Action;

import ID.ActionID;
import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.ActionType;
import Type.MapType;
import Type.PlayerInformation;

public class Move {
  public static void move(PlayerInformation p, byte[] Data){
    //send action that player move
    ActionType act = new ActionType();
    double Longitude = ByteArrayTransform.ToDouble(Data,0);
    double Latitude = ByteArrayTransform.ToDouble(Data,8);
    act.MoverMapID = p.MapID;
    act.Information1 = Longitude;
    act.Information2 = Latitude;
    p.MapAddress.Longitude = Longitude;
    p.MapAddress.Latitude = Latitude;
    act.ActionID = ActionID.PLAYER_MOVE;
    act.MoverID = p.PID;
    act.TargetMapID = 0;
    act.TargetID = 0;
    Server.Action.add(act);

  }
}
