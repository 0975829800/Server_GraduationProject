package Type;

public class ShopEquipment {
  public int ShopID;
  public int order;
  public int EquipmentID;
  public int part;
  public int Rarity;
  public int Level = 1;
  public int Skill1;
  public int Skill2;
  public int Price;

  public ShopEquipment(int ShopID, int order, int EquipmentID, int part, int Rarity, int Skill1, int Skill2, int Price){
    this.EquipmentID = EquipmentID;
    this.order = order;
    this.part = part;
    this.Rarity = Rarity;
    this.ShopID = ShopID;
    this.Skill1 = Skill1;
    this.Skill2 = Skill2;
    this.Price = Price;
  }

}
