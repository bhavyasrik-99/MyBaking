package com.example.mybaking;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybaking.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link itemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class itemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static  final String name="com.example.mybaking";
    String pack=name;
SharedPreferences shp;
SharedPreferences.Editor ed;
String rtitle;
String ingarray;
String steparray;
ArrayList<RecipeIngredients> recipeing;
List<RecipeStepsPojo> steps;
BakePojo bakePojo;
TextView tv;
String recipeQuantity;
String recipeMeasure;
String ingredients;
String sid;
String ssd;
String sd;
String ste;
String ing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        steps=new ArrayList<>();
        recipeing=new ArrayList<>();
        tv=findViewById(R.id.recipe_ingredient_text);
        Intent intent;
        intent=getIntent();

        if (intent.getExtras()!=null){

            bakePojo=intent.getExtras().getParcelable("recipe Baking");
           // bakePojo=intent.getParcelableExtra("baking");
            rtitle=bakePojo.getRname();
            ingarray=bakePojo.getRing();
            steparray=bakePojo.getSteps();
        }else{
            Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar acbar;
        acbar=getSupportActionBar();
        if (acbar!=null)acbar.setTitle(rtitle);

        //toolbar.setTitle(getTitle());
        if(ingarray==null);
        else {
            JSONArray ings;
            String k="";


            try {
                ings = new JSONArray(ingarray);
                int len = ings.length();
                for (int a = 0; a < len; a++) {
                    JSONObject jb;

                    jb = ings.optJSONObject(a);

                    recipeQuantity = jb.optString("quantity");
                    recipeMeasure = jb.optString("measure");
                    ingredients = jb.optString("ingredient");
                    recipeing.add(new RecipeIngredients(recipeQuantity, recipeMeasure, ingredients));
                    tv.append("\u2023 " + ingredients + "\t" + recipeQuantity + "\t" + recipeMeasure + "\n");
                    k+=ingredients + "\t" + recipeQuantity + "\t" + recipeMeasure + "\n";

                    Toast.makeText(this, "  "+recipeQuantity, Toast.LENGTH_SHORT).show();
                }
                RecipeWidgetService.seeviceCall(this,k);//oh u got it madam
            }
        catch (Exception e){
                e.printStackTrace();
        }
        }

//Congrats !!! thank you
        if(steparray==null);
            else{
            try {
               JSONArray rsteps=new JSONArray(steparray);
                int len=rsteps.length();
                for(int a=0;a<len;a++){
                    JSONObject jsonObject;
                    jsonObject=rsteps.optJSONObject(a);
                    sid=jsonObject.optString("id");
                    ssd=jsonObject.optString("shortDescription");
                    sd=jsonObject.optString("description");
                    String videopath=jsonObject.optString("videoURL");
                    String turl=jsonObject.optString("thumbnailURL");
                    steps.add(new RecipeStepsPojo(sid,ssd,sd,videopath,turl));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Widget added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            shp=getSharedPreferences(pack,MODE_PRIVATE);
            ed=shp.edit();
            StringBuffer sb;
            sb=new StringBuffer();
            int k=recipeing.size();
            for(int a=0;a<k;a++){

                sb.append(recipeing.get(a)
                        .getIngredients());
            }
            ed.putString("name",sb.toString());ed.apply();

            Intent in;
            in=new Intent(itemListActivity.this,RecipeWidget.class);
            in.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[]refids;
            refids=(AppWidgetManager.getInstance(itemListActivity.this)).getAppWidgetIds(new ComponentName(getApplication(),RecipeWidget.class));
            in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,refids);
            sendBroadcast(in);
            }
        });


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,steps, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final itemListActivity mParentActivity;
        private  List<RecipeStepsPojo> mValues=null;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeStepsPojo stepsPojo = (RecipeStepsPojo) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(itemDetailFragment.ARG_ITEM_ID, stepsPojo);
                    itemDetailFragment fragment = new itemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, itemDetailActivity.class);
                    intent.putExtra(itemDetailFragment.ARG_ITEM_ID, stepsPojo);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(itemListActivity parent,
                                      List<RecipeStepsPojo> mValues1,
                                      boolean twoPane) {
            mValues = mValues1;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getSsd());
            //holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            //final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.content_text_id);
               // mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
