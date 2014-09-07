package com.smokiyenko.burokrat.app;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class DocumentsAdapter implements ListAdapter {

    private final Activity context;
    private ArrayList<String> documentNames;

    public DocumentsAdapter(final Activity context, final ArrayList<String> documentNames) {
        this.documentNames = documentNames;
        this.context = context;
    }



    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return documentNames.size();
    }

    @Override
    public Object getItem(int position) {
        return documentNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.view_document,null);
        }
        ((TextView) convertView.findViewById(R.id.document_name)).setText(documentNames.get(position));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
