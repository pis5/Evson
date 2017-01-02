package com.example.mkass.evson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;
import entities.Evenement;
import entities.Personne;
import entities.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private List<Post> listPost = new ArrayList<Post>();

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_post, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post post = listPost.get(position);
        holder.display(post);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView commentaire;
        MyTextView sender;
        MyTextView date;
        ImageView image;
        ImageView profilImage;

        private Post currentPost;


        public MyViewHolder(final View itemView) {
            super(itemView);

            commentaire = (MyTextView)itemView.findViewById(R.id.title);
            sender = (MyTextView)itemView.findViewById(R.id.sender);
            date = (MyTextView)itemView.findViewById(R.id.date);
            image = (ImageView)itemView.findViewById(R.id.image);
            profilImage = (ImageView)itemView.findViewById(R.id.imageProfil);
            // profilImage = (ImageView)itemView.findViewById(R.id.profilImage);

        }

        public void display(Post p) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy H:m");

            currentPost = p;
            commentaire.setText(p.getCommentaire());

            sender.setText(p.getPersonneId().getPrenom() +" " + p.getPersonneId().getPrenom());
            if (p.getPersonneId().getPhoto() != null) {
               Bitmap bMap = BitmapFactory.decodeByteArray(p.getPersonneId().getPhoto(),0, p.getPersonneId().getPhoto().length);
               profilImage.setImageBitmap(bMap);
            }


            if(p.getDate()!=null)
                date.setText(sdf.format(p.getDate()));
        }
    }


    public void invokeWS(final Evenement evenement, final Context context){

        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new Gson();
        params.put("evenement", gson.toJson(evenement));

        // Show Progress Dialog
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        final String ip = context.getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("MMM d, yyyy HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                // When the JSON response has status boolean value assigned with true
                if(!response.equals("")&& !response.equals(null) && !response.equals("[]")){
                    //
                    Type type = new TypeToken<List<Post>>(){}.getType();
                    List<Post> L = gson.fromJson(response, type);

                    listPost.addAll(L);

                    notifyDataSetChanged();
                }
                // Else display error message
                else{
                    Toast.makeText(context, "Nothing to show here!!", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(context, "Something went wrong at server end !! ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

        };
        client.get( ip +"/posts/afficher",params ,RH);
    }

}