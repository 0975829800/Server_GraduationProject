package Type;

public class ItemType{
  public int PlayerID;
  public int Item_ID;
  public int Rarity;
  public int Amount;

  public ItemType(int PlayerID, int Item_ID, int Rarity, int Amount){
    this.PlayerID = PlayerID;
    this.Item_ID = Item_ID;
    this.Rarity = Rarity;
    this.Amount = Amount;
  }
  public  ItemType() {

  }
}