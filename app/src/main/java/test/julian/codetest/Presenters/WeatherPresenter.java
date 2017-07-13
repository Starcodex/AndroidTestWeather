package test.julian.codetest.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import test.julian.codetest.Models.WeekDay;
import test.julian.codetest.Presenters.Adapters.DailyAdapter;
import test.julian.codetest.Presenters.Asynctasks.getWeatherData;
import test.julian.codetest.Presenters.Interfaces.Interfaces;
import test.julian.codetest.Presenters.Interfaces.Interfaces.IPresenter;
import test.julian.codetest.Presenters.Interfaces.Interfaces.IView;
import test.julian.codetest.R;

/**
 * Created by JulianStack on 12/07/2017.
 */

public class WeatherPresenter implements IPresenter{


    public final String TAG = "WeatherPresenter";

    private IView Wview;
    Activity Wactivity;
    Context Wcontext;

    final int permission = 55;

    // Layout Objects
    ImageView BackWeather;
    TextView CurrentTemperature;
    ImageView CurrentIcon;
    TextView CurrentDay;
    TextView Mph;
    TextView Cardinal;
    TextView Precip;

    RecyclerView listView;
    LinearLayoutManager layoutManager;
    ArrayList<WeekDay> horizontalList = new ArrayList<>();
    DailyAdapter mAdapter;

    private FusedLocationProviderClient mFusedLocationClient;

    // Constructor
    public WeatherPresenter(Context context, Activity activity, IView view){
        this.Wcontext = context;
        this.Wactivity = activity;
        this.Wview = view;
    }



    @Override
    public void processView(View ly, Bundle bundle) {

        CurrentTemperature = (TextView)ly.findViewById(R.id.temperature);
        CurrentIcon = (ImageView)ly.findViewById(R.id.icon);
        CurrentDay = (TextView)ly.findViewById(R.id.currentday);


        mAdapter = new DailyAdapter(horizontalList);
        listView = (RecyclerView) ly.findViewById(R.id.listdaily);
        listView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(Wcontext,LinearLayoutManager.HORIZONTAL,false);
        listView.setLayoutManager(layoutManager);
        checkPermission();

    }


    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(Wcontext,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Wcontext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission to user
            ActivityCompat.requestPermissions(Wactivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION }, permission);
        }else{
            checkLocation();
        }
    }



    public void checkLocation(){
        final LocationManager manager = (LocationManager)Wcontext.getSystemService    (Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
            Log.d(TAG,"GPS IS DISABLED");
            showSettingsAlert();
            //Toast.makeText(Wcontext, "GPS is disabled!", Toast.LENGTH_LONG).show();
        }

        else
        {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Wcontext);

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(Wactivity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d(TAG,String.valueOf(location.getLongitude()));
                                Log.d(TAG,String.valueOf(location.getLatitude()));
                                getData(location.getLatitude(),location.getLongitude());

                            }
                        }
                    });
        }

    }


    private void getData(double latitude, double longitude) {
        new getWeatherData(new getWeatherData.getWeatherInterface() {
            @Override
            public void success(ArrayList<WeekDay> lista) {
                if(horizontalList.size()>0){
                    horizontalList.clear();
                }
                horizontalList.addAll(lista);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void failed(String messageError) {
            }
        },Wcontext,latitude,longitude).execute();
    }



    public void permissionResults(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case permission :
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == permission) {
                    Log.d(TAG,"Permission Granted");
                    checkLocation();
                } else {
                    // permission denied
                    Toast.makeText(Wcontext,Wcontext.getResources().getString(R.string.grant_permission),Toast.LENGTH_LONG).show();
                    Wview.finishView();
                }
                return;
        }
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Wcontext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Wcontext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
