package com.lakhlifi.studio_ghibli.Fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lakhlifi.studio_ghibli.R;

public class FavoriteFragment extends Fragment {
    private  View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_layout,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
