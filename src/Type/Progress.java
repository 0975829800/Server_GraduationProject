package Type;

public class Progress {
  public int PlayID;
  public int ProgressID;
  public int state;
  public int information1;
  public int information2;

  public Progress(int PlayID,int ProgressID, int state, int information1, int information2){
    this.ProgressID = ProgressID;
    this.PlayID = PlayID;
    this.state = state;
    this.information1 = information1;
    this.information2 = information2;
  }
}
