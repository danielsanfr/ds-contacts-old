package br.com.danielsan.dscontacts.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by daniel on 22/06/15.
 */
public class Metrics {

    /*
        Thanks: http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
