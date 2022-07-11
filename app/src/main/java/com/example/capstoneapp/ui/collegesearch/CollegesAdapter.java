package com.example.capstoneapp.ui.collegesearch;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.Utilities;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;

import java.util.List;

public class CollegesAdapter extends RecyclerView.Adapter<CollegesAdapter.ViewHolder> {
    private static final String TAG = "PostsAdapter";

    private Context context;
    private List<College> colleges;
    private Fragment fragment;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivThumbnail;
        private TextView tvName;
        private TextView tvLocation;
        private ImageButton ibtnFavorite;
        private TextView tvAverageGpa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            ibtnFavorite = itemView.findViewById(R.id.ibtnFavorite);
            tvAverageGpa = itemView.findViewById(R.id.tvAcceptanceRateTItle);
        }

        public void bind(College college) {
            Utilities.setViewImage(context, ivThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
            Utilities.setViewText(context, tvName, college.getName());
            Utilities.setViewText(context, tvLocation, college.getLocation());


            ibtnFavorite.setOnClickListener(this::onFavoriteClick);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "ViewHolder clicked!");
            int position = getAdapterPosition();
            Log.i(TAG, "onClick " + position);
            if (position != RecyclerView.NO_POSITION) {
                College college = colleges.get(position);

                Fragment fragment = CollegeDetailFragment.newInstance(college);
                Bundle args = new Bundle();
                args.putString(College.KEY_ID, college.getCollegeId());
                fragment.setArguments(args);

                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        public void onFavoriteClick(View v) {
            Toast.makeText(context, "Favorite clicked", Toast.LENGTH_SHORT).show();
            // TODO::Change image when button is clicked
        }
    }
}
