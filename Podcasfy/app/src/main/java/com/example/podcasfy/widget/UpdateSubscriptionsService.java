package com.example.podcasfy.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.podcasfy.R;

public class UpdateSubscriptionsService extends IntentService {

    public static final String ACTION_UPDATE_SUBSCRIPTIONS =
            "com.example.podcasfy.widget.action.update_subscriptions";

    public UpdateSubscriptionsService(){
        super("UpdateSubscriptionsService");
    }

    public static void startActionUpdateSubscriptions(Context context) {
        Intent intent = new Intent(context, UpdateSubscriptionsService.class);
        intent.setAction(ACTION_UPDATE_SUBSCRIPTIONS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_SUBSCRIPTIONS.equals(action)) {
                handleActionUpdateSubscriptions();
            }
        }
    }

    private void handleActionUpdateSubscriptions() {
        Log.d("WIDGET", "THIS IS MY WIDGET!");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, UpdateSubscriptionsService.class.getSimpleName()));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

        SubscriptionWidget.onUpdateSubscriptions(this, appWidgetManager, appWidgetIds);
    }
}
