package com.example.mybaking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * An activity representing a single item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link itemListActivity}.
 */
public class itemDetailActivity extends AppCompatActivity {
    FloatingActionButton fb;
Boolean check;
RecipeStepsPojo rpojo;
itemDetailFragment itf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent;
        intent=getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "added to widgets", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            itf=new itemDetailFragment();
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
             /*if(intent.hasExtra(itemDetailFragment.ARG_ITEM_ID)){*/
                 Intent intents=getIntent();
                rpojo= intents.getExtras().getParcelable(itemDetailFragment.ARG_ITEM_ID);
                // rpojo=(RecipeStepsPojo) intent.getParcelableExtra(itemDetailFragment.ARG_ITEM_ID);
                 Toast.makeText(this, "  "+rpojo.getSid(), Toast.LENGTH_SHORT).show();
                 arguments.putParcelable(itemDetailFragment.ARG_ITEM_ID,rpojo);

            // }
            itf.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, itf)
                    .commit();
        }else{
            itf= (itemDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, itemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
