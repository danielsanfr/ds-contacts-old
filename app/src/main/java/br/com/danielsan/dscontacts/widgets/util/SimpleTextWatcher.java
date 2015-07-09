package br.com.danielsan.dscontacts.widgets.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by daniel on 09/07/15.
 */
public class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) { }

}
