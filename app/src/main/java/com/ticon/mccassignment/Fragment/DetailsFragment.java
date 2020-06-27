package com.ticon.mccassignment.Fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ticon.mccassignment.Adapter.MovieTrailerAdapter;
import com.ticon.mccassignment.Interface.OnFullScreenChangeListener;
import com.ticon.mccassignment.Interface.TrailerClickEventInterface;
import com.ticon.mccassignment.Model.PopularMovieListData;
import com.ticon.mccassignment.Model.PopularMovieListModel;
import com.ticon.mccassignment.Model.TrailerDataModel;
import com.ticon.mccassignment.Model.TrailerdataSourceModel;
import com.ticon.mccassignment.R;
import com.ticon.mccassignment.Retrofit.APIClient;
import com.ticon.mccassignment.Retrofit.APIInterface;
import com.ticon.mccassignment.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsFragment extends Fragment implements TrailerClickEventInterface, OnFullScreenChangeListener {

    TextView titleTV, descriptionTV, release_dateTV, vote_averageTV;
    ImageView bannerIV;
    APIInterface apiInterface;
    String movieId;
    TrailerDataModel trailerDataModel;
    RecyclerView trailer_recyclerView;
    MovieTrailerAdapter movieTrailerAdapter;
    List<TrailerDataModel> trailerList = new ArrayList<>();
    TrailerClickEventInterface trailerClickEventInterface;
    NavController navController;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        titleTV = (TextView) view.findViewById(R.id.movienameTV);
        descriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
        release_dateTV = (TextView) view.findViewById(R.id.yearTV);
        vote_averageTV = (TextView) view.findViewById(R.id.ratingTV);
        bannerIV = (ImageView) view.findViewById(R.id.imageView);
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity().getApplicationContext(), "Bundle args " + getArguments().getString("id"), Toast.LENGTH_SHORT).show();
        Picasso.with(getActivity()).load(Utils.image_base_url + getArguments().getString("image_url")).into(bannerIV);
        titleTV.setText(getArguments().getString("title"));
        descriptionTV.setText(getArguments().getString("description"));
        String date = getArguments().getString("release_date");
        System.out.println("date " + date);
        release_dateTV.setText(date.substring(0, 4));
        vote_averageTV.setText(getArguments().getString("vote_average") + "/10");
        movieId = getArguments().getString("id");
        trailerClickEventInterface = this;
        trailer_recyclerView = (RecyclerView) view.findViewById(R.id.trailer_recyclerview);
        movieTrailerAdapter = new MovieTrailerAdapter(getActivity(), trailerList, trailerClickEventInterface);
        trailer_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trailer_recyclerView.setItemAnimator(new DefaultItemAnimator());
        trailer_recyclerView.setAdapter(movieTrailerAdapter);
//get api data
        getMovieTrailer();
    }

    public void getMovieTrailer() {
        Call<TrailerdataSourceModel> call = apiInterface.getMovieTrailer(movieId, Utils.api_key);
        call.enqueue(new Callback<TrailerdataSourceModel>() {
            @Override
            public void onResponse(Call<TrailerdataSourceModel> call, Response<TrailerdataSourceModel> response) {
                TrailerdataSourceModel resource = response.body();
                List<TrailerdataSourceModel.Result> movieList = resource.getResults();
                for (int i = 0; i < movieList.size(); i++) {
                    trailerDataModel = new TrailerDataModel();
                    trailerDataModel.setId(movieList.get(i).getKey().toString());
                    trailerDataModel.setTrailer_name(movieList.get(i).getName());
                    trailerList.add(trailerDataModel);
                }
                movieTrailerAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<TrailerdataSourceModel> call, Throwable t) {
                System.out.println("failure " + t.getCause() + t.getMessage());
                call.cancel();
            }
        });
    }

    Bundle bundle;

    @Override
    public void playIconClicked(String id) {
        bundle = new Bundle();
        bundle.putString("id", id);
        navController.navigate(R.id.action_detailsFragment_to_youtubeChannelFragment, bundle);

    }

    @Override
    public void onChangeListener() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Utils.youtubePlayerFullScreen = false;
    }
}