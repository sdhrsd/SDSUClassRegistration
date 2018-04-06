package com.herasiddiqui.sdsuclassregistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class SearchRecycleViewAdapter extends RecyclerView.Adapter<SearchRecycleViewAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<ClassDetails> classDetails;
    private ClassDetails populateCell;

    public SearchRecycleViewAdapter(Activity activity, ArrayList<ClassDetails> classDetail) {
        this.activity = activity;
        this.classDetails = classDetail;
        System.out.println("Class size is " +classDetails.size());
    }
    @Override
    public SearchRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.search_cell, parent, false);
        return new SearchRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchRecycleViewAdapter.ViewHolder holder, int position) {
        populateCell = new ClassDetails();
        populateCell = classDetails.get(position);
        String courseNoString = populateCell.getSubject() +"-"+populateCell.getCourseNo();
        holder.courseNo.setText(courseNoString);
        holder.scheduleNo.setText(populateCell.getScheduleNo());
        holder.title.setText(populateCell.getTitle());
        holder.units.setText(populateCell.getUnits());
        String timeString = populateCell.getStartTime() +"-" + populateCell.getEndTime();
        holder.time.setText(timeString);
        holder.day.setText(populateCell.getDays());
        holder.instructor.setText(populateCell.getInstructor());
        int seatsTally = populateCell.getSeats() - populateCell.getEnrolled();
        String seatsString = seatsTally +"/"+ populateCell.getSeats();
        holder.seats.setText(seatsString);
        if(populateCell.getSeats() - populateCell.getEnrolled() >0) {
            holder.addOrWait.setText(R.string.addS);
        }
        else {
            holder.addOrWait.setText(R.string.waitS);
        }
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
        private TextView seats;
        private Button details;
        private Button addOrWait;

        public ViewHolder(View view) {
            super(view);
            courseNo = view.findViewById(R.id.courseNoSearch);
            scheduleNo = view.findViewById(R.id.scheduleNoSearch);
            title = view.findViewById(R.id.titleSearch);
            units = view.findViewById(R.id.unitsSearch);
            time = view.findViewById(R.id.timeSearch);
            day = view.findViewById(R.id.daySearch);
            instructor = view.findViewById(R.id.instructorSearch);
            seats = view.findViewById(R.id.seatsSearch);
            details = view.findViewById(R.id.detailsButtonSearch);
            addOrWait = view.findViewById(R.id.addOrWaitButton);
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
            addOrWait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AddOrWait.class);
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
                    intent.putExtra("enrolled",classDetails.get(getAdapterPosition()).getEnrolled());
                    intent.putExtra("seats",classDetails.get(getAdapterPosition()).getSeats());
                    intent.putExtra("waitlist",classDetails.get(getAdapterPosition()).getWaitlist());
                    context.startActivity(intent);
                }
            });
        }
    }
}
