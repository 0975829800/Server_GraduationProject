package ID;

import ServerMainBody.Server;
import Type.ItemType;
import Type.ShopItem;
import Type.SkillType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class ItemID {
  public static Queue<ShopItem> shopItems = new LinkedList<>();

  public static void SetShopItems(){
    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Item.csv"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line;
      reader.readLine();
      while ((line = reader.readLine())!= null){
        shopItems.add(new ShopItem(line));
      }
      reader.close();
    }catch (Exception e){
      e.printStackTrace();
    }

    if(Server.debug){
      for (ShopItem s : shopItems){
        System.out.printf("%d %d %d %d %d\n",s.order,s.ShopID,s.ItemID,s.Rarity,s.Price);
      }
    }
  }

  public static ShopItem GetItemState(int ItemID){
    for(ShopItem s : shopItems){
      if(s.ItemID == ItemID){
        return s;
      }
    }
    return null;
  }
}
