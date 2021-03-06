package com.example.capstoneapp.ui.mycolleges;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.mycolleges.mycollegedetail.MyCollegeDetailFragment;
import com.google.firebase.auth.FirebaseAuth;


import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyCollegesAdapter extends RecyclerView.Adapter<MyCollegesAdapter.ViewHolder> {

    interface FavoriteButtonClickedCallback {
        void onFavButtonClicked(College college);
    }

    private static final String TAG = "MyCollegesAdapter";
    private final Context context;
    private List<College> favColleges = new ArrayList<>();
    private Map<Integer, List<CollegeApplicationTask>> collegeApplicationTasksMap = new HashMap<>();
    private final FavoriteButtonClickedCallback favButtonClickedCallback;

    public void setFavColleges(List<College> favColleges ,Map<Integer, List<CollegeApplicationTask>> collegeAppTasksMap) {
        this.favColleges = favColleges;
        this.collegeApplicationTasksMap = collegeAppTasksMap;
        notifyDataSetChanged();
    }

    public MyCollegesAdapter(Context context, FavoriteButtonClickedCallback favButtonClickedCallback) {
        this.context = context;
        this.favButtonClickedCallback = favButtonClickedCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favcollege, parent, false);
        return new MyCollegesAdapter.ViewHolder(view, favButtonClickedCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        College currentCollege = favColleges.get(position);
        holder.bind(currentCollege,collegeApplicationTasksMap.get(currentCollege.getCollegeId()));
    }

    @Override
    public int getItemCount() {
        return favColleges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivFavCollegeThumbnail;
        private final TextView tvFavCollegeName;
        private final TextView tvFavCollegeLocation;
        private final ImageButton ibtnFavorite;
        private final FavoriteButtonClickedCallback favButtonClickedCallback;
        private TaskProgressView taskProgressView;

        public ViewHolder(@NonNull View itemView, FavoriteButtonClickedCallback favBtnClickedCallback) {
            super(itemView);
            ivFavCollegeThumbnail = itemView.findViewById(R.id.ivFavCollegeThumbnail);
            tvFavCollegeName = itemView.findViewById(R.id.tvFavCollegeName);
            tvFavCollegeLocation = itemView.findViewById(R.id.tvFavCollegeLocation);
            ibtnFavorite = itemView.findViewById(R.id.ibtnFavorite);
            taskProgressView = itemView.findViewById(R.id.task_progress_view);
            favButtonClickedCallback = favBtnClickedCallback;
        }

        public void bind(College favCollege, List<CollegeApplicationTask> appTasks) {
            UiUtils.setViewImage(context, ivFavCollegeThumbnail, favCollege.getThumbnail(), null, R.drawable.college_black_48);
            UiUtils.setViewText(context, tvFavCollegeName, favCollege.getName());
            UiUtils.setViewText(context, tvFavCollegeLocation, favCollege.getLocation());
            taskProgressView.setTasks(appTasks);
            if (favCollege.isFavorite())
                ibtnFavorite.setBackground(context.getDrawable(R.drawable.favorite_black_48));
            else
                ibtnFavorite.setBackground(context.getDrawable(R.drawable.favorite_border_black_48));


            ibtnFavorite.setOnClickListener(this::onFavoriteClick);
            itemView.setOnClickListener(this::onClick);
        }

        public void onFavoriteClick(View v) {
            Log.i(TAG, "Favorite clicked");
            favButtonClickedCallback.onFavButtonClicked(favColleges.get(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "MyCollege ViewHolder clicked!");
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                College college = favColleges.get(position);
                String firebaseUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                Fragment fragment = MyCollegeDetailFragment.newInstance(firebaseUid,college.getCollegeId(), college);
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
