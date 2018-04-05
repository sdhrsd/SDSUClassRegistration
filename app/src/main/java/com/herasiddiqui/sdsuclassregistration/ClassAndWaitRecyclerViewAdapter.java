package com.herasiddiqui.sdsuclassregistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by admin on 4/2/18.
 */

public class ClassAndWaitRecyclerViewAdapter extends RecyclerView.Adapter<ClassAndWaitRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<ClassDetails> classDetails;
    private ClassDetails populateCell;

    public ClassAndWaitRecyclerViewAdapter(Activity activity, ArrayList<ClassDetails> classDetail) {
        //Log.i("yahan hain","CONSTRUCTOR CALL");
        this.activity = activity;
        this.classDetails = classDetail;
        System.out.println(classDetails.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.class_cell, parent, false);
        //Log.i("yahan hain","Viewholder creation");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populateCell = classDetails.get(position);
        holder.courseNo.setText(populateCell.getCourseNo());
        holder.scheduleNo.setText(populateCell.getScheduleNo());
        holder.title.setText(populateCell.getTitle());
        holder.units.setText(populateCell.getUnits());
        holder.time.setText(populateCell.getStartTime() +"-" + populateCell.getEndTime());
        holder.location.setText(populateCell.getBuilding() +"-" + populateCell.getRoom());
        holder.day.setText(populateCell.getDays());
        holder.instructor.setText(populateCell.getInstructor());
        //Log.i("yahan hain","Async issue");
    }

    @Override
    public int getItemCount() {
        return classDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNo;
        private TextView scheduleNo;
        private TextView title;
        private TextView units;
        private TextView time;
        private TextView location;
        private TextView day;
        private TextView instructor;
        private Button details;
        private Button drop;

        public ViewHolder(View view) {
            super(view);
            courseNo = view.findViewById(R.id.courseNo);
            scheduleNo = view.findViewById(R.id.scheduleNo);
            title = view.findViewById(R.id.title);
            units = view.findViewById(R.id.units);
            time = view.findViewById(R.id.time);
            day = view.findViewById(R.id.day);
            instructor = view.findViewById(R.id.instructor);
            location = view.findViewById(R.id.location);
            details = view.findViewById(R.id.detailsButton);
            drop = view.findViewById(R.id.dropButton);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CompleteClassDetails.class);
                    intent.putExtra("courseId",populateCell.getId());
                    intent.putExtra("enrolled",populateCell.isStudentEnrolled());
                    context.startActivity(intent);
                }
            });
            drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Drop.class);
                    intent.putExtra("courseId",populateCell.getId());
                    intent.putExtra("courseNo",populateCell.getCourseNo());
                    intent.putExtra("scheduleNo",populateCell.getScheduleNo());
                    intent.putExtra("fullTitle",populateCell.getFullTitle());
                    intent.putExtra("building",populateCell.getBuilding());
                    intent.putExtra("room",populateCell.getRoom());
                    intent.putExtra("start",populateCell.getStartTime());
                    intent.putExtra("end",populateCell.getEndTime());
                    intent.putExtra("department",populateCell.getDepartment());
                    intent.putExtra("days",populateCell.getDays());
                    intent.putExtra("subject",populateCell.getSubject());
                    intent.putExtra("units",populateCell.getUnits());
                    intent.putExtra("instructor",populateCell.getInstructor());
                    intent.putExtra("enrolled",populateCell.isStudentEnrolled());
                    context.startActivity(intent);
                }
            });
        }
    }
}

