package pk.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends BaseAdapter {
    Context contexto;
    List<Datos> ListaObjetos;

    public Adaptador(Context contexto, List<Datos> listaObjetos){
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        return ListaObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaObjetos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista;
        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista = inflater.inflate(R.layout.itemlistview,null);

        ImageView imagen = (ImageView)vista.findViewById(R.id.iconImageView);
        TextView nombre = (TextView)vista.findViewById(R.id.nameTextView);
        TextView localizacion = (TextView)vista.findViewById(R.id.cityTextView);

        nombre.setText(ListaObjetos.get(position).getNombre());
        localizacion.setText(ListaObjetos.get(position).getLocalitation());
        imagen.setImageResource(ListaObjetos.get(position).getImagen());
        return  vista;


    }
}
