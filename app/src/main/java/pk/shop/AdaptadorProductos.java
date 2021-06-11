package pk.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorProductos extends BaseAdapter {

    Context contexto;
    List<productos> ListaProductos;

    public AdaptadorProductos(Context contexto, List<productos> listaProductos) {
        this.contexto = contexto;
        ListaProductos = listaProductos;
    }

    @Override
    public int getCount() {
        return ListaProductos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaProductos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista= inflater.inflate(R.layout.list_product,null);

        ImageView imagen = (ImageView)vista.findViewById(R.id.IVproduct);
        TextView nombre = (TextView)vista.findViewById(R.id.TVname);
        TextView marca = (TextView)vista.findViewById(R.id.TVmarca);
        TextView Stock = (TextView)vista.findViewById(R.id.TVstock);
        TextView StockMin = (TextView)vista.findViewById(R.id.TVstockMin);
        TextView fechaCompra = (TextView)vista.findViewById(R.id.TVFechaCompra);



        imagen.setImageResource(ListaProductos.get(position).getImagen());
        nombre.setText(ListaProductos.get(position).getNombre());
        marca.setText(ListaProductos.get(position).getMarca());
        Stock.setText(ListaProductos.get(position).getStock());
        StockMin.setText(ListaProductos.get(position).getStockMin());
        fechaCompra.setText(ListaProductos.get(position).getFechaCompra());



        return vista;
    }
}
