package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;
import mateomartinelli.user2cadem.it.provafinale.R;

/**
 * Created by Teo on 14/12/2017.
 */

public class SimplePackageAdapter extends RecyclerView.Adapter<SimplePackageAdapter.ViewHolder>{
    ArrayList<Pacco> packages;
    public SimplePackageAdapter() {
        packages = new ArrayList<Pacco>();
    }

    public SimplePackageAdapter(ArrayList<Pacco> packages) {
        this.packages = packages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView singlePackage;
        public TextView destinatario, dataConsegna,indirizzoDestinazione,deposito,dimensioni,stato,packagedId, currierName;
        public ViewHolder(View v) {
            super(v);
            singlePackage = itemView.findViewById(R.id.PackageContainer);
            destinatario = v.findViewById(R.id.destinatario);
            dataConsegna = v.findViewById(R.id.dataConsegna);
            deposito = v.findViewById(R.id.deposito);
            dimensioni = v.findViewById(R.id.dimension);
            indirizzoDestinazione = v.findViewById(R.id.indirizzo);
            stato = v.findViewById(R.id.stato);
            packagedId = v.findViewById(R.id.idPacco);
            currierName = v.findViewById(R.id.currierName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(parent!= null) v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_package,null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pacco pacco = packages.get(position);

        String destination = pacco.getDestinazione();
        String paccoData = pacco.getData();
        String wareHouse = pacco.getDeposito();
        String sDestinatario = pacco.getDestinatario();
        String dim = pacco.getDimensione();
        String sStato = pacco.getStato();
        String idPacco = pacco.getIdPacco();
        String nomeCorriere = pacco.getNomeCorriere();

        holder.destinatario.setText(sDestinatario);
        holder.dataConsegna.setText(paccoData);
        holder.deposito.setText(wareHouse);
        holder.dimensioni.setText(dim);
        holder.indirizzoDestinazione.setText(destination);
        holder.stato.setText(sStato);
        holder.packagedId.setText(idPacco);
        holder.currierName.setText(nomeCorriere);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeOfClick = v.getId();
                /*RWObject.writeObject(v.getContext(), SAVE_POST,p);
                Intent intent = new Intent(v.getContext(), CommentsActivity.class);
                intent.putExtra(SAVE_POST,postName);
                v.getContext().startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return packages.size();
    }


}


