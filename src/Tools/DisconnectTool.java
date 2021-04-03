package Tools;

import Type.*;
import ServerMainBody.*;

public class DisconnectTool {
  public static void PlayerDisconnect(int SocketID,int PID) {
    Server.User.removeIf(socket -> socket.ID == SocketID);
  }
}
