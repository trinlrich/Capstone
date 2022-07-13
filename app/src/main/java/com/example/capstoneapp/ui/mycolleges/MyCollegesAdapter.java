package com.example.capstoneapp.ui.mycolleges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.CollegesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCollegesAdapter extends RecyclerView.Adapter<MyCollegesAdapter.ViewHolder> {

    private static final String TAG = "MyCollegesAdapter";
    private Context context;
    private List<College> favColleges = new ArrayList<>();

    public void setFavColleges(List<College> favColleges) {
        this.favColleges = favColleges;
        notifyDataSetChanged();
    }

    public MyCollegesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favcollege, parent, false);
        return new MyCollegesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favColleges.get(position));
    }

    @Override
    public int getItemCount() {
        return favColleges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void bind(College favCollege) {
            textView.setText(favCollege.getName());
        }
    }
}
