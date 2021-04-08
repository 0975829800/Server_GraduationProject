package DBS;

public class Progress {
  public int PlayID;
  public int MID;
  public boolean status;
  public Progress(int PlayID,int MID, boolean status){
    this.MID = MID;
    this.PlayID = PlayID;
    this.status = status;
  }
}
