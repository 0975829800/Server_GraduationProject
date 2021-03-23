import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.io.*;
public class test {
  public static void main(String[] args) {
    Socket client, action, map;
    OutputStream out;
    int port = 6666;
    int protocol;
    String text;
    byte[] buf;
    byte[] num = new byte[4];
    Scanner scanner = new Scanner(System.in);

    //寫一段數字和字串發送
    try {
      client = new Socket("127.0.0.1",6666);
      action = new Socket("127.0.0.1", 6667);
      map = new Socket("127.0.0.1", 6668);
      out = client.getOutputStream();
      while (true) {
        protocol = scanner.nextInt();
        text = scanner.next();
        buf = new byte[4+text.getBytes().length];
        ByteBuffer.wrap(num,0,4).putInt(protocol);
        System.arraycopy(num,0,buf,0,4);
        System.arraycopy(text.getBytes(),0,buf,4,text.getBytes().length);

        out.write(buf);
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
