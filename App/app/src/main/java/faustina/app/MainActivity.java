package faustina.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    boolean isGPSEnabled=false;
    boolean isNetworkEnabled=false;
    String locationAddress;
    String messagetosend="I am in danger come to kotturpuram";
    database d=new database();
    String contact1="8870607128";
    String contact2="9789959353";
    String contact3="9789822198";
    String send;
    int len=d.res.length;
    AppLocationService appLocationService;
    StringBuilder sb=new StringBuilder();
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appLocationService = new AppLocationService(
                MainActivity.this);
        Button b1;
        ImageButton im1,im2,im3;
        im1=(ImageButton)findViewById(R.id.imageButton1);
        b1=(Button)findViewById(R.id.database);
        im2=(ImageButton)findViewById(R.id.imageButton2);
        im3=(ImageButton)findViewById(R.id.imageButton3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbintent=new Intent(getApplicationContext(),database.class);
                startActivity(dbintent);
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);



                if (location!=null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());

                    SmsManager.getDefault().sendTextMessage("9487380580", null, messagetosend + "\n" + sb.toString(), null, null);

                   SmsManager.getDefault().sendTextMessage("9789959353", null, messagetosend + "\n" + sb.toString(), null, null);
                    SmsManager.getDefault().sendTextMessage("8870607128", null, messagetosend + "\n" + sb.toString(), null, null);
                    Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();


                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            MainActivity.this);
                    alertDialog.setTitle("SETTINGS");
                    alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
                    alertDialog.setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    MainActivity.this.startActivity(intent);
                                }
                            });
                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
            }
        });




        //btnShowAddress = (Button) findViewById(R.id.button2);




        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8870607128"));
                startActivity(i);

            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapIntent);

            }
        });


    }

    private class GeocoderHandler extends Handler {

        public void handleMessage(Message message) {


            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            sb.append(locationAddress);
        }
    }



}
