package com.example.medicalzone;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class DBHelperUtils {
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
