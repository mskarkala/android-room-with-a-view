package com.mes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class UserProfileViewModel extends ViewModel
{
    private LiveData<Earthquake> earthquake;
    private UserRepository       userRepo;

    @Inject // UserRepository parameter is provided by Dagger 2
    public UserProfileViewModel(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    public void init(int earthquakeId)
    {
        if (this.earthquake != null)
        {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        earthquake = userRepo.getEarthquake(earthquakeId);
    }

    public LiveData<Earthquake> getEarthquake()
    {
        return this.earthquake;
    }
}