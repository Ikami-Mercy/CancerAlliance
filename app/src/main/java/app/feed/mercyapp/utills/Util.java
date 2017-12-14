package app.feed.mercyapp.utills;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Mercy.Ikami on 12/2/2017.
 */

public class Util {

    public static void compressInputImage(Intent data, Context context, ImageView newIV) {
        Bitmap bitmap;
        Uri inputImageData = data.getData();
        try {
            Bitmap bitmapInputImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), inputImageData);
            if (bitmapInputImage.getWidth() > 2048 && bitmapInputImage.getHeight() > 2048) {
                bitmap = Bitmap.createScaledBitmap(bitmapInputImage, 1024, 1280, true);
                newIV.setImageBitmap(bitmap);
            } else if (bitmapInputImage.getWidth() > 2048 && bitmapInputImage.getHeight() < 2048) {
                bitmap = Bitmap.createScaledBitmap(bitmapInputImage, 1920, 1200, true);
                newIV.setImageBitmap(bitmap);
            } else if (bitmapInputImage.getWidth() < 2048 && bitmapInputImage.getHeight() > 2048) {
                bitmap = Bitmap.createScaledBitmap(bitmapInputImage, 1024, 1280, true);
                newIV.setImageBitmap(bitmap);
            } else if (bitmapInputImage.getWidth() < 2048 && bitmapInputImage.getHeight() < 2048) {
                bitmap = Bitmap.createScaledBitmap(bitmapInputImage, bitmapInputImage.getWidth(), bitmapInputImage.getHeight(), true);
                newIV.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public static void writeLog(String name, String msg){
        Log.e(name, "===================================================================+++++++++++++++++++==");
        Log.e(name, msg);
        Log.e(name, "===================================================================+++++++++++++++++++==");

    }


    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
