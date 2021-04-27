package ID;

import Type.ShopEquipment;
import Type.ShopItem;

import java.util.ArrayList;

public class ShopID {
  public static final int Shop1 = 1;
  public static final int Shop2 = 2;
  public static ArrayList<ShopItem> shopItem = new ArrayList<>();
  public static ArrayList<ShopEquipment> shopEquipment = new ArrayList<>();


  public static void SetItemShop(){ //set shop items
    shopItem.add(new ShopItem(Shop1,0,ItemID.HP_Potion,1,100));
    shopItem.add(new ShopItem(Shop1,1,ItemID.MP_Potion,1,100));
    shopItem.add(new ShopItem(Shop1,2,ItemID.HP_Potion,2,300));
    shopItem.add(new ShopItem(Shop1,3,ItemID.MP_Potion,2,300));
    shopItem.add(new ShopItem(Shop1,4,ItemID.GetCoin,2,-1000));
  }

  public static void SetEquipmentShop(){ //set shop equipment
    shopEquipment.add(new ShopEquipment(Shop1,0,EquipmentID.InitialHead,EquipmentPartID.Head,1,0,0,300));
    shopEquipment.add(new ShopEquipment(Shop1,1,EquipmentID.InitialArmor,EquipmentPartID.Armor,1,0,0,300));
    shopEquipment.add(new ShopEquipment(Shop1,2,EquipmentID.InitialGloves,EquipmentPartID.Gloves,1,0,0,300));
    shopEquipment.add(new ShopEquipment(Shop1,3,EquipmentID.InitialLeg,EquipmentPartID.Leg,1,0,0,300));
    shopEquipment.add(new ShopEquipment(Shop1,4,EquipmentID.InitialSword,EquipmentPartID.Weapon,1,0,0,300));
    shopEquipment.add(new ShopEquipment(Shop1,5,EquipmentID.InitialShield,EquipmentPartID.Secondary_Weapon,1,0,0,300));
  }
}
