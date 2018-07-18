package com.parassidhu.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.parassidhu.popularmovies.database.MovieDatabase;
import com.parassidhu.popularmovies.database.MovieRepository;
import com.parassidhu.popularmovies.models.FavoriteMovie;
import com.parassidhu.popularmovies.models.MovieItem;
import com.parassidhu.popularmovies.utils.Constants;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<MovieItem>> allMovies = new MutableLiveData<>();
    private MovieDatabase mDb;
    private String TAG = getClass().getSimpleName();
    private Application application;
    private LiveData<List<FavoriteMovie>> favMovies = new MutableLiveData<>();

    private String recentSortBy = Constants.POPULAR_LIST;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mDb = MovieDatabase.getDatabase(application);
        this.application = application;

        if (mRepository.isOnline()) {
            fetchMovies(Constants.FIRST_TIME_URL, recentSortBy);
        } else {
            allMovies = mRepository.getAllMovies();
        }
    }

    public LiveData<List<MovieItem>> getAllMovies() {
        Log.d(TAG, "getAllMovies: Added");
        return allMovies;
    }

    public void fetchMovies(String URL, String sortBy) {
        recentSortBy = sortBy;
        allMovies = mRepository.fetchMovies(URL, sortBy);
    }

    public void insertFavMovie(FavoriteMovie movie) {
        mRepository.insertFavMovie(movie);
    }

    public MovieItem getMovieById(int id) {
        return mRepository.getMovieById(id);
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return mRepository.getFavoriteMovies();
    }
}