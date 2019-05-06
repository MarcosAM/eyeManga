package com.foolproductions.eyemanga.readActivity;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class CheckForRotationTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        if (source.getWidth() > source.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            source.recycle();
            return result;
        }
        return source;
    }

    @Override
    public String key() {
        return "rotation()";
    }
}
