package Action;

import DBS.DBConnection;
import Tools.ToCSharpTool;
import Type.Status;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;

public class Login {
  public static int login (OutputStream out, byte[] data) throws SQLException{
    try {
      DBConnection con = new DBConnection();
      byte[] buf = new byte[4];

      String info = new String(data);
      String account = info.substring(0,9).trim();
      String password = info.substring(10,19).trim();

      System.out.println(account +"."+ password);
      int get = con.login(account,password);
      if (get >= 0){
        System.out.println(get + " login");
        buf = ToCSharpTool.ToCSharp(get);
        out.write(buf);
        return get;
      }
      else{
        ByteBuffer.wrap(buf).putInt(get);
        out.write(buf);
      }
    }catch (Exception e){
      System.err.println(e);
    }
    return -1;
  }

  public static int register (OutputStream out, byte[] data) throws SQLException{
    try {
      DBConnection con = new DBConnection();
      byte[] buf = new byte[4];

      String info = new String(data);
      String account = info.substring(0,9).trim();
      String password = info.substring(10,19).trim();
      String name = info.substring(20,29).trim();
      System.out.println(account +"."+ password+'.'+name);
      int get = con.register(account,password,name);
      if (get >= 0){
        System.out.println(get + " register");
        buf = ToCSharpTool.ToCSharp(get);
        out.write(buf);
        return get;
      }
      else{
        ByteBuffer.wrap(buf).putInt(get);
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

//  public static String get_name (int PID) throws SQLException{
//    try {
//      DBConnection con = new DBConnection();
//
//      String get = con.getName(PID);
//      return get;
//    }catch (Exception e){
//      System.err.println(e);
//    }
//    return null;
//  }
  public static Status getStatus(int PID){
    try {
      DBConnection con = new DBConnection();
      return con.getStatus(PID);
    }catch (Exception e){
      System.err.println(e);
    }
    return null;
  }

  public static void sendStatus(OutputStream out,int PID){
    try {
      DBConnection con = new DBConnection();
      Status status = con.getStatus(PID);
      byte[] buf = status.getByte();
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }
  }
}
