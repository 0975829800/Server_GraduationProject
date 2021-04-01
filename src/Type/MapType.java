package Type;

public class MapType {
  public static final int MapTypeSize = 40; //total bytes

  public int    TypeID;     //like player, monster or ....
  public int    BelongID;   //like PID, MID, IID....
  public int    Level;
  public int    HP;
  public int    MP;
  public int    state;      //狀態
  public double Longitude;  //經度
  public double Latitude;   //緯度

  public MapType(double longitude,double latitude,int typeID,int belongID, int level, int hp, int mp) {
    Longitude = longitude;
    Latitude = latitude;
    TypeID = typeID;
    BelongID = belongID;
    Level = level;
    HP = hp;
    MP = mp;
  }
}
