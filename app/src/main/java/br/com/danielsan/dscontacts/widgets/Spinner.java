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
    private List<View> mHeaderViewList;
    private List<View> mFooterViewList;
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
        mDividerHeight = 0;
        mHeaderViewList = null;
        mFooterViewList = null;
        mWrapperListAdapter = new WrapperListAdapter();
        mSpinnerMode = attrs.getAttributeIntValue(ANDROID_NAMESPACE_ATTRIBUTES, ATTRIBUTE_SPINNER_MODE, MODE_DROPDOWN);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mCancelable)
            this.dismiss();

        int index = (mHeaderViewList == null) ? position : (position - mHeaderViewList.size());
        if (index < mWrapperListAdapter.getCount())
            this.setSelection(index, true);
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        try {
            mWrapperListAdapter.setBaseAdapter((BaseAdapter) adapter);
            super.setAdapter(adapter);
        } catch (ClassCastException e) {
            throw new ClassCastException(adapter.toString() + " must extend BaseAdapter.");
        }
    }

    public void dismiss() {
        if (mSpinnerMode == MODE_DROPDOWN && mPopupWindow != null)
            mPopupWindow.dismiss();
        else if (mSpinnerMode == MODE_DIALOG && mAlertDialog != null)
            mAlertDialog.dismiss();

        mPopupWindow = null;
        mAlertDialog = null;
    }

    public Configurator getConfigurator() {
        return new Configurator(this);
    }

    private ListView createList() {
        ListView listView = new ListView(mContext);

        if (mHeaderViewList != null) {
            for (View view : mHeaderViewList)
                listView.addHeaderView(view);
        }
        if (mFooterViewList != null) {
            for (View view : mFooterViewList)
                listView.addFooterView(view);
        }

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
        public void setBaseAdapter(@NonNull BaseAdapter baseAdapter) {
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

    public static class Configurator {
        private Spinner mSpinner;
        private int dividerHeight;
        private boolean cancelable;
        private BaseAdapter baseAdapter;
        private List<View> headerViewList;
        private List<View> footerViewList;
        private OnClickListener headerOnClickListener;
        private OnClickListener footerOnClickListener;
        private Configurator(Spinner spinner) {
            mSpinner = spinner;
            dividerHeight = 0;
            cancelable = true;
            baseAdapter = null;
            headerViewList = new ArrayList<>();
            footerViewList = new ArrayList<>();
            headerOnClickListener = null;
            footerOnClickListener = null;
        }
        public Configurator setDividerHeight(int height) {
            this.dividerHeight = height;
            return this;
        }
        public Configurator setAdapter(@NonNull BaseAdapter baseAdapter) {
            this.baseAdapter = baseAdapter;
            return this;
        }
        public Configurator addHeaderView(@NonNull View view) {
            headerViewList.add(view);
            return this;
        }
        public Configurator addFooterView(@NonNull View view) {
            footerViewList.add(view);
            return this;
        }
        public Configurator setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }
        public Configurator setHeaderOnClickListener(OnClickListener onClickListener) {
            this.headerOnClickListener = onClickListener;
            return this;
        }
        public Configurator setFooterOnClickListener(OnClickListener onClickListener) {
            this.footerOnClickListener = onClickListener;
            return this;
        }
        //        public Configurator setMultiChoiceItems(@ArrayRes int items) {
//            this.setCancelable(false);
//            this.setAdapter(baseAdapter);
//            return this;
//        }
//        public Configurator setMultiChoiceItems(@ArrayRes int items) {
//            this.setCancelable(false);
//            this.setAdapter(baseAdapter);
//            return this;
//        }
        public void apply() {
            this.setListeners(headerViewList, headerOnClickListener);
            this.setListeners(footerViewList, footerOnClickListener);
            mSpinner.dismiss();
            mSpinner.mCancelable = cancelable;
            mSpinner.mDividerHeight = dividerHeight;
            mSpinner.mHeaderViewList = headerViewList;
            mSpinner.mFooterViewList = footerViewList;
            if (baseAdapter != null)
                mSpinner.mWrapperListAdapter.setBaseAdapter(baseAdapter);
        }
        private void setListeners(List<View> viewList, OnClickListener onClickListener) {
            if (viewList != null) {
                for (View view : viewList)
                    view.setOnClickListener(onClickListener);
            }
        }
    }
    
}
