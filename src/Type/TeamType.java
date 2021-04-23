package Type;

public class TeamType {
  public static int TEAM_ID_COUNTER = 0;

  int TeamID;
  int[] PlayerID;
  boolean[] online;
  String TeamName;

  public TeamType(int[] PlayerID, int TeamID, String TeamName){
    this.PlayerID = PlayerID;
    this.TeamID = TeamID;
    this.TeamName = TeamName;
  }
}
