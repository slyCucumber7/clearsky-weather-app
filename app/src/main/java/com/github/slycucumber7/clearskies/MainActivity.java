package com.github.slycucumber7.clearskies;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    Location recentLocation;
    LocationCallback mLocationCallBack;
    TextView tv;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static boolean PERMISSION_STATE = false;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        tv = findViewById(R.id.displayWindow);


// get location here

checkAppPermissions();
if(PERMISSION_STATE){
    LocationRequest.Builder mReqB = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,20000);
    LocationRequest mReq = mReqB.build();
     mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location tempLocation = locationResult.getLastLocation();
            if(tempLocation != null){
                recentLocation = locationResult.getLastLocation();
            }
        }
    };
    LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mReq,mLocationCallBack,null);
}

//To stop location updates:
//        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(mLocationCallBack);




// end location setup




        //JSON request proof of concept using Android Volley

        //Create Request Queue
//        RequestQueue rq = Volley.newRequestQueue(this);
//        String url = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&forecast_days=1";
//
//        //Make a request to the URL
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        String s = response.toString();
//                        Log.d("Response: ", s);
//                        tv.setText(s);
//
//                    }
//                }, new Response.ErrorListener(){
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("HTTP ERROR: ", error.toString());
//                    }
//                });
//
//        //Add the request to the queue
//        rq.add(request);
//
//       //END JSON request





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    } //end on create


    public void pushLocationToTv(View view){
        if(recentLocation != null){
            String temp = "Lat, long: " + recentLocation.getLatitude() + ", " +  recentLocation.getLongitude();
            tv.setText(temp);
            String foo = buildRequest();
            Log.d("String result: ",foo);
        }
    }

    public String buildRequest(){
        //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,relative_humidity_2m,precipitation_probability,visibility,cloud_cover,wind_speed_10m&timezone=auto&forecast_days=1
        String format = "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&hourly=temperature_2m,relative_humidity_2m,precipitation_probability,visibility,cloud_cover,wind_speed_10m&timezone=auto&forecast_days=1";
        return (recentLocation != null) ? String.format(format,recentLocation.getLatitude(),recentLocation.getLongitude()) : "Recent location is null.";
    }

    //Permission handling code

    //checks permissions
    private void checkAppPermissions(){
        int permission_result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission_result != PackageManager.PERMISSION_GRANTED){
            //permissions are not granted
            PERMISSION_STATE = false;
            //show explanation
            Log.d("checkAppPermissions","Permissions not yet granted.");
            showExplanation("Permission Needed", "Precise location is required to request weather data from weather service.", Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
            //permissions are granted
            Log.d("checkAppPermissions","Permissions already granted.");
            PERMISSION_STATE = true;
        }
    }

    //show permission rationale
    private void showExplanation(String title, String message, final String permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //request permissions here
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
                    }
                });
        builder.create().show();
    }

    //handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                Log.d("On request result", "Granted");
                PERMISSION_STATE = true;
            } else {
                //permission denied
                Log.d("On request result", "Denied");
//                showExplanation("Permission Needed", "Argument", permissions[0]);
            }
        }
        else{
            Log.d("Permission Callback Result", "Request codes did not match.");
        }
    }






    // end permission handling code








} // end class