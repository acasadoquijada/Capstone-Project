package com.example.podcasfy.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.podcasfy.MainActivity;
import com.example.podcasfy.R;

/**
 * Implementation of App Widget functionality.
 */
public class SubscriptionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews rv = getSubscriptionGridRemoteView(context);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getSubscriptionGridRemoteView(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.subscription_widget_provider);

        Intent intent = new Intent(context,GridWidgetService.class);

        Intent appIntent = new Intent(context, MainActivity.class);

        PendingIntent appPendingIntent = PendingIntent.getActivity(
                context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        views.setEmptyView(R.id.widget_grid_view,R.id.recipe_name_widget);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    public static void onUpdateSubscriptions(Context context, AppWidgetManager appWidgetManager,
                                             int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

