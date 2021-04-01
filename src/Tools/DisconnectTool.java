package Tools;

import Type.*;
import ServerMainBody.*;

public class DisconnectTool {
  public static void PlayerDisconnect(int SocketID) {
    Server.User.removeIf(socket -> socket.ID == SocketID);
  }
}
