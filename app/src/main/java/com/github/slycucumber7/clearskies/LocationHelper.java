package com.github.slycucumber7.clearskies;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationHelper {
    Context context;
    LocationManager lm;
    Location location;
    double lattitude, longitude;


    //You must check whether you have permissions every time you
    //perform an operation that requires that permission

    public LocationHelper(Context context) {
        this.context = context;


    }



    public double[] getLocation() {
        return new double[]{lattitude, longitude};
    }

}
