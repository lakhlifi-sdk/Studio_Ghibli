package com.lakhlifi.studio_ghibli.Fragements;

import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lakhlifi.studio_ghibli.Adapters.FilmAdapter;
import com.lakhlifi.studio_ghibli.Constants;
import com.lakhlifi.studio_ghibli.Models.Film;
import com.lakhlifi.studio_ghibli.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static ArrayList<Film> arrayList;
    private SwipeRefreshLayout refreshLayout;
    private FilmAdapter filmsAdapter;
    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_home,container,false);
        init();
        return view;
    }

    private void init(){
        recyclerView=view.findViewById(R.id.recyclerHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        refreshLayout = view.findViewById(R.id.swipeHome);
        setHasOptionsMenu(true);
        getFilms();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFilms();
            }
        });
    }

    private void getFilms() {
        arrayList = new ArrayList<>();
        refreshLayout.setRefreshing(true);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constants.URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0 ;  i<response.length();i++){
                        JSONObject filmObject= response.getJSONObject(i);
                        Film film = new Film();
                        film.setId(filmObject.getString("id"));
                        film.setDescription(filmObject.getString("description"));
                        film.setTitle(filmObject.getString("title"));
                        film.setDirector(filmObject.getString("director"));
                        film.setRelease_date(filmObject.getString("release_date"));
                        film.setProducer(filmObject.getString("producer"));
                        film.setLocations(filmObject.getString("locations"));
                        film.setRt_score(filmObject.getString("rt_score"));
                        film.setSpecies(filmObject.getString("species"));
                        film.setPeople(filmObject.getString("people"));
                        film.setUrl(filmObject.getString("url"));
                        System.out.println(" Film object--->"+film);
                        arrayList.add(film);
                        refreshLayout.setRefreshing(false);
                    }

                    filmsAdapter = new FilmAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(filmsAdapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                    refreshLayout.setRefreshing(false);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                refreshLayout.setRefreshing(false);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.search);
        Drawable drawable = menu.getItem(0).getIcon();
        drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filmsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
