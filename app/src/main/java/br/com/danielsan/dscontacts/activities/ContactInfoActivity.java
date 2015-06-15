package br.com.danielsan.dscontacts.activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.r0adkll.slidr.Slidr;

import br.com.danielsan.dscontacts.R;

/**
 * Created by daniel on 15/06/15.
 */
public class ContactInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_contact_info);
        Slidr.attach(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getMasterContainer() {
        return 0;
    }
}
