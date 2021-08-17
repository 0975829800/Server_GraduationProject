package ID;

public class EquipmentPartID {
  public static final int Head = 1;
  public static final int Armor = 2;
  public static final int Gloves = 3;
  public static final int Leg = 4;
  public static final int Weapon = 5;

  public static int SetPart(String data){
    if(data.compareTo("head") == 0){
      return 1;
    }else if(data.compareTo("armor") == 0){
      return 2;
    }else if(data.compareTo("hand") == 0){
      return 3;
    }else if(data.compareTo("leg") == 0){
      return 4;
    }else if(data.compareTo("weapon") == 0){
      return 5;
    }
    return 0;
  }
}
