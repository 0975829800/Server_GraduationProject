package Action;

import ID.TypeID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Type.MapType;
import Type.PlayerInformation;

public class CreateNPC {
  public static void create(PlayerInformation p, byte[] data){
    int ID = ByteArrayTransform.ToInt(data, 0);
    double lat = ByteArrayTransform.ToDouble(data,4);
    double lon = ByteArrayTransform.ToDouble(data,12);
    MapType map = new MapType();
    map.Latitude = lat;
    map.Longitude = lon;
    map.TypeID = TypeID.NPC;
    map.BelongID = ID;
    String name = null;
    switch (ID){
      case 1:
        name = "吉他社社長";
        break;
      case 2:
        name = "史丹利";
        break;
      case 3:
        name = "終結者";
        break;
      default:
        name = "";
    }
    map.Name = name;
    Server.Map.add(map);
  }
}
