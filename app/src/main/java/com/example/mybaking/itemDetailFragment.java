package com.example.mybaking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybaking.dummy.DummyContent;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single item detail screen.
 * This fragment is either contained in a {@link itemListActivity}
 * in two-pane mode (on tablets) or a {@link itemDetailActivity}
 * on handsets.
 */
public class itemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public itemDetailFragment() {
    }

    String rurl;
    ImageView imgv;
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView sexpv;
    Uri exovideourl;
    long position;
    RecipeStepsPojo rpstepsmodel;
    boolean play;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
           // mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        sexpv = rootView.findViewById(R.id.exoplayer_itemview);
        imgv = rootView.findViewById(R.id.image_description);


        // Show the dummy content as text in a TextView.
        Activity act;
        act = this.getActivity();
        CollapsingToolbarLayout abl;
        abl = act.findViewById(R.id.toolbar_layout);

//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        //}
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            rpstepsmodel = getArguments().getParcelable(ARG_ITEM_ID);
            if (rpstepsmodel != null) {
                if (abl != null) {
                    abl.setTitle(rpstepsmodel.getSid());
                }
                if (rpstepsmodel != null) {
                    ((TextView) rootView.findViewById(R.id.item_details)).setText(rpstepsmodel.getSd());
                } else {
                    ((TextView) rootView.findViewById(R.id.item_details)).setText("No description of the recipe");

                }
                if (rpstepsmodel.getVideopath() != null) {
                    rurl = rpstepsmodel.getVideopath();
                    startplay();

                } else if (rpstepsmodel.getTurl() != null) {
                    rurl = rpstepsmodel.getTurl();
                    startplay();
                    if (rpstepsmodel.getTurl() == null) {
                        Context context = getActivity().getApplicationContext();
                        Picasso.with(context).load(rurl).into(imgv);
                    }
                }
            } else {
                Toast.makeText(act, "No steps for this recipe", Toast.LENGTH_SHORT).show();
            }
        }
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("pos");
            exoPlayer.seekTo(position);
            play = savedInstanceState.getBoolean("play");
            exoPlayer.setPlayWhenReady(play);
        }
        return rootView;
    }

    public void startplay() {
        if (exoPlayer == null && rurl != null) {
            BandwidthMeter bwm;
            bwm = new DefaultBandwidthMeter();
            TrackSelector tks;
            tks = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bwm));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), tks);
            exovideourl = Uri.parse(rurl);
            DefaultHttpDataSourceFactory ddsf;
            ddsf = new DefaultHttpDataSourceFactory("ExoPlayerDemo");
            ExtractorsFactory efs;
            efs = new DefaultExtractorsFactory();
            MediaSource ms;
            ms = new ExtractorMediaSource(exovideourl, ddsf, efs, null, null);
            sexpv.setPlayer(exoPlayer);
            exoPlayer.prepare(ms);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer!=null){
            outState.putLong("pos",exoPlayer.getContentPosition());
            outState.putBoolean("play",exoPlayer.getPlayWhenReady());

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(exoPlayer!=null)
        {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}


