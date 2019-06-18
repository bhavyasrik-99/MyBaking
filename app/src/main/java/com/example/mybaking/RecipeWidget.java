package com.example.mybaking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */

public class RecipeWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, String ingre) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent intent=new Intent(context,MainActivity.class);

        PendingIntent pi=PendingIntent.getActivity(context,0,intent,0);

        views.setTextViewText(R.id.recipe_widget_text,ingre);

        views.setOnClickPendingIntent(R.id.recipe_widget_text,pi);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RecipeWidgetService.seeviceCall(context,"Tap for ingredients");


    }
    static void onUpdateIngredients(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,String ingre){

        for(int appWidgetId:appWidgetIds ){

            updateAppWidget(context,appWidgetManager,appWidgetId,ingre);

        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
    }
}


