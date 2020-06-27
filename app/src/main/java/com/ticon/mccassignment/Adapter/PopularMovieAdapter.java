package com.ticon.mccassignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ticon.mccassignment.Interface.ClickEventListener;
import com.ticon.mccassignment.Model.PopularMovieListData;
import com.ticon.mccassignment.Model.PopularMovieListModel;
import com.ticon.mccassignment.R;
import com.ticon.mccassignment.Utils.Utils;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {
    private List<PopularMovieListData> popularMovieList;
    private Context mContext;
    View view;
    ClickEventListener clickEventListener;

    public PopularMovieAdapter(Context mContext, List<PopularMovieListData> popularMovieList,ClickEventListener clickEventListener) {
        this.mContext = mContext;
        this.popularMovieList = popularMovieList;
        this.clickEventListener=clickEventListener;
    }


    @NonNull
    @Override
    public PopularMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.popular_movielist_item, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieAdapter.ViewHolder holder, final int position) {
        Picasso.with(mContext).load(Utils.image_base_url + popularMovieList.get(position).getImage_url()).into(holder.popular_movieIV);
        holder.popular_movieIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEventListener.buttonClicked(
                        popularMovieList.get(position).getMovie_id(),
                        popularMovieList.get(position).getImage_url(),
                        popularMovieList.get(position).getDescription(),
                        popularMovieList.get(position).getRelease_date(),
                        popularMovieList.get(position).getTitle(),
                        popularMovieList.get(position).getVote_average()

                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popular_movieIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popular_movieIV = (ImageView) itemView.findViewById(R.id.popular_movieIV);
        }
    }
}
