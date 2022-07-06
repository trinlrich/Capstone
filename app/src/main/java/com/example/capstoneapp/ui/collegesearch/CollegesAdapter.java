package com.example.capstoneapp.ui.collegesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstoneapp.College;
import com.example.capstoneapp.R;
import com.parse.ParseFile;

import java.util.List;

public class CollegesAdapter extends RecyclerView.Adapter<CollegesAdapter.ViewHolder> {
    private static final String TAG = "PostsAdapter";

    private Context context;
    private List<College> colleges;

    public CollegesAdapter(Context context, List<College> colleges) {
        this.context = context;
        this.colleges = colleges;
    }

    @NonNull
    @Override
    public CollegesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_college, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegesAdapter.ViewHolder holder, int position) {
        College college = colleges.get(position);
        holder.bind(college);
    }

    @Override
    public int getItemCount() {
        return colleges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;
        private TextView tvName;
        private TextView tvAverageGpa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvAverageGpa = itemView.findViewById(R.id.tvAverageGpa);
        }

        public void bind(College college) {
            //Set college thumbnail
            ParseFile thumbnail = college.getThumbnail();
            if (thumbnail != null) {
                Glide.with(context)
                        .load(thumbnail.getUrl())
                        .into(ivThumbnail);
            } else {
                ivThumbnail.setImageResource(R.drawable.college_black_48);
            }

            tvName.setText(college.getCollegeName());
            tvAverageGpa.setText("Other Info");
        }
    }
}
