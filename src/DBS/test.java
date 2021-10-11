package DBS;

public class test {
  public static void main(String[] args) {
    try {
      DBConnection con = new DBConnection();
      con.init();
//      System.out.println(Arrays.toString(c.getAccountInform("2")));
      try {
//        Thread.sleep(7000);
//        System.out.println(con.register("4568","zzzz","e04"));
//        System.out.println(con.login("456789","zzzzzz"));
//        System.out.println(con.setName(31240985,new String("哈哈".getBytes("Big5"))));
//        System.out.println(con.hasAccount(354354));
//        System.out.println(con.createTeam(144906270,"test"));
//        System.out.println(con.setTeam(408417096,144906270));
//        System.out.println(con.setTeam(719616367,144906270));
//        System.out.println(con.setTeam(144906270,0));
//        System.out.println(con.replaceTeamLeader(144906270));
//        System.out.println(con.delTeam(408417096));
//        System.out.println(con.getTeamNum(1));

//        System.out.println(con.delItem_bag(408417096));
//        System.out.println(con.delEquipment_bag(144906270));
//        System.out.println(con.delProgresses(144906270));

//        System.out.println(con.addEquipment_bag(663828321,4,8,2,3,100,1,1,0));
//        System.out.println(con.getEquipment_bag(663828321).get(0).EquipmentBox_ID);
//        System.out.println(con.updateEquipment_bag(663828321,1,3,4,5,1,1,1,1));
//        System.out.println(con.delEquipment_bag(663828321,1));
        System.out.println(DBConnection.getName(267566541));

//        System.out.println(con.addItem_bag(663828321,3,3,1,8));
//        System.out.println(con.getItem_bag(663828321).get(0).PlayerID);
//        System.out.println(con.updateItem_bag(663828321,1,4,5,3));
//        System.out.println(con.delItem_bag(663828321,1));

//        System.out.println(con.addFriend(408417096,144906270,0));
//        System.out.println(con.addFriend(144906270,408417096,2));
//        System.out.println(con.acceptFriend(408417096,144906270));
//        System.out.println(con.acceptFriend(144906270,408417096));
//        System.out.println(con.delFriend(663828321,144906270));
//        System.out.println(con.getFriend(663828321).get(0).State);

//        System.out.println(con.addStatus(31240985,1,2,3,4,5,6,7,8,9,10,11,0,80));
//        System.out.println(con.getStatus(31240985).EXP);
//        System.out.println(con.updateStatus(144906270,100,100,100,100,20,10,20,10,1,10,0,7000, 0));
//        System.out.println(con.delStatus(146937808));

//        System.out.println(con.addProgress(144906270, 2, 0, 0,1));
//        System.out.println(con.setProgress(144906270, 2, 0, 0,3));
//        System.out.println(con.getProgress(144906270).get(0).information2);
//        System.out.println(con);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } }
}
