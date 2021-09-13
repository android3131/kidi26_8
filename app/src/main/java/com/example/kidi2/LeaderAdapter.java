package com.example.kidi2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> implements Filterable {
    Context context;
    private List<Leader> leader_list;
    private List<Leader> FullList;


    public LeaderAdapter(List<Leader> leader_list) {
        //this.context=context;
        this.leader_list = leader_list;

        FullList = new ArrayList<>(leader_list);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (leader_list != null && leader_list.size() > 0) {
            Leader currentItem = leader_list.get(position);
            holder.username_tv.setText(currentItem.getFullName());
            int age=getAge(currentItem.getDateOfBirth());
            holder.age_tv.setText(String.valueOf(age));

            //  Date diffDate = new Date(diff);

            holder.age_tv.setText("14");
            holder.active_tv.setText(currentItem.getActiveStatus().toString());
            holder.type_tv.setText("leader");
            holder.active_date_tv.setText(currentItem.getActiveDate().toString());
            holder.v.setBackgroundColor(currentItem.getClr());
        }
    }

    @Override
    public int getItemCount() {

        return leader_list.size();
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username_tv, type_tv, age_tv, active_tv, active_date_tv;
        TableRow v;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username_tv = itemView.findViewById(R.id.username_tv);
            type_tv = itemView.findViewById(R.id.type_tv);
            age_tv = itemView.findViewById(R.id.age_tv);
            active_tv = itemView.findViewById(R.id.active_tv);
            active_date_tv = itemView.findViewById(R.id.active_date_tv);
            v=itemView.findViewById(R.id.table);
        }
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Leader> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Leader item : FullList) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            leader_list.clear();
            leader_list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public static int getAge(Date date) {
        if (date != null) {
            int age = 0;
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }

            return age;
        }
        return 0;
    }

}
