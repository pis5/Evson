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
import android.widget.Button;
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

public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.MyViewHolder> {

    private List<Evenement> evenements = new ArrayList<Evenement>();
    private Personne pers;
    private Context context;

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    @Override
    public int getItemCount() {
        return evenements.size();
    }

    public EvenementAdapter(Personne personne){
        pers = personne;
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
        Button btnParticipation;

        private Evenement currentEv;


        public MyViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();

            title = (MyTextView)itemView.findViewById(R.id.title);
            description = (MyTextView)itemView.findViewById(R.id.description);
            sender = (MyTextView)itemView.findViewById(R.id.sender);
            date = (MyTextView)itemView.findViewById(R.id.date);
            image = (ImageView)itemView.findViewById(R.id.image);
            profilImage = (ImageView)itemView.findViewById(R.id.imageProfil);
            btnParticipation =(Button)itemView.findViewById(R.id.btn_participer);
           // profilImage = (ImageView)itemView.findViewById(R.id.profilImage);

            if(itemView.getContext() instanceof HomeActivity){


            }else{
                btnParticipation.setVisibility(View.GONE);

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(view.getContext(), EventActivity.class);
                    it.putExtra("personne",pers);
                    it.putExtra("evenement",currentEv);
                    view.getContext().startActivity(it);
                }
            });
        }

        public void display(final Evenement ev) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

            currentEv = ev;
            title.setText(ev.getTitre());
            description.setText(ev.getText());
            if(ev.getOrganisateur() != null){
                sender.setText(ev.getOrganisateur().getNom() +" " + ev.getOrganisateur().getPrenom());
                if (ev.getOrganisateur().getPhoto() != null) {
                    Bitmap bMap = BitmapFactory.decodeByteArray(ev.getOrganisateur().getPhoto(),0, ev.getOrganisateur().getPhoto().length);
                    profilImage.setImageBitmap(bMap);
                }
            }

            if(ev.getDateDeCreation()!=null)
                date.setText(sdf.format(ev.getDateDeCreation()));
            if(itemView.getContext() instanceof HomeActivity) {
                btnParticipation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        invokeParticipe(pers, ev, context);
                    }
                });
            }

        }
    }

    private void invokeParticipe(Personne pers, final Evenement ev, final Context context) {


        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("MMM d, yyyy HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        params.put("personne", gson.toJson(pers));
        params.put("evenement", gson.toJson(ev));

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

                if(response.equals("added")){
                    //

                    boolean b=evenements.remove(ev);
                    Log.i("l", Boolean.toString(b));
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

        };
        client.get( ip +"/mesevenements/add",params ,RH);
    }


    public void invokeWS(final Personne pers,  final int nbre, final boolean plusAncien, final Context context){

        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new Gson();
        params.put("personne", gson.toJson(pers));
       if(evenements.size()>0){
        params.put("offset", gson.toJson(evenements.get(evenements.size()-1).getId()));}
        else{   params.put("offset",gson.toJson(0));}

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
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("MMM d, yyyy HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                // When the JSON response has status boolean value assigned with true
                if(!response.equals("")&& !response.equals(null) && !response.equals("[]")){
                    //
                    Type type = new TypeToken<List<Evenement>>(){}.getType();
                    List<Evenement> L = gson.fromJson(response, type);
                    if(plusAncien == true)
                        evenements.addAll(evenements.size(),L);
                    else
                        evenements.addAll(0, L);
                    Log.i("Liste actuelle", evenements.toString());
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
        client.get( ip +"/evenementsamis/afficher",params ,RH);

    }

    public void invokeMesEvenements(final Personne pers,  final int nbre, final boolean plusAncien, final Context context){

        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new Gson();
        params.put("personne", gson.toJson(pers));
        if(evenements.size()>0){
            params.put("offset", gson.toJson(evenements.get(evenements.size()-1).getId()));}
        else{   params.put("offset",gson.toJson(0));}

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
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("MMM d, yyyy HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                // When the JSON response has status boolean value assigned with true
                if(!response.equals("")&& !response.equals(null) && !response.equals("[]")){
                    //
                    Type type = new TypeToken<List<Evenement>>(){}.getType();
                    List<Evenement> L = gson.fromJson(response, type);
                    if(plusAncien == true)
                        evenements.addAll(evenements.size(),L);
                    else
                        evenements.addAll(0, L);
                    Log.i("Liste actuelle", evenements.toString());
                    notifyDataSetChanged();
                }
                // Else display error message
                else{
                    Toast.makeText(context, "All events charged", Toast.LENGTH_LONG).show();
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
        client.get( ip +"/mesevenements/afficher",params ,RH);
    }

}