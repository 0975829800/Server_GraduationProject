package ID;

import Type.LocationType;
import Type.MonsterType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class LocationID {
  public static Queue<LocationType> location = new LinkedList<>();

  public static void setLocation() {
    try{
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src\\ID\\Location.csv"));
      BufferedReader reader = new BufferedReader(inputStreamReader);

      String line = null;
      while ((line = reader.readLine())!= null){
        location.add(new LocationType(line));
      }
      reader.close();
    }catch (Exception e){
      System.out.println(e);
    }
  }
}
