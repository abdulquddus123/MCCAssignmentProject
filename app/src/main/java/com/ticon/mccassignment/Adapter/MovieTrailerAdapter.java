package com.ticon.mccassignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticon.mccassignment.Interface.ClickEventListener;
import com.ticon.mccassignment.Interface.TrailerClickEventInterface;
import com.ticon.mccassignment.Model.PopularMovieListData;
import com.ticon.mccassignment.Model.TrailerDataModel;
import com.ticon.mccassignment.Model.TrailerdataSourceModel;
import com.ticon.mccassignment.R;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {
    private List<TrailerDataModel> trailerList;
    private Context mContext;
    TrailerClickEventInterface trailerClickEventInterface;
    View view;
    ClickEventListener clickEventListener;
    public MovieTrailerAdapter(Context mContext, List<TrailerDataModel> trailerList,TrailerClickEventInterface trailerClickEventInterface) {
        this.mContext = mContext;
        this.trailerList = trailerList;
        this.trailerClickEventInterface=trailerClickEventInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        view = LayoutInflater.from(mContext).inflate(R.layout.movie_trailer_item, parent, false);
        final MovieTrailerAdapter.ViewHolder mViewHolder = new MovieTrailerAdapter.ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
      holder.trailer_name.setText(trailerList.get(position).getTrailer_name());
      holder.play_icon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              trailerClickEventInterface.playIconClicked(trailerList.get(position).getId());
          }
      });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView play_icon;
        TextView trailer_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            play_icon=(ImageView)itemView.findViewById(R.id.play_icon);
            trailer_name=(TextView)itemView.findViewById(R.id.trailer_name);
        }
    }
}
