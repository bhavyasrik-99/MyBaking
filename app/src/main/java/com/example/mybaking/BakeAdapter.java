package com.example.mybaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BakeAdapter extends RecyclerView.Adapter<BakeAdapter.RecipeViewHolder> {
    int posters[]=new int[4];
   ArrayList<BakePojo> pojos;
    Context context;

    public BakeAdapter(int[] posters, ArrayList<BakePojo> pojos, Context context) {
        this.posters = posters;
        this.pojos = pojos;
        this.context = context;
    }

    @NonNull
    @Override
    public BakeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_menu,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull BakeAdapter.RecipeViewHolder recipeViewHolder,final int i) {
        Picasso.with(context).load(posters[i]).placeholder(R.drawable.ic_launcher_background).into(recipeViewHolder.rpimage);
        recipeViewHolder.rpname.setText(pojos.get(i).getRname());
        recipeViewHolder.rpname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it;
                it=new Intent(context,itemListActivity.class);
                Bundle b=new Bundle();
                b.putParcelable("recipe Baking",pojos.get(i));
                it.putExtras(b);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (pojos==null)?0:pojos.size();
        //return pojos.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView rpname;
        ImageView rpimage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            rpname=itemView.findViewById(R.id.recipeTitle);
            rpimage=itemView.findViewById(R.id.recipe_poster);
        }
    }
}
