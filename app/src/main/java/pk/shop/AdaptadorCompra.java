package pk.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorCompra extends BaseAdapter {

    Context contexto;
    List<Compra> ListaCompra;

    public AdaptadorCompra(Context contexto, List<Compra> listaCompra) {
        this.contexto = contexto;
        ListaCompra = listaCompra;
    }

    @Override
    public int getCount() {
        return ListaCompra.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaCompra.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaCompra.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista= inflater.inflate(R.layout.list_productcompra,null);

        ImageView imagen = (ImageView)vista.findViewById(R.id.IVproduct);
        TextView nombre = (TextView)vista.findViewById(R.id.TVname);
        TextView marca = (TextView)vista.findViewById(R.id.TVmarca);
        TextView precio = vista.findViewById(R.id.TVprecio);
        TextView Stock = (TextView)vista.findViewById(R.id.TVstock);
        TextView StockMin = (TextView)vista.findViewById(R.id.TVstockMin);
        TextView fechaCompra = (TextView)vista.findViewById(R.id.TVFechaCompra);



        imagen.setImageResource(ListaCompra.get(position).getImagen());
        nombre.setText(ListaCompra.get(position).getNombre());
        marca.setText(ListaCompra.get(position).getMarca());
        precio.setText(ListaCompra.get(position).getPrecio());
        Stock.setText(ListaCompra.get(position).getStock());
        StockMin.setText(ListaCompra.get(position).getStockMin());
        fechaCompra.setText(ListaCompra.get(position).getFechaCompra());

        return vista;
    }
}
