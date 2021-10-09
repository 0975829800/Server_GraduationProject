package Action;

import DBS.DBConnection;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.EquipmentBoxType;
import Type.PlayerInformation;
import Type.Status;

import java.io.IOException;
import java.nio.charset.Charset;

public class GetOtherPlayerInformation {
  public static void get(PlayerInformation p, byte[] data){
    int OtherPID = ByteArrayTransform.ToInt(data,0);
    int start = 0, length = 0;
    boolean flag = false;
    for(PlayerInformation playerInformation: Server.Information){
      if(playerInformation.PID == OtherPID){
        flag = true;
        byte[] buf = new byte[Status.SendSize + 20 + 6*EquipmentBoxType.SendSize];
        byte[] Name = p.Name.getBytes(Charset.forName("big5"));
        System.arraycopy(Name,0,buf,start,Name.length);
        start += 20;
        byte[] status = playerInformation.status.getByte();
        System.arraycopy(status,0,buf,20,Status.SendSize);
        start += Status.SendSize;
        for(EquipmentBoxType e: playerInformation.equipment){
          if(e.Equipping){
            System.arraycopy(e.getByte(),0,buf,start,EquipmentBoxType.SendSize);
            start += EquipmentBoxType.SendSize;
            length++;
          }
        }
        byte[] ans = new byte[Status.SendSize + 20 + length*EquipmentBoxType.SendSize];
        System.arraycopy(buf,0,ans,0,ans.length);

        try {
          p.sc.getOutputStream().write(ans);
        } catch (Exception e) {
          e.printStackTrace();
        }

        break;
      }
    }
    if(!flag){
      if(DBConnection.hasAccount(OtherPID)){
        try {
          byte[] buf = new byte[20 + Status.SendSize + 6*EquipmentBoxType.SendSize];
          byte[] Name = DBConnection.getName(OtherPID).getBytes(Charset.forName("big5"));
          System.out.println(DBConnection.getName(OtherPID));
          System.arraycopy(Name,0,buf,start,Name.length);
          start += 20;
          byte[] status = DBConnection.getStatus(OtherPID).getByte();
          System.arraycopy(status,0,buf,start,Status.SendSize);
          start += Status.SendSize;
          for(EquipmentBoxType e: DBConnection.getEquipment_bag(OtherPID)){
            if(e.Equipping){
              System.arraycopy(e.getByte(),0,buf,start,EquipmentBoxType.SendSize);
              start += EquipmentBoxType.SendSize;
              length++;
            }
          }
          byte[] ans = new byte[Status.SendSize + 20 + length*EquipmentBoxType.SendSize];
          System.arraycopy(buf,0,ans,0,ans.length);
          p.sc.getOutputStream().write(ans);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }else {
        try {
          byte[] buf = new byte[1];

          p.sc.getOutputStream().write(buf);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

  }
}
