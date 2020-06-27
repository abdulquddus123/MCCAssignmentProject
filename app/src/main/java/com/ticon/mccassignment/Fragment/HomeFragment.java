package com.ticon.mccassignment.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ticon.mccassignment.Adapter.PopularMovieAdapter;
import com.ticon.mccassignment.Interface.ClickEventListener;
import com.ticon.mccassignment.Model.PopularMovieListData;
import com.ticon.mccassignment.Model.PopularMovieListModel;
import com.ticon.mccassignment.R;
import com.ticon.mccassignment.Retrofit.APIClient;
import com.ticon.mccassignment.Retrofit.APIInterface;
import com.ticon.mccassignment.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ClickEventListener {

    APIInterface apiInterface;
    RecyclerView popularMovieRecyclerView;
    List<PopularMovieListModel> popularMovieList = new ArrayList<>();
    List<PopularMovieListData> popularMoviedataList = new ArrayList<>();
    PopularMovieAdapter popularMovieAdapter;
    PopularMovieListData popularMovieListData;
    NavController navController;
    ClickEventListener clickEventListener;
    Bundle bundle;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Pop Movies");

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickEventListener = this;
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        popularMovieRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movielist_recyclerview);
        popularMovieAdapter = new PopularMovieAdapter(getActivity(), popularMoviedataList, clickEventListener);
        popularMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularMovieRecyclerView.setAdapter(popularMovieAdapter);
        getPopularMovie();
    }

    public void getPopularMovie() {

        Call<PopularMovieListModel> call = apiInterface.getPopularMoiveList(Utils.api_key);
        call.enqueue(new Callback<PopularMovieListModel>() {
            @Override
            public void onResponse(Call<PopularMovieListModel> call, Response<PopularMovieListModel> response) {
                PopularMovieListModel resource = response.body();
                List<PopularMovieListModel.Result> movieList = resource.getResults();
                for (int i = 0; i < movieList.size(); i++) {
                    popularMovieListData = new PopularMovieListData();
                    System.out.println("api response " + movieList.get(i).getId().toString());
                    System.out.println("api response path " + movieList.get(i).getPosterPath());
                    popularMovieListData.setMovie_id(movieList.get(i).getId().toString());
                    popularMovieListData.setImage_url(movieList.get(i).getPosterPath());
                    popularMovieListData.setDescription(movieList.get(i).getOverview());
                    popularMovieListData.setTitle(movieList.get(i).getOriginalTitle());
                    popularMovieListData.setRelease_date(movieList.get(i).getReleaseDate());
                    popularMovieListData.setVote_average(movieList.get(i).getVoteAverage().toString());
                    popularMoviedataList.add(popularMovieListData);
                    System.out.println("api response path 11 " + popularMoviedataList.get(i).getImage_url());
                }
                popularMovieAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<PopularMovieListModel> call, Throwable t) {
                System.out.println("failure " + t.getCause() + t.getMessage());
                call.cancel();
            }
        });
    }


    @Override
    public void buttonClicked(String id, String image_url, String description, String release_date, String title, String vote_average) {
        bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("image_url", image_url);
        bundle.putString("description", description);
        bundle.putString("release_date", release_date);
        bundle.putString("title", title);
        bundle.putString("vote_average", vote_average);
        navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle);

    }
}