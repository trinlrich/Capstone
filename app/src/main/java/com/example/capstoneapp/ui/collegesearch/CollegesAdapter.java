package com.example.capstoneapp.ui.collegesearch;

import android.content.Context;
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

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class CollegesAdapter extends RecyclerView.Adapter<CollegesAdapter.ViewHolder> {

    interface FavoriteButtonClickedCallback {
        void onFavButtonClicked(College college);
    }
    private static final String TAG = "CollegesAdapter";

    private Context context;

    public void setColleges(List<College> colleges) {
        this.colleges = colleges;
        notifyDataSetChanged();
    }

    private List<College> colleges = new ArrayList<>();
    private final FavoriteButtonClickedCallback favButtonClickedCallback;

    public CollegesAdapter(Context context, FavoriteButtonClickedCallback favButtonClickedCallback) {
        this.context = context;
        this.favButtonClickedCallback = favButtonClickedCallback;
    }

    @NonNull
    @Override
    public CollegesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_college, parent, false);
        return new ViewHolder(view,favButtonClickedCallback);
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

        private final ImageView ivThumbnail;
        private final TextView tvName;
        private final TextView tvLocation;
        private final TextView tvCollegeType;
        private final TextView tvAvgCost;
        private final ImageButton ibtnFavorite;
        private final FavoriteButtonClickedCallback favButtonClickedCallback;

        public ViewHolder(@NonNull View itemView, FavoriteButtonClickedCallback favBtnClickedCallback) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvNameItem);
            tvLocation = itemView.findViewById(R.id.tvLocationItem);
            tvCollegeType = itemView.findViewById(R.id.tvCollegeTypeItem);
            tvAvgCost = itemView.findViewById(R.id.tvAvgCostItem);
            ibtnFavorite = itemView.findViewById(R.id.ibtnFavorite);
            favButtonClickedCallback = favBtnClickedCallback;
        }

        public void bind(College college) {
            UiUtils.setViewImage(context, ivThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
            UiUtils.setViewText(context, tvName, college.getName());
            UiUtils.setViewText(context, tvLocation, college.getLocation());
            UiUtils.setViewText(context, tvCollegeType, college.getCollegeTypeAsText());
            UiUtils.setViewText(context, tvAvgCost, college.getAvgCostAsText());

            if (college.isFavorite())
                ibtnFavorite.setBackground(context.getDrawable(R.drawable.favorite_black_48));
            else
                ibtnFavorite.setBackground(context.getDrawable(R.drawable.favorite_border_black_48));

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
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        public void onFavoriteClick(View v) {
            Log.i(TAG, "Favorite clicked");
            favButtonClickedCallback.onFavButtonClicked(colleges.get(getAdapterPosition()));
        }
    }
}
