package com.example.podcasfy.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.podcasfy.R;
import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.AppExecutorUtils;

import java.util.ArrayList;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("WIDGET", "Create onGetViewFactory");

        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<Podcast> podcastList;

    GridRemoteViewsFactory(Context mContext) {

        Log.d("WIDGET", "Create GridRemoteViewsFactory");

        this.mContext = mContext;
        podcastList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        onDataSetChanged();
    }

    @Override
    public void onDataSetChanged() {
        final AppDataBase appDataBase = AppDataBase.getInstance(mContext);

        AppExecutorUtils.getsInstance().diskIO().execute(() -> {
            podcastList = appDataBase.podcastDAO().getPodcasts();
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(podcastList != null){
            Log.d("WIDGET", "getcount podcast" + podcastList.size());
            return podcastList.size();
        }
        Log.d("WIDGET", "no podcast" );

        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(podcastList != null && podcastList.size() > 0){

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.subscription_widget);

            views.setTextViewText(R.id.appwidget_text,podcastList.get(position).getName());

            Intent fillIntent = new Intent();

            views.setOnClickFillInIntent(R.id.appwidget_text, fillIntent);

            return views;
        }

        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
