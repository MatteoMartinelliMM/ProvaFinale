package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;
import mateomartinelli.user2cadem.it.provafinale.R;

/**
 * Created by utente2.academy on 12/15/2017.
 */

public class UserPackageAdapter extends RecyclerView.Adapter<UserPackageAdapter.ViewHolder> {

    ArrayList<Pacco> packages;
    private Context context;

    public UserPackageAdapter() {
        packages = new ArrayList<Pacco>();
    }

    public UserPackageAdapter(ArrayList<Pacco> packages) {
        this.packages = packages;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView singlePackage;
        public TextView dataConsegna, indirizzoDestinazione, packagedId, currierName;
        public ImageView packageImg,stato;

        public ViewHolder(View v) {
            super(v);
            singlePackage = itemView.findViewById(R.id.userPackageContainer);
            dataConsegna = v.findViewById(R.id.deliveryDate);
            stato = v.findViewById(R.id.stateImg);
            packagedId = v.findViewById(R.id.idP);
            indirizzoDestinazione = v.findViewById(R.id.indrizzoDest);
            currierName = v.findViewById(R.id.elCurier);
            packageImg = v.findViewById(R.id.box);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        context = parent.getContext();
        if (parent != null)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_package_for_user, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pacco pacco = packages.get(position);

        String destination = pacco.getDestinazione();
        String paccoData = pacco.getData();
        String dim = pacco.getDimensione();
        String idPack = pacco.getIdPacco();
        String nomeCorriere = pacco.getNomeCorriere();
        String sStato = pacco.getStato();

        holder.dataConsegna.setText(paccoData);
        holder.indirizzoDestinazione.setText(destination);
        holder.packagedId.setText(idPack);
        loadBoxesDimensionImage(holder, dim);
        holder.currierName.setText(nomeCorriere);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.truck_delivery);
        holder.stato.setImageBitmap(bitmap);
        int newColor;
        switch (sStato.toLowerCase()){
            case "commissionato":
                holder.stato.setColorFilter(R.color.red);
                break;
            case "in consegna":
                holder.stato.setColorFilter(R.color.colorPrimaryDark);
                break;
            case "consegnato":
                holder.stato.setColorFilter(R.color.colorPrimary);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    private void loadBoxesDimensionImage(ViewHolder holder, String dim) {
        switch (dim.toLowerCase()) {
            case "small":
                holder.packageImg.setImageResource(R.drawable.box_small);
                break;
            case "medium":
                holder.packageImg.setImageResource(R.drawable.box_medium);
                break;
            case "large":
                holder.packageImg.setImageResource(R.drawable.box_large);
                break;
        }
    }

}