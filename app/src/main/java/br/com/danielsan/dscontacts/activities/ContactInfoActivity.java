package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.r0adkll.slidr.Slidr;

import br.com.danielsan.dscontacts.R;

/**
 * Created by daniel on 15/06/15.
 */
public class ContactInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_contact_info);
        Slidr.attach(this);

        mActionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Daniel San");
    }

    @Override
    protected int getMasterContainer() {
        return 0;
    }

    // A method to find height of the status bar
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = this.getResources().getDimensionPixelSize(resourceId);
        return result;
}

}
