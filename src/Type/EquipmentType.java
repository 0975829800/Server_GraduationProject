package Type;

public class EquipmentType {
  public int EID;
  public double STR;
  public double MG;
  public double AGI;
  public double LUC;
  public double HP;
  public double MP;
  public double GrowthRate;
  public int part;
  public int price;

  public EquipmentType(String Line){
    String[] s = Line.split(",");
    EID = Integer.parseInt(s[0]);
    STR = Double.parseDouble(s[2]);
    MG = Double.parseDouble(s[3]);
    AGI = Double.parseDouble(s[4]);
    LUC = Double.parseDouble(s[5]);
    HP = Double.parseDouble(s[6]);
    MP = Double.parseDouble(s[7]);
    GrowthRate = Double.parseDouble(s[8]);
    part = Integer.parseInt(s[9]);
    price = Integer.parseInt(s[10]);
  }
}
