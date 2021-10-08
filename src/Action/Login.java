package Action;

import DBS.DBConnection;
import ID.ShopID;
import ServerMainBody.Server;
import Tools.ToCSharpTool;
import Type.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class Login {

  public static Boolean isOnline(int PID){
    for(int i : Server.online){
      if(i == PID) return true;
    }
    return false;
  }

  public static int login (OutputStream out, byte[] data) throws SQLException{
    try {
      byte[] buf;
      String info = new String(data);
      String account = info.substring(0,9).trim();
      String password = info.substring(10,19).trim();

      System.out.println(account +"."+ password);
      int get = DBConnection.login(account,password);
      if (get >= 0 && !isOnline(get)){
        System.out.println(get + " login");
        buf = ToCSharpTool.ToCSharp(get);
        out.write(buf);
        return get;
      }
      else if(!isOnline(get)){
        buf = ToCSharpTool.ToCSharp(-1);
        out.write(buf);
      }
      else{
        buf = ToCSharpTool.ToCSharp(-2);
        out.write(buf);
      }
    }catch (Exception e){
      System.err.println(e);
    }
    return -1;
  }

  public static int register (OutputStream out, byte[] data) throws SQLException{
    try {
      byte[] buf;

      String info = new String(data);
      String account = info.substring(0,9).trim();
      String password = info.substring(10,19).trim();
      String name = info.substring(20,29).trim();
      System.out.println(account +"."+ password+'.'+name);
      int get = DBConnection.register(account,password,name);
      if (get >= 0){
        System.out.println(get + " register");
        buf = ToCSharpTool.ToCSharp(get);
        out.write(buf);
        return get;
      }
      else{
        buf = ToCSharpTool.ToCSharp(get);
        out.write(buf);
      }
    }catch (Exception e){
      System.err.println(e);
    }
    return -1;
  }

//  public static boolean set_name (OutputStream out,int PID, byte[] data) throws SQLException{
//    try {
//      DBConnection con = new DBConnection();
//      byte[] buf = new byte[4];
//
//      String info = new String(data);
//      String name = info.trim();
//      boolean get = con.setName(PID,name);
//      if (get){
//        ByteBuffer.wrap(buf).putInt(1);
//        out.write(buf);
//        return get;
//      }
//      else{
//        ByteBuffer.wrap(buf).putInt(-1);
//        out.write(buf);
//      }
//    }catch (Exception e){
//      System.err.println(e);
//    }
//    return false;
//  }

  public static String get_name (int PID) throws SQLException{
    try {

      String get = DBConnection.getName(PID);
      return get;
    }catch (Exception e){
      System.err.println(e);
    }
    return null;
  }
  public static Status getStatus(int PID){
    try {
      return DBConnection.getStatus(PID);
    }catch (Exception e){
      System.err.println(e);
    }
    return new Status();
  }


  public static ArrayList<ItemType> getItem(int PID){
    try{
      return DBConnection.getItem_bag(PID);
    }catch (Exception e){
      System.err.println(e);
    }
    return new ArrayList<ItemType>();
  }

  public static void sendItem(OutputStream out, int PID){
    ArrayList<ItemType> item = getItem(PID);
    byte[] send = new byte[ItemType.SendSize * item.size()];
    if(item.size() == 0) send = new byte[1];
    for(int i = 0; i < item.size(); i++){
      byte[] temp = item.get(i).getByte();
      System.arraycopy(temp,0,send,i*ItemType.SendSize,ItemType.SendSize);
    }
    try {
      out.write(send);
      out.flush();
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static ArrayList<EquipmentBoxType> getEquipment(int PID){
    try{
      return DBConnection.getEquipment_bag(PID);
    }catch (Exception e){
      System.err.println(e);
    }
    return new ArrayList<EquipmentBoxType>();
  }

  public static ArrayList<Progress> getProgress(int PID){
    try {
      return DBConnection.getProgress(PID);
    }catch (Exception e){
      e.printStackTrace();
    }
    return new ArrayList<Progress>();
  }

  public static void sendEquipment(OutputStream out, int PID){
    ArrayList<EquipmentBoxType> Equipment = getEquipment(PID);
    byte[] send = new byte[EquipmentBoxType.SendSize * Equipment.size()];
    if(Equipment.size() == 0) send = new byte[1];
    for(int i = 0; i < Equipment.size(); i++){
      byte[] temp = Equipment.get(i).getByte();
      System.arraycopy(temp,0,send,i*EquipmentBoxType.SendSize, EquipmentBoxType.SendSize);
    }
    try {
      out.write(send);
      out.flush();
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static void sendShopInformation(OutputStream out){
    byte[] send = new byte[8 + ShopItem.SendSize * ShopID.shopItem.size() + ShopEquipment.SendSize * ShopID.shopEquipment.size()];
    System.arraycopy(ToCSharpTool.ToCSharp(ShopID.shopItem.size()),0,send,0,4);
    int start = 4;
    for(int i = 0; i < ShopID.shopItem.size(); i++){
      System.arraycopy(ShopID.shopItem.get(i).getByte(),0,send,start,ShopItem.SendSize);
      start+=ShopItem.SendSize;
    }
    System.arraycopy(ToCSharpTool.ToCSharp(ShopID.shopEquipment.size()),0,send,start,4);
    start += 4;
    for(int i = 0; i < ShopID.shopEquipment.size(); i++){
      System.arraycopy(ShopID.shopEquipment.get(i).getByte(),0,send,start,ShopEquipment.SendSize);
      start+=ShopEquipment.SendSize;
    }
    try {
      out.write(send);
      out.flush();
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static void Login_Send(PlayerInformation p, int PID){
    OutputStream out = null;
    try {
      out = p.sc.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    MessageSender.StatusUpdate(p);
    MessageSender.EquipBoxUpdate(p);
    MessageSender.ItemBoxUpdate(p);
    sendShopInformation(out);
  }

}
