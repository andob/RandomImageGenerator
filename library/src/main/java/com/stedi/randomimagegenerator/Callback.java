package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

public interface Callback {
    void onGenerated(ImageParams imageParams, Bitmap bitmap);
    void onException(ImageParams imageParams, Exception ex);
}
