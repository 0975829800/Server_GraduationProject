package Tools;

import ID.TypeID;
import Type.*;
import ServerMainBody.*;

public class DisconnectTool {
  public static void PlayerDisconnect(int SocketID,int PID) {
    Server.User.removeIf(socket -> socket.ID == SocketID);                      //remove socket about player
    Server.Map.removeIf(m -> m.TypeID == TypeID.PLAYER && m.BelongID == PID);   //remove map information about player
  }
}
