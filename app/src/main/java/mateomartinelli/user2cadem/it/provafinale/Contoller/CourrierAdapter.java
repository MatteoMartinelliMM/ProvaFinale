package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.R;

/**
 * Created by utente2.academy on 12/15/2017.
 */

public class CourrierAdapter extends ArrayAdapter<Corriere> {
    private ArrayList<Corriere> corrieri;
    private LayoutInflater layoutInflater;

    public CourrierAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Corriere> corrieri) {
        super(context, resource, corrieri);
        this.layoutInflater = LayoutInflater.from(context); //TO REHEARS
        this.corrieri = corrieri;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        TextView nome,nrPacchi;
        if(v==null) v = layoutInflater.inflate(R.layout.currier_list,null);//se view vuota -> aggiungi un elemento di lista alla lista
        Corriere c = getItem(position);
        nome = (TextView) v.findViewById(R.id.nCorriere);
        nrPacchi = (TextView)v.findViewById(R.id.nPacchi);
        nome.setText(c.getUserName());
        nrPacchi.setText(c.getIdPacchi().size()+"");
        return v;
    }

    @Nullable
    @Override
    public Corriere getItem(int position) {
        return corrieri.get(position);
    }
}
