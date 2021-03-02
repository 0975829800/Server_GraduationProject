public class Broadcast extends Thread{
  public void run() {
    try {
      sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
