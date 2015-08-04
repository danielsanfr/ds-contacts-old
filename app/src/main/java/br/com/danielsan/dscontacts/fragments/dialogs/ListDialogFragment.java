package br.com.danielsan.dscontacts.fragments.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;

import java.util.ArrayList;

public class ListDialogFragment extends DialogFragment {

    private static final String TITLE = "title";
    private static final String TITLE_RES = "title_res";
    private static final String ITEMS = "items";
    private static final String ITEMS_RES = "items_res";
    private static final String ITEMS_LIST = "items_list";

    public static ListDialogFragment newInstance(@StringRes int titleRes, @ArrayRes int itemsRes) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES, titleRes);
        bundle.putInt(ITEMS_RES, itemsRes);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ListDialogFragment newInstance(@StringRes int titleRes, String[] items) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES, titleRes);
        bundle.putStringArray(ITEMS, items);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ListDialogFragment newInstance(@StringRes int titleRes, ArrayList<String> itemsList) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES, titleRes);
        bundle.putStringArrayList(ITEMS_LIST, itemsList);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ListDialogFragment newInstance(String title, @ArrayRes int itemsRes) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(ITEMS_RES, itemsRes);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ListDialogFragment newInstance(String title, String[] items) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putStringArray(ITEMS, items);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ListDialogFragment newInstance(String title, ArrayList<String> itemsList) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putStringArrayList(ITEMS_LIST, itemsList);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public ListDialogFragment() { /* Required empty public constructor */ }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof DialogInterface.OnClickListener))
            throw new ClassCastException(activity.toString() + " must implement DialogInterface.OnClickListener.");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            if (arguments.containsKey(TITLE_RES))
                builder.setTitle(arguments.getInt(TITLE_RES));
            else if (arguments.containsKey(TITLE))
                builder.setTitle(arguments.getString(TITLE));

            String[] items = null;
            if (arguments.containsKey(ITEMS_RES))
                items = this.getActivity().getResources().getStringArray(arguments.getInt(ITEMS_RES));
            else if (arguments.containsKey(ITEMS))
                items = arguments.getStringArray(ITEMS);
            else if (arguments.containsKey(ITEMS_LIST)) {
                ArrayList<String> itemsList = arguments.getStringArrayList(ITEMS_LIST);
                if (itemsList != null) {
                    items = new String[itemsList.size()];
                    items = itemsList.toArray(items);
                }
            }

            builder.setItems(items, (DialogInterface.OnClickListener) this.getActivity());
        }

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setCancelable(true);
        return builder.create();
    }

}