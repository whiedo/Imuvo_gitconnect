package com.example.sco.imuvo.CustomViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SpinnerITCKRIST extends Spinner {
    public SpinnerITCKRIST(Context context) {
        super(context);
    }

    public SpinnerITCKRIST(Context context, int mode) {
        super(context, mode);
    }

    public SpinnerITCKRIST(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerITCKRIST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpinnerITCKRIST(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"fonts/ITCKRIST.TTF");
    }
}
