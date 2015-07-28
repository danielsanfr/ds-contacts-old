package br.com.danielsan.dscontacts.adapters;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.dialogs.EditTextDialogFragment;

/**
 * Created by daniel on 28/07/15.
 */
public class FieldSpinnerAdapter extends ArrayAdapter<String> implements
        AdapterView.OnItemSelectedListener,
        EditTextDialogFragment.Listener {

    private int mTextsSize;
    private Spinner mSpinner;
    private FragmentActivity mFragmentActivity;

    private FieldSpinnerAdapter(FragmentActivity fragmentActivity, Spinner spinner, int resource, List<String> texts) {
        super(fragmentActivity, resource, texts);
        mSpinner = spinner;
        mTextsSize = texts.size();
        mFragmentActivity = fragmentActivity;
    }

    public static void configureAdapter(FragmentActivity fragmentActivity, Spinner spinner, @ArrayRes int arrayRes) {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(fragmentActivity.getResources().getStringArray(arrayRes)));

        FieldSpinnerAdapter arrayAdapter = new FieldSpinnerAdapter(fragmentActivity, spinner, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(arrayAdapter);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onInputText(CharSequence text) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) mSpinner.getAdapter();
        if (adapter.getCount() > mTextsSize)
            adapter.remove(adapter.getItem(0));

        adapter.insert(text.toString(), 0);
        mSpinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!(view instanceof TextView))
            return;

        String text = ((TextView) view).getText().toString();
        if (text.equals(mFragmentActivity.getString(R.string.custom))
            || text.equals(mFragmentActivity.getString(R.string.create_new_group))) {
            EditTextDialogFragment editTextDialogFragment =
            EditTextDialogFragment.newInstance(text);
            editTextDialogFragment.setOnEditTextDialogListener(this);
            editTextDialogFragment.show(mFragmentActivity.getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

}
