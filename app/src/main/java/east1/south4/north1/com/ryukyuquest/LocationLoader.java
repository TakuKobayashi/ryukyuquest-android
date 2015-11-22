package east1.south4.north1.com.ryukyuquest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class LocationLoader extends ContextSingletonBase implements LocationListener {

  private LocationManager mLocationManager;
  private Location _location;
  private ArrayList<LocationUpdateListener> mListenerQueue;

  public void init(Context context){
    super.init(context);
    mListenerQueue = new ArrayList<LocationUpdateListener>();
    mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    _location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
  }

  public void addUpdateListener(LocationUpdateListener listener){
    mListenerQueue.add(listener);
  }

  public void removeUpdateListener(LocationUpdateListener listener){
    mListenerQueue.remove(listener);
  }

  public void startRequestLocation(){
    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this); // 位置情報リスナー
    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this); // 位置情報リスナー
  }

  public void stopRequestLocation(){
    mLocationManager.removeUpdates(this);
  }

  public Location getCurrentLocation(){
    return _location;
  }

  //デストラクタ
  @Override
  protected void finalize() throws Throwable {
    stopRequestLocation();
    mListenerQueue.clear();
    super.finalize();
  }

  @Override
  public void onLocationChanged(Location location) {

    Log.d("buruburunabi", "lon:" + location.getLongitude() + "lat:" + location.getLatitude());
    _location = location;
    for(LocationUpdateListener listener : mListenerQueue){
      listener.onUpdate(location);
    }
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
  }

  @Override
  public void onProviderEnabled(String provider) {
  }

  @Override
  public void onProviderDisabled(String provider) {
  }

  public interface LocationUpdateListener{
    public void onUpdate(Location location);
  }
}
