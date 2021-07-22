package Type;

public class NoticeType {

  public int PlayerID;
  public int noticeID;
  public String message;

  public NoticeType(int PlayerID,int noticeID, String message){
    this.PlayerID = PlayerID;
    this.noticeID = noticeID;
    this.message = message;
  }
}
