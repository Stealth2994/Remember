package com.remember.alpha;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture {
    public static String picName;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private static String folderPath;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    Activity launchActivity;
    public TakePicture(String picName, String folderPath, Activity launchActivity) {
        this.folderPath = folderPath;
        this.picName = picName;
        this.launchActivity = launchActivity;
    }
    public void capturePicture() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name


        // start the image capture Intent
        launchActivity.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
     File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Remember/" + TakePicture.folderPath);
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Remember", "failed to create directory");
                return null;
            }
        }
        // Create the storage directory if it does not exist


        // Create a media file name
     /*   String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());*/

        File mediaFile;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (type == MEDIA_TYPE_IMAGE){
            Log.i("TakePicture",mediaStorageDir.getPath() + File.separator +
                    TakePicture.picName + timeStamp + ".jpg");
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    TakePicture.picName + timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    TakePicture.picName + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void refreshActivity(Context c,Class activity) {
        Intent intent = new Intent(c,activity);
        c.startActivity(intent);
    }
}


