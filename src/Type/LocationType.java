package Type;

import javax.media.jai.PerspectiveTransform;
import javax.media.jai.WarpPerspective;
import java.awt.geom.Point2D;

public class LocationType {
  public int Sum = 0;
  public int locationID;
  public double[] X = new double[4];
  public double[] Y = new double[4];

  public LocationType(){

  }

  public LocationType(String data){
    String[] d = data.split(",");
    locationID = Integer.parseInt(d[0]);
    X[0] = Double.parseDouble(d[1]);
    X[1] = Double.parseDouble(d[3]);
    X[2] = Double.parseDouble(d[5]);
    X[3] = Double.parseDouble(d[7]);
    Y[0] = Double.parseDouble(d[2]);
    Y[1] = Double.parseDouble(d[4]);
    Y[2] = Double.parseDouble(d[6]);
    Y[3] = Double.parseDouble(d[8]);
  }

  public double[] CreateRandomPosition(){
    Point2D r = new Point2D.Double();
    r.setLocation(Math.random(),Math.random());
    PerspectiveTransform p = PerspectiveTransform.getSquareToQuad(X[0],Y[0],X[1],Y[1],X[2],Y[2],X[3],Y[3]);
    Point2D result  = p.transform(r,null);
    double[] ans = new double[2];
    ans[0] = result.getX();
    ans[1] = result.getY();
    return ans;
  }

  public boolean inLocation(double x, double y){
    double a = (X[1] - X[0])*(y - Y[0]) - (Y[1] - Y[0])*(x - X[0]);
    double b = (X[2] - X[1])*(y - Y[1]) - (Y[2] - Y[1])*(x - X[1]);
    double c = (X[3] - X[2])*(y - Y[2]) - (Y[3] - Y[2])*(x - X[2]);
    double d = (X[0] - X[3])*(y - Y[3]) - (Y[0] - Y[3])*(x - X[3]);
    if((a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0)) {
      return true;
    }
    return false;
  }
}
