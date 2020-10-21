package com.bca.roomwordssample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllWords;

    TaskRepository(Application application){
            TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
            mTaskDao = db.wordDao();
            mAllWords = mTaskDao.getAllWords();
    }

    LiveData<List<Task>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Task task){
        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
