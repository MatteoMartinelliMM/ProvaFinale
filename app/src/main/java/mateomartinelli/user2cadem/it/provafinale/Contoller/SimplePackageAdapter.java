package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mateomartinelli.user2cadem.it.provafinale.CorriereActivity;
import mateomartinelli.user2cadem.it.provafinale.ConfirmPackageReceptionActivity;
import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;
import mateomartinelli.user2cadem.it.provafinale.R;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.PACK_TO_PASS;


/**
 * Created by Teo on 14/12/2017.
 */

public class SimplePackageAdapter extends RecyclerView.Adapter<SimplePackageAdapter.ViewHolder> {

    ArrayList<Pacco> packages;
    String classCaller;

    public SimplePackageAdapter(String classCaller) {
        packages = new ArrayList<Pacco>();
        this.classCaller = classCaller;
    }

    public SimplePackageAdapter(ArrayList<Pacco> packages, String classCaller) {
        this.packages = packages;
        this.classCaller = classCaller;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView singlePackage;
        public TextView destinatario, dataConsegna, indirizzoDestinazione, deposito, dimensioni, stato, packagedId, currierName;
        public ImageView packageImg;

        public ViewHolder(View v) {
            super(v);
            singlePackage = itemView.findViewById(R.id.PackageContainer);
            destinatario = v.findViewById(R.id.destinatario);
            dataConsegna = v.findViewById(R.id.dataConsegna);
            deposito = v.findViewById(R.id.deposito);
            packagedId = v.findViewById(R.id.idPack);
            dimensioni = v.findViewById(R.id.dimension);
            indirizzoDestinazione = v.findViewById(R.id.indirizzo);
            stato = v.findViewById(R.id.stato);
            currierName = v.findViewById(R.id.currierName);
            packageImg = v.findViewById(R.id.packageImg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (parent != null)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_package, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pacco pacco = packages.get(position);

        String destination = pacco.getDestinazione();
        String paccoData = pacco.getData();
        String wareHouse = pacco.getDeposito();
        final String sDestinatario = pacco.getDestinatario();
        String dim = pacco.getDimensione();
        String sStato = pacco.getStato();
        String idPack = pacco.getIdPacco();
        String nomeCorriere = pacco.getNomeCorriere();

        holder.destinatario.setText(sDestinatario);
        holder.dataConsegna.setText(paccoData);
        holder.deposito.setText(wareHouse);
        holder.dimensioni.setText(dim);
        holder.indirizzoDestinazione.setText(destination);
        holder.stato.setText(sStato);
        holder.packagedId.setText(idPack);
        loadBoxesDimensionImage(holder, dim);
        if (classCaller.equals("CorriereActivity")) {
            holder.currierName.setText("Tu");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RWObject.writeObject(v.getContext(), PACK_TO_PASS, pacco);
                    UtilitySharedPreference.saveUserReciver(v.getContext(),sDestinatario);
                    Intent intent = new Intent(v.getContext(), ConfirmPackageReceptionActivity.class);
                    if(v.getContext() instanceof CorriereActivity){
                        CorriereActivity context = (CorriereActivity) v.getContext();
                        context.startActivityForResult(intent,1);
                    }
                }
            });
        } else holder.currierName.setText(nomeCorriere);


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


