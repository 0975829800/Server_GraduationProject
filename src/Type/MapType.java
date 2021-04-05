package Type;

public class MapType {
  public static final int MapTypeSize = 44; //total bytes
  public static int MapIDCounter = 0;

  public int    MapObjectID;
  public int    TypeID;     //like player, monster or ....
  public int    BelongID;   //like PID, MID, IID....
  public int    Level;
  public int    HP;
  public int    MP;
  public int    state;      //狀態
  public double Longitude;  //經度
  public double Latitude;   //緯度

  public MapType(double longitude,double latitude,int typeID,int belongID, int level, int hp, int mp, int state) {
    MapObjectID = MapIDCounter;
    MapIDCounter++;

    Longitude = longitude;
    Latitude = latitude;
    TypeID = typeID;
    BelongID = belongID;
    Level = level;
    HP = hp;
    MP = mp;
    this.state = state;
  }

  public MapType() {
    MapObjectID = MapIDCounter;
    MapIDCounter++;
  }

  public String toString(){
    return String.format("%d %d %d %d %d %d %d %f %f", MapObjectID, TypeID, BelongID, Level, HP, MP, state, Longitude, Latitude);
  }
}
