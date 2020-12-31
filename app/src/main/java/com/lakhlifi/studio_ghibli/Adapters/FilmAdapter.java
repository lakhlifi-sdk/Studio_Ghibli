package com.lakhlifi.studio_ghibli.Adapters;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lakhlifi.studio_ghibli.Mabase;
import com.lakhlifi.studio_ghibli.Models.Film;
import com.lakhlifi.studio_ghibli.R;
import java.util.ArrayList;
import java.util.Collection;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {
    private Context context;
    private ArrayList<Film> list;
    private boolean isSelfLike=true;
    private ArrayList<Film> listAll;
    public FilmAdapter(Context context, ArrayList<Film> list) {
        this.context = context;
        this.list = list;
        this.listAll = new ArrayList<>(list);
    }
    @NonNull
    @Override
    public FilmAdapter.FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_arow,parent,false);
        return new FilmAdapter.FilmHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FilmHolder holder, int position) {
        Film film = list.get(position);
        holder.title_film.setText(film.getTitle());
        holder.txtFilmDesc.setText(film.getDescription());

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isSelfLike){
                    holder.btnLike.setImageResource(R.drawable.ic_favorite_red);
                    isSelfLike=false;
                    Mabase db= new Mabase(context);
                    db.addFilm(film);
                    db.getAllFilms();



                }else{
                    holder.btnLike.setImageResource(R.drawable.ic_like);
                    isSelfLike=true;
                }



            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Film> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(listAll);
            } else {
                for (Film film : listAll){
                    if(film.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())
                            || film.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
                            || film.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(film);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends Film>) results.values);
            notifyDataSetChanged();
        }
    };
    public Filter getFilter() {
        return filter;
    }

    class FilmHolder extends RecyclerView.ViewHolder{
        private TextView title_film,txtFilmDesc;
        private ImageButton btnLike;
        public FilmHolder(@NonNull View itemView) {
            super(itemView);
            title_film = itemView.findViewById(R.id.title_film);
            txtFilmDesc = itemView.findViewById(R.id.txtFilmDesc);
            btnLike = itemView.findViewById(R.id.btnFilmLike);
        }

    }
}
