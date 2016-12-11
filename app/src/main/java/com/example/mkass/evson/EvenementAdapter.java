package com.example.mkass.evson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
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

public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.MyViewHolder> {

    private List<Evenement> evenements = new ArrayList<Evenement>();


    @Override
    public int getItemCount() {
        return evenements.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Evenement ev = evenements.get(position);
        holder.display(ev);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView title;
        MyTextView description;
        MyTextView sender;
        MyTextView date;
        ImageView image;
        ImageView profilImage;

        private Evenement currentEv;


        public MyViewHolder(final View itemView) {
            super(itemView);

            title = (MyTextView)itemView.findViewById(R.id.title);
            description = (MyTextView)itemView.findViewById(R.id.description);
            sender = (MyTextView)itemView.findViewById(R.id.sender);
            date = (MyTextView)itemView.findViewById(R.id.date);
            image = (ImageView)itemView.findViewById(R.id.image);
           // profilImage = (ImageView)itemView.findViewById(R.id.profilImage);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void display(Evenement ev) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

            currentEv = ev;
            title.setText(ev.getTitre());
            description.setText(ev.getText());
            if(ev.getOrganisateur() != null){
                sender.setText(ev.getOrganisateur().getNom() +" " + ev.getOrganisateur().getPrenom());
                if (ev.getOrganisateur().getPhoto() != null) {
                    Bitmap bMap = BitmapFactory.decodeByteArray(ev.getOrganisateur().getPhoto(),0, ev.getOrganisateur().getPhoto().length);
                    image.setImageBitmap(bMap);
                }
            }

            if(ev.getDateDeCreation()!=null)
                date.setText(sdf.format(ev.getDateDeCreation()));


        }
    }


    public void invokeWS(final Personne pers, final int offset, final int nbre, final boolean plusAncien, final Context context){

        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new Gson();
        params.put("personne", gson.toJson(pers));
        params.put("offset", gson.toJson(evenements.size()-1));
        params.put("nbre", gson.toJson(nbre));
        params.put("plusAncien", gson.toJson(plusAncien));
        // Show Progress Dialog
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        final String ip = context.getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                Gson gson = new Gson();
                // When the JSON response has status boolean value assigned with true
                if(!response.equals("")&& !response.equals(null) && !response.equals("[]")){
                    //
                    Type type = new TypeToken<List<Evenement>>(){}.getType();
                    List<Evenement> L = gson.fromJson(response, type);
                    evenements.addAll(L);
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
                     Toast.makeText(context, "Something went wrong at server end 2 ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                     Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFinish() {/*
                Gson gson = new Gson();
                RequestParams params = new RequestParams();
                params.put("personne", gson.toJson(pers));
                params.put("offset", gson.toJson(evenements.size()-1));
                params.put("nbre", gson.toJson(nbre));
                params.put("plusAncien", gson.toJson(plusAncien));
                client.get( context.getString(R.string.ipAdress) +"/evenementsamis/afficher",params ,this);*/
            }
        };
        client.get( ip +"/evenementsamis/afficher",params ,RH);

    }

}