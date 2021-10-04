package Type;

import ID.*;

public class MapType {
  public static final int MapTypeSize = 64; //total bytes
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
  public String Name = "";

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

  public void setName(String name){
    Name = name;
  }

  public MapType(MonsterType m){
    MapObjectID = MapIDCounter;
    MapIDCounter++;
    TypeID = ID.TypeID.MONSTER;
    BelongID = m.MonsterID;
    Longitude = m.Longitude;
    Latitude = m.Latitude;
    HP = (int)m.HP;
    MP = (int)m.MAX_HP;
    state = m.State;

  }
  public MapType() {
    MapObjectID = MapIDCounter;
    MapIDCounter++;
  }

  public void MapCopy(MapType m){
    TypeID = m.TypeID;
    BelongID = m.BelongID;
    Longitude = m.Longitude;
    Latitude = m.Latitude;
    HP = m.HP;
    MP = m.MP;
    state = m.state;
  }

  public String toString(){
    return String.format("%d %d %d %d %d %d %d %f %f", MapObjectID, TypeID, BelongID, Level, HP, MP, state, Longitude, Latitude);
  }
}
