package faustina.app;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Service;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
public class Geo extends Service implements LocationListener {

    private final Context context;
    boolean isGPSEnabled=false;
    boolean canGetLocation=false;
    boolean isNetworkEnabled=false;
    double latitude;
    double longitude;
    StringBuilder str;
    String address,city,state,country,postalcode,known;
    //double la;
    //double lo;
    Location loc;
    String lat,lon;
    private static final long Minchangeupd=1*1;
    private static final long Mintime = 1*1;

    protected LocationManager locationmanager;
    public Geo(Context context)
    {
        this.context=context;
        getLocation();
    }
    public Location getLocation()
    {
        try
        {
            locationmanager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled=locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled && !isNetworkEnabled)
            {
                showSettingsAlert();
            }
            else
            {
                this.canGetLocation=true;
                if(isNetworkEnabled)
                {
                    locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,Mintime, Minchangeupd, this);


                    if(locationmanager!=null)
                    {
					/*la=latitude;
					lo=longitude;*/
                        loc=locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if(loc!=null)
                        {
                            latitude=loc.getLatitude();
                            lat=Double.toString(latitude);
                            longitude=loc.getLongitude();
                            lon=Double.toString(longitude);
                        }
                    }

                }
                if(isGPSEnabled)
                {
                    if(loc==null)
                    {
                        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,Mintime, Minchangeupd, this);

                        if(locationmanager!=null)
                        {
                            //la=latitude;
                            //lo=longitude;
                            loc=locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(loc!=null)
                            {
                                latitude=loc.getLatitude();
                                lat=Double.toString(latitude);
                                longitude=loc.getLongitude();
                                lon=Double.toString(longitude);
                            }
                        }
                    }

                }
                Geocoder geocoder;
                List<Address> addresses;
                geocoder=new Geocoder(this,Locale.getDefault());

                addresses=geocoder.getFromLocation(latitude, longitude, 1);
                if(addresses.size()>1)
                {
				 /*address=addresses.get(0).getAddressLine(0);
				city=addresses.get(0).getLocality();
				state=addresses.get(0).getAdminArea();
				country=addresses.get(0).getCountryName();
				postalcode=addresses.get(0).getPostalCode();
				known=addresses.get(0).getFeatureName();*/
                    //System.out.println(address+city+state);
                    Address address=addresses.get(0);
                    str=new StringBuilder("ADDRESS: \n");
                    for(int i=0;i<address.getMaxAddressLineIndex();i++)
                    {
                        str.append(address.getAddressLine(i)).append(" \n ");
                    }

                }



            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //return loc;
        //la=latitude;
        //lo=longitude;
        return loc;
    }
    public void stopUsingGps()
    {
        if(locationmanager!=null)
        {
            locationmanager.removeUpdates(Geo.this);
        }
    }
    /*public Location loce()
    {
        Location loc1=null;
        if(loc!=null)
        {
            loc1=loc;
        }
        return loc1;

    }*/
    public double getLatitude()
    {
        if(loc!=null)
        {
            latitude=loc.getLatitude();
            lat=Double.toString(latitude);
        }
        return latitude;
    }
    public double getLongitude()
    {
        if(loc!=null)
        {
            longitude=loc.getLongitude();
            lon=Double.toString(longitude);

        }
        return longitude;
    }
    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle("Settings");
        alertDialog.setMessage("Gps is not enabled go to settings and enable it?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });
        alertDialog.show();
    }
	/*public void check()
	{
		if((latitude+longitude)-(la+lo)>=0.0000003)
		{
			AlertDialog.Builder alertDialog1=new AlertDialog.Builder(context);
			alertDialog1.setTitle("Image");
			alertDialog1.setMessage("Hey chinniah!");
			alertDialog1.show();
		}
	}*/

    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }



}