package pk.shop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProductosLista extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ImageButton bttVolver;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String DatosQR = "";

    EditText ADDprodText;//productos
    Button ADDprodButton, POPUPaddProd, SALIRpopupADD, bttverProd;
    TextView nombre, localidad, ciudad;
    String name;
    //----------------
    Button editGuardar, editCancelar;
    TextView Stock, MinStock;
    EditText stockEdit, MinimoStock;

    ListView productos, deleteproducts, editproducts;
    ArrayList<productos> ListaProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_productos_lista);
        references();
        lista();
        init();
        ListaComprar();
        // editarOculato.setVisibility(View.INVISIBLE);
    }

    public void references() {
        nombre = (TextView) findViewById(R.id.nameTextView);
        localidad = (TextView) findViewById(R.id.statusTexView);
        ciudad = (TextView) findViewById(R.id.cityTextView);
        productos = (ListView) findViewById(R.id.listaProductos);
    }

    private void lista() {
        name = getIntent().getExtras().getString("name");
        String localidade = getIntent().getExtras().getString("localidad");
        String ciudade = getIntent().getExtras().getString("city");
        String adrees = getIntent().getExtras().getString("adrees");
        nombre.setText(name);
        ciudad.setText(localidade + ", " + ciudade);
        localidad.setText(adrees);

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

        ListaProductos = new ArrayList<productos>();

        for (int a = 0; a < contador; a++) {
            System.out.println(parts[a] + "  cdsadasdasdas   ");
            String[] partesAlmacen = parts[a].split("-");

            ListaProductos.add(new productos(contador, partesAlmacen[0], partesAlmacen[1], partesAlmacen[2], partesAlmacen[3], partesAlmacen[4], partesAlmacen[5], partesAlmacen[6], partesAlmacen[7], R.drawable.grocery));
            System.out.println(ListaProductos.get(a).toString() + "  vvdffds");
        }


//ListaProductos.add(new productos(1,"2","3","4","5","9","10","11","12",R.drawable.grocery));
        AdaptadorProductos miaAdaptador = new AdaptadorProductos(getApplicationContext(), ListaProductos);
        productos.setAdapter(miaAdaptador);


    }

    public void añadirProdManual(String escanerProd) {
        ArrayList al = new ArrayList();
        String lecturaCasa = LeerEdit(name + "Casa");
        System.out.println("------------------------------------------" + lecturaCasa);
        //--------------------------ficreo count ";"
        int posicion, contador = 0;
        posicion = lecturaCasa.indexOf(";");
        while (posicion != -1) {
            contador++;

            posicion = lecturaCasa.indexOf(";", posicion + 1);
        }
        System.out.println(contador + "    ------numero de veces que aparece ;");

        if (contador >= 1) {

            System.out.println("EXITEEEE ALGO DENTROOOOOOOOOOOOO");


            int posicionComasQR, contadorComasQR = 0;
            posicionComasQR = escanerProd.indexOf(";");
            while (posicionComasQR != -1) {
                contadorComasQR++;

                posicionComasQR = escanerProd.indexOf(";", posicionComasQR + 1);
            }
            System.out.println(contadorComasQR + "    ------numero de veces que aparece ;");


            if (contadorComasQR >= 1) {
                ArrayList<String> AlMucho = new ArrayList<>();
                ArrayList<String> AlEscan = new ArrayList<>();
                System.out.println("Viene con mas de ; pero existe cosas ya dentro nene");

                String newStringCasa = lecturaCasa.replace("|", "-");
                String escaneoGUION = escanerProd.replace("|", "-");

                String[] filasFichero = newStringCasa.split(";");
                String[] filasScan = escaneoGUION.split(";");

                String nombreFicherito = "";
                for (int a = 0; a < contador; a++) {
                    AlMucho.add(filasFichero[a]);//linea en list
                    String[] productosNombre = filasFichero[a].split("-");
                    nombreFicherito += productosNombre[0] + ";";
                }

                String nombreScan = "";
                for (int a = 0; a < contadorComasQR; a++) {
                    AlEscan.add(filasScan[a]);//linea en list
                    String[] productosNombre = filasScan[a].split("-");
                    nombreScan += productosNombre[0] + ";";
                }
                System.out.println(nombreFicherito + "  +++++++  " + nombreScan + "   --********* ");

                String[] nombresFichero = nombreFicherito.split(";");
                String[] nombresScan = nombreScan.split(";");

                ArrayList<String> nombres = new ArrayList<>();

                for (int y = 0; y < contador; y++) {
                    for (int v = 0; v < contadorComasQR; v++) {

                        if (nombresFichero[y].equals(nombresScan[v])) {
                            nombres.add(nombresFichero[y]);
                            System.out.println("IGUALEEES " + nombresFichero[y] + " ==========" + nombresScan[v]);
                            System.out.println(AlMucho.get(y) + "  esta es la posiuciojn ");
                            System.out.println(AlEscan.get(v) + "  esta es la posiuciojn ");

                            String sumarExiste = AlMucho.get(y);
                            String cantidadExiste = AlEscan.get(v);

                            String[] StockSuma = sumarExiste.split("-");
                            String[] CantidadSuma = cantidadExiste.split("-");

                            int stockSUMa = Integer.parseInt(StockSuma[4]);
                            int cantidadkSUMa = Integer.parseInt(CantidadSuma[3]);
                            System.out.println("stock " + stockSUMa + "   cantidad" + cantidadkSUMa);
                            int total = stockSUMa + cantidadkSUMa;
                            System.out.println(total + "  nena maldiciuon");
                            String productoSAVE = CantidadSuma[0] + "|" + "d" + "|" + CantidadSuma[1] + "|" + CantidadSuma[2] + "|" + total + "|" + StockSuma[5] + "|" + CantidadSuma[4] + "|" + CantidadSuma[5];
                            System.out.println(productoSAVE + "    nuevooooo");
                            AlMucho.set(y, productoSAVE);


                        } else {
                            System.out.println(" no mi reewyyyyy" + nombresFichero[y] + " ==========" + nombresScan[v]);
                        }
                    }
                }


                for (int v = 0; v < contadorComasQR; v++) {

                    if (nombres.contains(nombresScan[v])) {
                        System.out.println("Ya existe");

                    } else {
                        nombres.add(nombresScan[v]);
                        String cantidadExiste = AlEscan.get(v);
                        String[] CantidadSuma = cantidadExiste.split("-");
                        String productoSAVE = CantidadSuma[0] + "|" + "d"  + "|" + CantidadSuma[1] + "|" + CantidadSuma[2] + "|" + CantidadSuma[3] + "|" + "3" + "|" + CantidadSuma[4] + "|" + CantidadSuma[5];
                        System.out.println(productoSAVE + "  esto ea lo que meteraaa");
                        AlMucho.add(productoSAVE);


                    }
                }


                String[] miarray = new String[AlMucho.size()];
                miarray = (String[]) AlMucho.toArray(miarray);

                StringBuffer cadena = new StringBuffer();
                String solu = "";
                for (int save = 0; save < AlMucho.size(); save++) {

                    cadena = cadena.append(miarray[save]).append(";");
                    solu = cadena.substring(0, cadena.length() - 1);

                }
                System.out.println("Esto ira pal ficheropoo " + solu);

                Guardar(name + "Casa", solu);
                System.out.println("SOLUCIONNNN   " + solu);
                LeerEdit(name + "Casa");


            }
           /* else if// escaneo 1 ; y 1 ; en ele fechrero
            (contadorComasQR == 1) {

                String newStringCasa = lecturaCasa.replace("|", "-");
                String escaneoGUION = escanerProd.replace("|", "-");


                System.out.println("Solo entra uno en un fichero lleno    " + newStringCasa);
                System.out.println("Escaneo con un guion:    " + escaneoGUION);


                //lecturaCasa lo que dice el fihcer

                String[] fila = newStringCasa.split(";");
                String[] nombre = fila[0].split("-");
                String[] nombreEscaneo = escaneoGUION.split("-");
                System.out.println(nombre[0] + "   nombre");
                System.out.println(nombreEscaneo[0] + "   nombreEscaneo");

                if (nombre[0].equals(nombreEscaneo[0])) {
                    System.out.println("nombre iguales");
                    System.out.println("stock  " + nombre[4]);
                    System.out.println("cantidad " + nombreEscaneo[3]);

                    int stockSUMa = Integer.parseInt(nombre[4]);
                    int cantidadkSUMa = Integer.parseInt(nombreEscaneo[3]);
                    System.out.println("stock " + stockSUMa + "   cantidad" + cantidadkSUMa);
                    int total = stockSUMa + cantidadkSUMa;
                    System.out.println(total + "  nena maldiciuon");
                    String productoSAVE = nombreEscaneo[0] + "|" + usuarioProd + "|" + nombreEscaneo[1] + "|" + nombreEscaneo[2] + "|" + total + "|" + nombre[5] + "|" + nombreEscaneo[4] + "|" + nombreEscaneo[5];
                    System.out.println(productoSAVE + "    nuevooooo");
                    String guardar = productoSAVE.substring(0, productoSAVE.length() - 1);
                    System.out.println(guardar + "   gfdgfdgdf");
                    Guardar(name + "Casa", guardar);
                    String lecturaCasa2 = LeerEdit(name + "Casa");

                }
            } */


            else {
                System.out.println("no viene con ;");
            }


        } else {


            int posicionVacio, contadorVacio = 0;
            posicionVacio = escanerProd.indexOf(";");
            while (posicionVacio != -1) {
                contadorVacio++;

                posicionVacio = escanerProd.indexOf(";", posicionVacio + 1);
            }
            System.out.println(contadorVacio + "    ------numero de veces que aparece ;");

            //  String scanerQR = escanerProd.replace("|", "-");


            if (contadorVacio >= 2) {
                System.out.println("VACIO PERO QR MAS DE ;");

                String escanerLimpio = escanerProd.replace("|", "-");
                String[] lineaVacio = escanerLimpio.split(";");
                ArrayList<String> Al = new ArrayList<>();
                for (int a = 0; a < contadorVacio; a++) {
                    String[] producto = lineaVacio[a].split("-");

                    String productoSAVE = producto[0] + "|" + "d"  + "|" + producto[1] + "|" + producto[2] + "|" + producto[3] + "|" + "3" + "|" + producto[4] + "|" + producto[5];
                    System.out.println(productoSAVE + "    nuevooooo");
                    Al.add(productoSAVE);

                    System.out.println(lineaVacio[a] + "   ---------------");
                }


                String[] miarrayLleno = new String[Al.size()];
                miarrayLleno = (String[]) Al.toArray(miarrayLleno);

                StringBuffer cadena = new StringBuffer();
                String solu = "";
                for (int save = 0; save < contadorVacio; save++) {

                    cadena = cadena.append(miarrayLleno[save]).append(";");
                    solu = cadena.substring(0, cadena.length() - 1);

                }

                System.out.println("solu  " + solu);
                Guardar(name + "Casa", solu);
                String dfs = LeerEdit(name + "Casa");
                System.out.println(dfs + "          -----------------------+++++++++++++++++");
                for (String s : miarrayLleno)
                    System.out.println(s + "   cambiosssss");


            } //insertar mas de 1 produxto PROBLEMA el stock MIN
            else//no hay na  0
            {
                System.out.println("NO HAYYYYYYYY");

                String newString = escanerProd.replace("|", "-");

                String[] producto = newString.split("-");


                dialogBuilder = new AlertDialog.Builder(this);
                final View contactPopupAddPrd = getLayoutInflater().inflate(R.layout.stockmin, null);
                //declaracione

                //ETProducto =(EditText) contactPopupAddPrd.findViewById(R.id.etproducto);
                ADDprodText = (EditText) contactPopupAddPrd.findViewById(R.id.stackMinEdit);
                ADDprodButton = (Button) contactPopupAddPrd.findViewById(R.id.bottonStockmin);

                //------------metodo


                dialogBuilder.setView(contactPopupAddPrd);
                dialog = dialogBuilder.create();
                dialog.show();
                ADDprodButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stockMin = ADDprodText.getText().toString();

                        String productoSAVE = producto[0] + "|" + "d"  + "|" + producto[1] + "|" + producto[2] + "|" + producto[3] + "|" + stockMin + "|" + producto[4] + "|" + producto[5];
                        System.out.println(productoSAVE + "    nuevooooo");
                        //solu = cadena.substring(0, cadena.length() - 1);
                        productoSAVE = productoSAVE.substring(0, productoSAVE.length() - 1);
                        Guardar(name + "Casa", productoSAVE);
                        String lecturaCasa2 = LeerEdit(name + "Casa");
                        dialog.dismiss();

                    }
                });
            }


        }

    }

    //----MenuProductos
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.PrdCreate:
                // DatosQR="alex|brand|0|5|0|01/10/2001;carla|pulvea|0|55|0|01/10/2001;MARTA|pulvea|0|55|0|01/10/2001;sara|pulvea|0|55|0|01/10/2001;ctangana|brand|0|5|0|01/10/2001;";
                dialogBuilder = new AlertDialog.Builder(this);
                final View contactPopupAddPrd = getLayoutInflater().inflate(R.layout.activity_popup_addproduct, null);
                //declaracione
                POPUPaddProd = (Button) contactPopupAddPrd.findViewById(R.id.anadirPRODPopUp);
                SALIRpopupADD = (Button) contactPopupAddPrd.findViewById(R.id.ProductoCancelar);
                //------------metodo

                dialogBuilder.setView(contactPopupAddPrd);
                dialog = dialogBuilder.create();
                dialog.show();

                SALIRpopupADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                POPUPaddProd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //DatosQR = "";
                        if (DatosQR == "") {
                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "Primero pase el escaner", Toast.LENGTH_SHORT);
                            toast1.show();
                        } else {
                            String qrCode = DatosQR;
                            añadirProdManual(qrCode);
                            //añadirProdManual("alex|brand|0|5|0|01/10/2001;carla|pulvea|0|55|0|01/10/2001;MARTA|pulvea|0|55|0|01/10/2001;sara|pulvea|0|55|0|01/10/2001;ctangana|brand|0|5|0|01/10/2001;");
                            DatosQR = "";
                            init();
                            ListaComprar();
                        }
                    }
                });
                return true;
            case R.id.PrdEdit:
                ArrayList<productos> editPro = new ArrayList<>();
                dialogBuilder = new AlertDialog.Builder(ProductosLista.this);
                final View contactPopupEditPrd = getLayoutInflater().inflate(R.layout.activity_popup_editproduct, null);

                editproducts = (ListView) contactPopupEditPrd.findViewById(R.id.listDatosEditar);
                //stockEdit,MinimoStock
                stockEdit = (EditText) contactPopupEditPrd.findViewById(R.id.StockEditar);
                MinimoStock = (EditText) contactPopupEditPrd.findViewById(R.id.MinStockEditar);
                editGuardar = (Button) contactPopupEditPrd.findViewById(R.id.save);
                editCancelar = (Button) contactPopupEditPrd.findViewById(R.id.cancel);

                Stock = (TextView) contactPopupEditPrd.findViewById(R.id.Stock);
                MinStock = (TextView) contactPopupEditPrd.findViewById(R.id.MinStock);

                String textoEdit = LeerEdit(name + "Casa");
                textoEdit = textoEdit.replace("|", "-");
                int posicionEdit, contadorEdit = 0;

                posicionEdit = textoEdit.indexOf(";");
                while (posicionEdit != -1) { //mientras se encuentre el caracter
                    contadorEdit++;           //se cuenta
                    //se sigue buscando a partir de la posición siguiente a la encontrada
                    posicionEdit = textoEdit.indexOf(";", posicionEdit + 1);
                }
                System.out.println(contadorEdit + "    ------numero de veces que aparece ;");

                String[] partsEditar = textoEdit.split(";");

                editPro = new ArrayList<productos>();
                ArrayList<String> editar = new ArrayList<>();
                for (int a = 0; a < contadorEdit; a++) {
                    editar.add(partsEditar[a]);
                    System.out.println(partsEditar[a] + "  cdsadasdasdas   ");
                    String[] partesAlmacen = partsEditar[a].split("-");

                    editPro.add(new productos(contadorEdit, partesAlmacen[0], partesAlmacen[1], partesAlmacen[2], partesAlmacen[3], partesAlmacen[4], partesAlmacen[5], partesAlmacen[6], partesAlmacen[7], R.drawable.grocery));

                }

                AdaptadorProductos miaAdaptadorEditar = new AdaptadorProductos(getApplicationContext(), editPro);
                editproducts.setAdapter(miaAdaptadorEditar);

                dialogBuilder.setView(contactPopupEditPrd);
                dialog = dialogBuilder.create();
                dialog.show();

                editproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        editar.get(position);
                        String[] partesEdit = editar.get(position).split("-");

                        MinStock.setText(partesEdit[5]);
                        Stock.setText(partesEdit[4]);
                        MinimoStock.setText(partesEdit[5]);
                        stockEdit.setText(partesEdit[4]);


                        editGuardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String StockCambiado = stockEdit.getText().toString();
                                String minimoStock = MinimoStock.getText().toString();

                                String productoSAVE = partesEdit[0] + "|" + partesEdit[1] + "|" + partesEdit[2] + "|" + partesEdit[3] + "|" + StockCambiado + "|" + minimoStock + "|" + partesEdit[6] + "|" + partesEdit[7];

                                System.out.println(productoSAVE + "   ---------------------------");
                                editar.set(position, productoSAVE);

                                String[] miarray = new String[editar.size()];
                                miarray = (String[]) editar.toArray(miarray);

                                StringBuffer cadena = new StringBuffer();
                                String solu = "";
                                for (int save = 0; save < editar.size(); save++) {

                                    cadena = cadena.append(miarray[save]).append(";");
                                    solu = cadena.substring(0, cadena.length() - 1);

                                }
                                System.out.println(solu + "  ------------------");
                                Guardar(name + "Casa", solu);
                                System.out.println("SOLUCIONNNN   " + solu);
                                LeerEdit(name + "Casa");
                                init();
                                ListaComprar();
                                dialog.dismiss();

                            }
                        });
                        editCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

                return true;
            case R.id.PrdDelete:
                ArrayList<productos> deletePro = new ArrayList<>();


                dialogBuilder = new AlertDialog.Builder(ProductosLista.this);
                final View contactPopupDeletePrd = getLayoutInflater().inflate(R.layout.pop_up_delete_product, null);
                //declaracione

                deleteproducts = (ListView) contactPopupDeletePrd.findViewById(R.id.listDatos);

                String texto = LeerEdit(name + "Casa");
                texto = texto.replace("|", "-");
                int posicion, contador = 0;

                posicion = texto.indexOf(";");
                while (posicion != -1) { //mientras se encuentre el caracter
                    contador++;           //se cuenta
                    //se sigue buscando a partir de la posición siguiente a la encontrada
                    posicion = texto.indexOf(";", posicion + 1);
                }
                System.out.println(contador + "    ------numero de veces que aparece ;");

                String[] parts = texto.split(";");

                deletePro = new ArrayList<productos>();
                ArrayList<String> delete = new ArrayList<>();
                for (int a = 0; a < contador; a++) {
                    delete.add(parts[a]);
                    System.out.println(parts[a] + "  cdsadasdasdas   ");
                    String[] partesAlmacen = parts[a].split("-");

                    deletePro.add(new productos(contador, partesAlmacen[0], partesAlmacen[1], partesAlmacen[2], partesAlmacen[3], partesAlmacen[4], partesAlmacen[5], partesAlmacen[6], partesAlmacen[7], R.drawable.grocery));

                }
