package com.example.sco.imuvo.HelperClasses;

import android.content.Context;
import android.graphics.Typeface;
import java.lang.reflect.Field;

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
