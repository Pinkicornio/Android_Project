package pk.shop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProductosCompra extends AppCompatActivity {
    private ImageButton bttVolver;
    private Button bttComprar;
    TextView nombre, marca, precio, fechaC ;
    String name;
    ListView compras;
    ArrayList<Compra> ListaCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comprar);
        init();
        //lista();
        // editarOculato.setVisibility(View.INVISIBLE);
    }
    public void references() {
        bttVolver = findViewById(R.id.bttbackHome);
        bttComprar = findViewById(R.id.comprar);
        nombre = (TextView) findViewById(R.id.TVname);
        marca = (TextView) findViewById(R.id.TVmarca);
        precio = (TextView) findViewById(R.id.TVprecio);
        fechaC = findViewById(R.id.TVFechaCompra);
        compras = (ListView) findViewById(R.id.listaCompras);
    }

    private void lista() {
        name = getIntent().getExtras().getString("name");
        String marca = getIntent().getExtras().getString("marca");
        String ciudade = getIntent().getExtras().getString("city");
        String adrees = getIntent().getExtras().getString("adrees");
        nombre.setText(name);
    }

    public void init() {
        String fichero = name + "Casa";//aqui tiene que recibir el fichero que quieres añadir osea la lista
        String texto = LeerEdit(fichero);
        int posicion, contador = 0;

        posicion = texto.indexOf(";");
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = texto.indexOf(";", posicion + 1);
        }
        System.out.println(contador + "    ------numero de veces que aparece ;");
        String textoLimpio = texto.replace("|", "-");
        System.out.println(textoLimpio + "    ------nce++++++++++++++++ ;");
        String[] parts = textoLimpio.split(";");

        ListaCompra = new ArrayList<Compra>();

        for (int a = 0; a < contador; a++) {
            System.out.println(parts[a] + "  cdsadasdasdas   ");
            String[] partesAlmacen = parts[a].split("-");

            ListaCompra.add(new Compra(contador, partesAlmacen[0], partesAlmacen[1], partesAlmacen[2], partesAlmacen[3], partesAlmacen[4], partesAlmacen[5], partesAlmacen[6], partesAlmacen[7], R.drawable.grocery));
            System.out.println(ListaCompra.get(a).toString() + "  vvdffds");
        }

//ListaProductos.add(new productos(1,"2","3","4","5","9","10","11","12",R.drawable.grocery));
        AdaptadorCompra miaAdaptador = new AdaptadorCompra(getApplicationContext(), ListaCompra);
        Compra.setAdapter(miaAdaptador);

    }


    //--------------LEER
    private String LeerEdit(String fichero) {
        String texto = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fichero);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineaTexto = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((lineaTexto = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineaTexto).append(";");
            }
            texto = stringBuilder.toString();
            System.out.println("El texto es " + texto);
        } catch (Exception e) {

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return texto;
    }
    //-------Logout
    public void setComprar(View view) {


        compra();
    }

    public void compra(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("¿Confrimar compra?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        msgToast("Compra realizada");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    //--Volver al Main
    public void setBttVolver(View view) {
        bttVolver = findViewById(R.id.bttbackHome);
        onBackPressed();
    }
    public void msgToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
