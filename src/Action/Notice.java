package Action;

import Tools.ToCSharpTool;
import Type.NoticeType;

import java.io.OutputStream;

public class Notice {

  public static void send(OutputStream out, NoticeType notice){
    try {
      byte[] buf;
      buf = ToCSharpTool.ToCSharp(notice.noticeID+' '+notice.message);
      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
