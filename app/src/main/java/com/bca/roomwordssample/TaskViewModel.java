package com.bca.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private LiveData<List<Task>> mAllWords;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllWords = mRepository.getmAllWords();
    }

    LiveData<List<Task>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Task task){
        mRepository.insert(task);
    }

}
