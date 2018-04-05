package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 4/4/18.
 */
// Delete this

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private OnItemClick callback;
    private Context context;
    private ArrayList<Subject> listSubject;
    LayoutInflater inflater;
    Subject subject;
    private SubjectAdapter subjectAdapter;
    private boolean isFromView = false;
    private ArrayList<Subject> selectedSubjects = new ArrayList<>();

    public SubjectAdapter(@NonNull Context context, int resource, @NonNull List<Subject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listSubject = (ArrayList<Subject>) objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subjectAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;
        if(row == null) {
            row = inflater.inflate(R.layout.spinner_helper,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = row.findViewById(R.id.text);
            viewHolder.mCheckBox = row.findViewById(R.id.checkbox);
            row.setTag(viewHolder);
        }
        else {
            selectedSubjects.clear();
            viewHolder = (ViewHolder) row.getTag();
        }
        subject = listSubject.get(position);

        viewHolder.mTextView.setText(subject.getTitle());

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callback = (OnItemClick) getContext();
                if(isChecked) {
                    //int getPosition = (Integer) buttonView.getTag();
                    listSubject.get(position).setSelected(true);
                    selectedSubjects.add(listSubject.get(position));
                    //callback.onClick(selectedSubjects);
                    Toast.makeText(getContext(),"value is"+listSubject.get(position).getTitle(),Toast.LENGTH_LONG ).show();
                }
                else {
                    listSubject.get(position).setSelected(false);
                    selectedSubjects.remove(listSubject.get(position));
                    //callback.onClick(selectedSubjects);
                }
                callback.onClick(selectedSubjects);
            }
        });
        isFromView = true;
        viewHolder.mCheckBox.setChecked(subject.isSelected());
        isFromView = false;

        return row;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    public interface OnItemClick {
        void onClick(ArrayList<Subject> subjectList);
    }

}
