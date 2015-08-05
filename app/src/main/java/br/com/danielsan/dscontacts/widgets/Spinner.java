package br.com.danielsan.dscontacts.widgets;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 04/08/15.
 */
public class Spinner extends AppCompatSpinner implements AdapterView.OnItemClickListener {

    private static final String ATTRIBUTE_SPINNER_MODE = "spinnerMode";
    private static final String ANDROID_NAMESPACE_ATTRIBUTES = "http://schemas.android.com/apk/res/android";

    private int mDividerHeight;
    private int mSpinnerMode;
    private boolean mCancelable;
    private Context mContext;
    private WrapperListAdapter mWrapperListAdapter;

    private PopupWindow mPopupWindow;
    private AlertDialog mAlertDialog;

    public Spinner(Context context) {
        super(context);
        this.init(context, null);
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;

        mContext = context;
        mCancelable = true;
        mSpinnerMode = attrs.getAttributeIntValue(ANDROID_NAMESPACE_ATTRIBUTES, ATTRIBUTE_SPINNER_MODE, MODE_DIALOG);
    }

    private void apply(Configuration configuration) {
        mDividerHeight = configuration.height;
    }

    @Override
    public boolean performClick() {
        if (mWrapperListAdapter == null)
            return true;

        if (mSpinnerMode == MODE_DROPDOWN)
            this.showDropdown();
        else if (mSpinnerMode == MODE_DIALOG)
            this.showDialog();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.setSelection(i, true);
        if (mCancelable) {
            if (mSpinnerMode == MODE_DROPDOWN)
                mPopupWindow.dismiss();
            else if (mSpinnerMode == MODE_DIALOG)
                mAlertDialog.dismiss();

            mPopupWindow = null;
            mAlertDialog = null;
        }
        if (this.getOnItemSelectedListener() != null)
            this.getOnItemSelectedListener().onItemSelected(this, view, i, l);
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        try {
            mWrapperListAdapter = new WrapperListAdapter((BaseAdapter) adapter);
            super.setAdapter(adapter);
        } catch (ClassCastException e) {
            throw new ClassCastException(adapter.toString() + " must extend BaseAdapter.");
        }
    }

    private ListView createList() {
        ListView listView = new ListView(mContext);
        if (mWrapperListAdapter != null) {
            listView.setAdapter(mWrapperListAdapter);
            listView.setOnItemClickListener(this);
            listView.setDividerHeight(mDividerHeight);
        }

        return listView;
    }

    private void showDropdown() {
        mPopupWindow = new PopupWindow(this.createList(), this.getWidth(), this.getWidth(), true);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
        PopupWindowCompat.showAsDropDown(mPopupWindow, this, 0, 0, GravityCompat.START);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setView(this.createList());
        mAlertDialog = builder.show();
    }

    private static class WrapperListAdapter implements ListAdapter {
        private BaseAdapter mBaseAdapter;
        public WrapperListAdapter(BaseAdapter baseAdapter) {
            mBaseAdapter = baseAdapter;
        }
        @Override
        public boolean areAllItemsEnabled() {
            return mBaseAdapter.areAllItemsEnabled();
        }
        @Override
        public boolean isEnabled(int i) {
            return mBaseAdapter.isEnabled(i);
        }
        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            mBaseAdapter.registerDataSetObserver(dataSetObserver);
        }
        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            mBaseAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        @Override
        public int getCount() {
            return mBaseAdapter.getCount();
        }
        @Override
        public Object getItem(int i) {
            return mBaseAdapter.getItem(i);
        }
        @Override
        public long getItemId(int i) {
            return mBaseAdapter.getItemId(i);
        }
        @Override
        public boolean hasStableIds() {
            return mBaseAdapter.hasStableIds();
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return mBaseAdapter.getDropDownView(i, view, viewGroup);
        }
        @Override
        public int getItemViewType(int i) {
            return mBaseAdapter.getItemViewType(i);
        }
        @Override
        public int getViewTypeCount() {
            return mBaseAdapter.getViewTypeCount();
        }
        @Override
        public boolean isEmpty() {
            return mBaseAdapter.isEmpty();
        }
    }

    public static class Configuration {
        private int height;
        private boolean cancelable;
        private BaseAdapter baseAdapter;
        private List<View> headerViewList;
        private List<View> footerViewList;
        public Configuration() {
            cancelable = true;
            headerViewList = new ArrayList<>();
            footerViewList = new ArrayList<>();
        }
        public Configuration setDividerHeight(int height) {
            this.height = height;
            return this;
        }
        public Configuration setAdapter(@NonNull BaseAdapter baseAdapter) {
            this.baseAdapter = baseAdapter;
            return this;
        }
        public Configuration addHeraderView(@NonNull View view) {
            headerViewList.add(view);
            return this;
        }
        public Configuration addFooterView(@NonNull View view) {
            footerViewList.add(view);
            return this;
        }
        public Configuration setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }
    }
    
}
