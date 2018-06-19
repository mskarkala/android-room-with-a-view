package com.mes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository
{
    private Webservice webservice;
    //private UserCache  userCache;

    private String DB_NAME = "db_task";

    private MyDatabase myDatabase;

    public UserRepository(Context context)
    {
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, DB_NAME).build();
    }

    public LiveData<Earthquake> getEarthquake(int earthquakeId)
    {
//        LiveData<Earthquake> cached = userCache.get(earthquakeId);
//        if (cached != null)
//        {
//            return cached;
//        }
        // This is not an optimal implementation, we'll fix it below
        final MutableLiveData<Earthquake> data = new MutableLiveData<>();
//        userCache.put(earthquakeId, data);
        webservice.getEarthquake(earthquakeId).enqueue(new Callback<Earthquake>()
        {
            @Override
            public void onResponse(Call<Earthquake> call, Response<Earthquake> response)
            {
                // error case is left out for brevity
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Earthquake> call, Throwable t)
            {

            }
        });
        return data;
    }

    public void insertTask(String title, String description)
    {
        insertTask(title, description, false, null);
    }

    public void insertTask(String title, String description, boolean encrypt, String password)
    {
        Earthquake earthquake = new Earthquake();
        insertTask(earthquake);
    }

    public void insertTask(final Earthquake earthquake)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                myDatabase.earthquakeDao().insertTask(earthquake);
                return null;
            }
        }.execute();
    }

    public void updateTask(final Earthquake note)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                myDatabase.earthquakeDao().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final int id)
    {
        final LiveData<Earthquake> task = getTask(id);
        if (task != null)
        {
            new AsyncTask<Void, Void, Void>()
            {
                @Override
                protected Void doInBackground(Void... voids)
                {
                    myDatabase.earthquakeDao().deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final Earthquake note)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                myDatabase.earthquakeDao().deleteTask(note);
                return null;
            }
        }.execute();
    }

    public LiveData<Earthquake> getTask(int id)
    {
        return myDatabase.earthquakeDao().getTask(id);
    }

    public LiveData<List<Earthquake>> getTasks()
    {
        return myDatabase.earthquakeDao().fetchAllTasks();
    }
}