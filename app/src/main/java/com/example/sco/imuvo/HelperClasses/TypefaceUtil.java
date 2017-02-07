package com.example.sco.imuvo.HelperClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by sco on 02.01.2017.
 */

public class TypefaceUtil {
    public static void overrideFont(Context context) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/ITCKRIST.TTF");
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField("SERIF");
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
        }
    }
}