//ListaProductos.add(new productos(1,"2","3","4","5","9","10","11","12",R.drawable.grocery));
                AdaptadorProductos miaAdaptador = new AdaptadorProductos(getApplicationContext(), deletePro);
                deleteproducts.setAdapter(miaAdaptador);

                dialogBuilder.setView(contactPopupDeletePrd);
                dialog = dialogBuilder.create();
                dialog.show();


                deleteproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        productos obj = (productos) parent.getItemAtPosition(position);
                        System.out.println(position + "   +++++5+5+5+5+5+");
                        System.out.println(delete.get(position) + "   +++++5+5+5+5+5+");

                        //String[] partesAlmacen = parts[position].split("-");

                        ///-----Alert Dialog

                        delete.remove(position);
                        System.out.println(" se acaboooooo");
                        String[] miarray = new String[delete.size()];
                        miarray = (String[]) delete.toArray(miarray);

                        for (String s : miarray)
                            System.out.println(s);

                        StringBuffer cadena = new StringBuffer();
                        String solu = "";
                        for (int save = 0; save < delete.size(); save++) {

                            cadena = cadena.append(delete.get(save)).append(";");
                            solu = cadena.substring(0, cadena.length() - 1);

                        }
                        Guardar(name + "Casa", solu);
                        System.out.println("SOLUCIONNNN   " + solu);
                        LeerEdit(name + "Casa");
                        init();
                        dialog.dismiss();
                    }
                });
                ListaComprar();
                return true;
            default:
                return false;
        }
    }

    //hacer la lista
    private void ListaComprar() {
        String fichero = name + "Casa";
        String listaCompraSucio = LeerEdit(fichero);
        String listaCompra = listaCompraSucio.replace("|", "-");

        int posicion, contador = 0;

        posicion = listaCompra.indexOf(";");
        while (posicion != -1) {
            contador++;
            posicion = listaCompra.indexOf(";", posicion + 1);
        }

        String[] parts = listaCompra.split(";");

        ArrayList list = new ArrayList();
        for (int a = 0; a < contador; a++) {
            System.out.println("productos:    ---  " + parts[a] + "     productos");

            String[] datos = parts[a].split("-");

            System.out.println(datos[4] + " dato1     " + datos[5] + "   segundo dato");
//datos[3] cantidas
//datos[5] minimo

            int cantidad = Integer.parseInt(datos[4]);
            int MinCantidad = Integer.parseInt(datos[5]);

            String stock = "";
            if (cantidad == MinCantidad) {
                stock = "igual";
            } else if (cantidad > MinCantidad) {
                stock = "sobrado";
            } else if (cantidad < MinCantidad) {
                stock = "poco";
            } else {
                stock = "Cuidado";
            }

            switch (stock) {
                case "igual":


                    break;
                case "sobrado":


                    break;
                case "poco":

                    list.add(parts[a]);


                    break;

                default:
                    System.out.println("Hay un problema con las cantidades");

                    break;
            }
            stock = "";

        }

        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i));
        }


        String[] listaArray = new String[list.size()];
        listaArray = (String[]) list.toArray(listaArray);

        for (String s : listaArray)
            System.out.println(s);

        StringBuffer listaCadena = new StringBuffer();
        String solu2 = "";
        for (int save = 0; save < list.size(); save++) {

            listaCadena = listaCadena.append(listaArray[save]).append(";");
            solu2 = listaCadena.substring(0, listaCadena.length() - 1);

        }
        System.out.println("Fuera del bucle   " + listaCadena);
        System.out.println("Fuera del bucle lista final a guardar   " + solu2);

    /*String compra = LeerEdit(name+"Lista");
    Guardar(name+"Lista",compra+ solu2);
    System.out.println("SOLUCIONNNN   " + solu2);
    LeerEdit(name+"Lista");
*/
        Guardar(name + "Lista", solu2);
        System.out.println("SOLUCIONNNN   " + solu2);
        LeerEdit(name + "Lista");


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
        //santy="";
        return texto;
    }

    //------------GUARDAR
    private void Guardar(String fichero, String textoGuardar) {


        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(fichero, MODE_PRIVATE);
            System.out.println("Que escribe  = " + textoGuardar);
            fileOutputStream.write(textoGuardar.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void eliminarProducrto() {
        String fichero = LeerEdit(name + "Casa");
        fichero = fichero.replace("|", "-");
        String[] filas = fichero.split(";");
        int posicion, contador = 0;
        posicion = fichero.indexOf(";");
        while (posicion != -1) {
            contador++;
            posicion = fichero.indexOf(";", posicion + 1);
        }
        ArrayList<String> ficheroFilas = new ArrayList<>();
        for (int k = 0; k < contador; k++) {
            ficheroFilas.add(filas[k]);

        }
        /////////////
        String algo = "alex|d|brand|0|65|3|0|01/10/2001;";
        ////////////


    }

    //---ir a Compra
    public void setBttverProd(View view) {
       compra();
    }
    public void compra(){
        bttverProd = findViewById(R.id.verPROD);
            Intent verDetalle = new Intent(this, ProductosCompra.class);
            startActivity(verDetalle);
    }

    //--Volver al Main
    public void setBttVolver(View view) {
        bttVolver = findViewById(R.id.bttbackHome);
        onBackPressed();
    }

    //Scanner
    public void onClick(View view) {
        if (view.getId() == R.id.AbrirScanner) {
            new IntentIntegrator(this).initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String datos = result.getContents();
        DatosQR = datos;
    }

    public void setBttMenu(View v) {
        PopupMenu menuproducto = new PopupMenu(this, v);
        menuproducto.setOnMenuItemClickListener(this);
        menuproducto.inflate(R.menu.menuproduct);
        menuproducto.show();
    }
}