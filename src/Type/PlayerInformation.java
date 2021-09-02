package Type;

import java.net.Socket;
import java.util.ArrayList;

public class PlayerInformation {
  public String Name;
  public int PID;
  public int MapID;
  public MapType MapAddress;
  public boolean Dead = false;
  public Socket sc;
  public Socket mss;
  public Status status;
  public ArrayList<ItemType> item;
  public ArrayList<EquipmentBoxType> equipment;
  public ArrayList<Progress> progress;

  public PlayerInformation(){ }

  public int getEmptyItemBoxIndex(){
    boolean[] full = new boolean[50];
    for(ItemType i : item){
      full[i.ItemBox_ID] = true;
    }
    for(int i = 0; i < 50; i++){
      if(!full[i]){
        return i;
      }
    }
    return -1;
  }

  public int getEmptyEquipmentBoxIndex(){
    boolean[] full = new boolean[50];
    for(EquipmentBoxType i : equipment){
      full[i.EquipmentBox_ID] = true;
    }
    for(int i = 0; i < 50; i++){
      if(!full[i]){
        return i;
      }
    }
    return -1;
  }

  public byte[] getProgressesByte(){
    byte[] buf = new byte[Progress.SendSize * progress.size()];
    int start = 0;
    for(Progress p : progress){
      System.arraycopy(p.getByte(),0,buf,start,Progress.SendSize);
      start += Progress.SendSize;
    }
    return buf;
  }

  @Override
  public String toString() {
    return "PlayerInformation{" +
            "PID=" + PID +
            ", MapID=" + MapID +
            ", Dead=" + Dead +
            ", sc=" + sc +
            ", mss=" + mss +
            ", status=" + status.toString() +
            ", item=" + item +
            ", equipment=" + equipment +
            ", progress=" + progress +
            '}';
  }
}
