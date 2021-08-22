package Action;

import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.EquipmentBoxType;
import Type.PlayerInformation;
import Type.Status;

import java.io.IOException;

public class GetOtherPlayerInformation {
  public static void get(PlayerInformation p, byte[] data){
    int OtherPID = ByteArrayTransform.ToInt(data,0);
    for(PlayerInformation playerInformation: Server.Information){
      if(playerInformation.PID == OtherPID){
        int start = 0, length = 0;
        byte[] buf = new byte[Status.SendSize + 16 + 6*EquipmentBoxType.SendSize];
        byte[] status = playerInformation.status.getByte();
        System.arraycopy(status,0,buf,0,Status.SendSize);
        start += Status.SendSize;
        byte[] Estatus = playerInformation.status.getEquipByte();
        System.arraycopy(Estatus,0,buf,start,Estatus.length);
        start += Estatus.length;
        for(EquipmentBoxType e: playerInformation.equipment){
          if(e.Equipping){
            System.arraycopy(e.getByte(),0,buf,start,EquipmentBoxType.SendSize);
            start += EquipmentBoxType.SendSize;
            length++;
          }
        }
        byte[] ans = new byte[Status.SendSize + 16 + length*EquipmentBoxType.SendSize];
        System.arraycopy(buf,0,ans,0,ans.length);

        try {
          p.sc.getOutputStream().write(ans);
        } catch (Exception e) {
          e.printStackTrace();
        }

        break;
      }
    }

  }
}
