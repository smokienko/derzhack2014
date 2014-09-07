package com.smokiyenko.burokrat.app;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by s.mokiyenko on 9/7/14.
 */
public class DocumentsCheckBoxAdapter implements ListAdapter {

    private final List<String> documentSteps;
    private final Activity activity;

    public DocumentsCheckBoxAdapter(final Activity activity, final List<String> documentSteps) {
        this.documentSteps = documentSteps;
        this.activity = activity;
    }


    private class ViewHolder {

        TextView code;
        CheckBox name;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return documentSteps.size();
    }

    @Override
    public String getItem(int position) {
        return documentSteps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.view_document_step, null);

        ViewHolder holder = new ViewHolder();
        holder.code = (TextView) convertView.findViewById(R.id.step_name);
        holder.name = (CheckBox) convertView.findViewById(R.id.step_checkBox);
        convertView.setTag(holder);

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();
                holder.name.setChecked(!holder.name.isChecked());
            }
        });

        String stepName = documentSteps.get(position);
        holder.code.setText(stepName);
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
}
