package com.example.mybaking;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

public class RecipeWidgetService extends IntentService {

    public RecipeWidgetService() {

        super("asdf");
    }

    @SuppressLint("ResourceType")
    public static void seeviceCall(Context context, String ingre){

        Intent intent=new Intent(context, RecipeWidgetService.class);

        intent.putExtra("Ingredients",ingre);

        intent.setAction("action");//problem is here itself

        context.startService(intent);

    }


    @SuppressLint("ResourceType")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    if(intent.getAction().equals("action")){

            String ingredients=intent.getStringExtra("Ingredients");

            AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(this);

            int[] appWidgetids=appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));

            RecipeWidget.onUpdateIngredients(this,appWidgetManager,appWidgetids,ingredients);

        }

    }
}


