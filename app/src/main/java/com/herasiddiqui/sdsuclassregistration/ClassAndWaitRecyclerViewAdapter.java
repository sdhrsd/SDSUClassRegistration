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


public class ClassAndWaitRecyclerViewAdapter extends RecyclerView.Adapter<ClassAndWaitRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<ClassDetails> classDetails;
    private ClassDetails populateCell;

    public ClassAndWaitRecyclerViewAdapter(Activity activity, ArrayList<ClassDetails> classDetail) {
        this.activity = activity;
        this.classDetails = classDetail;
        System.out.println(classDetails.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.class_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populateCell = classDetails.get(position);
        holder.courseNo.setText(populateCell.getCourseNo());
        holder.scheduleNo.setText(populateCell.getScheduleNo());
        holder.title.setText(populateCell.getTitle());
        holder.units.setText(populateCell.getUnits());
        String timeString = populateCell.getStartTime() +"-" + populateCell.getEndTime();
        holder.time.setText(timeString);
        String locationString = populateCell.getBuilding() +"-" + populateCell.getRoom();
        holder.location.setText(locationString);
        holder.day.setText(populateCell.getDays());
        holder.instructor.setText(populateCell.getInstructor());
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
                    //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CompleteClassDetails.class);
                    intent.putExtra("courseId",classDetails.get(getAdapterPosition()).getId());
                    intent.putExtra("courseNo",classDetails.get(getAdapterPosition()).getCourseNo());
                    intent.putExtra("scheduleNo",classDetails.get(getAdapterPosition()).getScheduleNo());
                    intent.putExtra("fullTitle",classDetails.get(getAdapterPosition()).getFullTitle());
                    intent.putExtra("title",classDetails.get(getAdapterPosition()).getTitle());
                    intent.putExtra("building",classDetails.get(getAdapterPosition()).getBuilding());
                    intent.putExtra("room",classDetails.get(getAdapterPosition()).getRoom());
                    intent.putExtra("start",classDetails.get(getAdapterPosition()).getStartTime());
                    intent.putExtra("end",classDetails.get(getAdapterPosition()).getEndTime());
                    intent.putExtra("department",classDetails.get(getAdapterPosition()).getDepartment());
                    intent.putExtra("days",classDetails.get(getAdapterPosition()).getDays());
                    intent.putExtra("subject",classDetails.get(getAdapterPosition()).getSubject());
                    intent.putExtra("units",classDetails.get(getAdapterPosition()).getUnits());
                    intent.putExtra("instructor",classDetails.get(getAdapterPosition()).getInstructor());
                    intent.putExtra("meetingType",classDetails.get(getAdapterPosition()).getMeetingType());
                    intent.putExtra("section",classDetails.get(getAdapterPosition()).getSection());
                    intent.putExtra("prerequisite",classDetails.get(getAdapterPosition()).getPrerequisite());
                    intent.putExtra("description",classDetails.get(getAdapterPosition()).getDescription());
                    intent.putExtra("seats",classDetails.get(getAdapterPosition()).getSeats());
                    intent.putExtra("enrolled",classDetails.get(getAdapterPosition()).getEnrolled());
                    context.startActivity(intent);
                }
            });
            drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Drop.class);
                    intent.putExtra("courseId",classDetails.get(getAdapterPosition()).getId());
                    intent.putExtra("courseNo",classDetails.get(getAdapterPosition()).getCourseNo());
                    intent.putExtra("scheduleNo",classDetails.get(getAdapterPosition()).getScheduleNo());
                    intent.putExtra("fullTitle",classDetails.get(getAdapterPosition()).getFullTitle());
                    intent.putExtra("building",classDetails.get(getAdapterPosition()).getBuilding());
                    intent.putExtra("room",classDetails.get(getAdapterPosition()).getRoom());
                    intent.putExtra("start",classDetails.get(getAdapterPosition()).getStartTime());
                    intent.putExtra("end",classDetails.get(getAdapterPosition()).getEndTime());
                    intent.putExtra("department",classDetails.get(getAdapterPosition()).getDepartment());
                    intent.putExtra("days",classDetails.get(getAdapterPosition()).getDays());
                    intent.putExtra("subject",classDetails.get(getAdapterPosition()).getSubject());
                    intent.putExtra("units",classDetails.get(getAdapterPosition()).getUnits());
                    intent.putExtra("instructor",classDetails.get(getAdapterPosition()).getInstructor());
                    intent.putExtra("enrolled",classDetails.get(getAdapterPosition()).isStudentEnrolled());
                    context.startActivity(intent);
                }
            });
        }
    }
}

