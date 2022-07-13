package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.FavoriteCollege;


import java.util.List;

public interface GetFavCollegesCallback {
    void onCompleted(List<FavoriteCollege> favoriteColleges, Boolean error);
}

