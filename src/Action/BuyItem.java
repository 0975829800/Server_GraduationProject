package Action;

import ID.ShopID;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.ItemType;
import Type.PlayerInformation;
import Type.Status;

import java.io.OutputStream;
import java.nio.ByteBuffer;

public class BuyItem {

  public static PlayerInformation BuyItem(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    byte[] buf;
    boolean hasSame = false;
    int order  = ByteArrayTransform.ToInt(Data,0);
    int amount = ByteArrayTransform.ToInt(Data,4);
    if(playerInformation.status.coin >= ShopID.shopItem.get(order).Price * amount){
      playerInformation.status.coin -= ShopID.shopItem.get(order).Price * amount;

      System.out.println(1);
      for (ItemType i : playerInformation.item){
        if(i.Amount == 99){ //道具滿(99個)跳過
          continue;
        }
        if(i.Item_ID == ShopID.shopItem.get(order).ItemID && i.Rarity == ShopID.shopItem.get(order).Rarity){ //找背包是否有一樣的道具
          System.out.println(2);
          i.Amount+=amount;
          if(i.Amount/100 > 0){ //是否超過單個道具欄上限
            int EmptyIndex = playerInformation.getEmptyItemBoxIndex();
            if(EmptyIndex != -1)   //目前未處理道具欄空格不足問題
              playerInformation.item.add(new ItemType(playerInformation.PID,EmptyIndex,i.Item_ID,i.Rarity,i.Amount%99));
            i.Amount = 99;
            hasSame = true;
            break;
          }
          else {
            i.Amount+=amount;
          }
          hasSame = true;
        }
      }
      if(!hasSame){
        System.out.println(3);
        playerInformation.item.add(new ItemType(playerInformation.PID,playerInformation.getEmptyItemBoxIndex(),
                ShopID.shopItem.get(order).ItemID,ShopID.shopItem.get(order).Rarity,amount));
      }

      System.out.println(4);
      buf = new byte[4+ItemType.SendSize*playerInformation.item.size()];
      System.arraycopy(ToCSharpTool.ToCSharp(playerInformation.status.coin),0,buf,0,4);
      int start = 4;
      for(ItemType i : playerInformation.item){
        System.arraycopy(i.getByte(),0,buf,start,ItemType.SendSize);
        start += ItemType.SendSize;
      }
    }else {
      buf = new byte[4];
      buf = ToCSharpTool.ToCSharp(-1);
    }

    try {
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }
    return playerInformation;
  }
}