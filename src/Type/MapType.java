package Type;

public class MapType {
  public double Longitude;
  public double Latitude;
  public int    ObjectID;
  public int    HP;
  public int    MP;

  public MapType(double longitude,double latitude,int objectID, int hp, int mp) {
    Longitude = longitude;
    Latitude = latitude;
    ObjectID = objectID;
    HP = hp;
    MP = mp;
  }
}
