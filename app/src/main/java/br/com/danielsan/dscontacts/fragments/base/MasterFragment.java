package br.com.danielsan.dscontacts.fragments.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import br.com.danielsan.dscontacts.activities.BaseActivity;

/**
 * Created by daniel on 10/06/15.
 */
public class MasterFragment extends BFragment {

    public MasterFragment() {}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setPaddingForTranslucent(view);
    }

    private void setPaddingForTranslucent(View view) {
        MasterFragment.setPaddingForTranslucent(mBaseActivity, view);
    }

    public static void setPaddingForTranslucent(BaseActivity baseActivity, View view) {
//        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT)
//            return;
//        SystemBarTintManager.SystemBarConfig config = baseActivity.getSystemBarTintManager().getConfig();
//        view.setPadding(0, config.getPixelInsetTop(true) + 16, 0, config.getPixelInsetBottom());
    }

}
