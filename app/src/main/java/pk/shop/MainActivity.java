package pk.shop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.SQLTransactionRollbackException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    ImageButton bttHomecrt, bttHomeedit, bttHomedlt,bttInfo, bttProfile, bttLogout;

    private Button bttAlmacenarSave, bttAlmacenarCancelar, bttAlmacenEditar;
    private Spinner spinerCategoriaLugar;

    private EditText NameEdit, LocalitationEdit, CityEdit, AdreesEdit;

    private LinearLayout POcreateLayout;
    private GridLayout gridLayout;
    private RecyclerView recyclerView;
    //private String usuario, casa;
    Context mContext = this;
//------------------------Eliminar
    ListView listaDatos, listaCasas, listaCasaEdit;
    ArrayList<Datos> Lista;
    //-------------------POP UP-------------------------

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText ETname, ETlocation, ETcity, ETaddress;

    //--------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        references();
        init();

        /*File file3 = new File(getApplicationContext().getFilesDir(), "o");
        file3.delete();
        File file4 = new File(getApplicationContext().getFilesDir(), "oCasa");
        file4.delete();
        showCasas();*/
    }

    private void showCasas() {

        String path = getApplicationContext().getFilesDir().toString();
        //Log.d("Files", "Path: " + path);
        System.out.println(path + "   ----------------------");
        File directory = new File(path);
        File[] files = directory.listFiles();
        //Log.d("Files", "Size: " + files.length);
        System.out.println(files.length + "  SIZE----------------------");

        for (int i = 0; i < files.length; i++) {
            System.out.println("FileName:" + files[i].getName());
        }
    }

    public void references() {
        //MainLayout
        bttHomecrt = findViewById(R.id.bttHcrt);
        bttHomeedit = findViewById(R.id.bttHedit);
        bttHomedlt = findViewById(R.id.bttHdlt);
        gridLayout = findViewById(R.id.centerScreen);
        listaCasas = findViewById(R.id.listaCasitas);
    }


    //----------ADD
    public void openDialogCreate(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupCreate = getLayoutInflater().inflate(R.layout.activity_popup_create, null);
        //DECLARACIONES
        ETname = (EditText) contactPopupCreate.findViewById(R.id.etname);
        ETlocation = (EditText) contactPopupCreate.findViewById(R.id.etlocation);
        ETcity = (EditText) contactPopupCreate.findViewById(R.id.etcity);
        ETaddress = (EditText) contactPopupCreate.findViewById(R.id.etadress);
        bttAlmacenarSave = contactPopupCreate.findViewById(R.id.AlmacenSave);
        bttAlmacenarCancelar = contactPopupCreate.findViewById(R.id.AlmacenCancelar);
        dialogBuilder.setView(contactPopupCreate);
        dialog = dialogBuilder.create();
        dialog.show();
        bttAlmacenarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                String fecha = dateFormat.format(date);
                //hour
                DateFormat hourateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Date hour = new Date();
                String hourformatted = hourateFormat.format(hour);
                //miliseconds
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int mSec = calendar.get(Calendar.MILLISECOND);
                //gestion de todoe
                hourformatted = hourformatted.replace(":", "");
                fecha = fecha.replace("-", "");

                String creacion = fecha + hourformatted + mSec;

                String textoASalvar = ETname.getText().toString() + "/" + "d" + "/" + ETlocation.getText().toString() + "/" + ETcity.getText().toString() + "/" + ETaddress.getText().toString() + "/" + creacion;

                FileOutputStream fileOutputStream = null;
                FileOutputStream Lista = null;


                File file = new File(getApplicationContext().getFilesDir(), ETname.getText().toString());

                if (file.exists()) {
                    Toast casaExiste = Toast.makeText(getApplicationContext(), "La casa ya existe", Toast.LENGTH_SHORT);
                    casaExiste.show();
                } else {
                    try {
                        fileOutputStream = openFileOutput(ETname.getText().toString(), MODE_PRIVATE);
                        fileOutputStream.write(textoASalvar.getBytes());
                        System.out.println("Fichero Salvado en: " + getFilesDir() + "/" + ETname.getText().toString() + " ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        /*----------------------------------- crear lista */
                        try {
                            Añadir(textoASalvar);
                            //--------------------------------------------------------
                            String inicio = "";
                            Lista = openFileOutput(ETname.getText().toString() + "Casa", MODE_PRIVATE);
                            Lista.write(inicio.getBytes());

                            System.out.println("Fichero Salvado en: " + getFilesDir() + "/" + ETname.getText().toString() + "Almacen");
                            /*----------------------------------- crear lista */

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
                        /*----------------------------------- */
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
                dialog.dismiss();
                init();
            }
        });
        bttAlmacenarCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void init() {
        String fichero = "Casas";
        String CasaLista = "";

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
            CasaLista = stringBuilder.toString();

        } catch (Exception e) {

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {

                }
            }
        }

        int posicion, contador = 0;

        posicion = CasaLista.indexOf(";");
        while (posicion != -1) {
            contador++;

            posicion = CasaLista.indexOf(";", posicion + 1);
        }

        String[] parts = CasaLista.split(";");

        Lista = new ArrayList<Datos>();
        for (int a = 0; a < contador; a++) {


            String[] productos = parts[a].split("/");

            Lista.add(new Datos(a, productos[0], productos[1], productos[2], R.drawable.grocery, productos[3], productos[4], productos[5]));

        }


        Adaptador miaAdaptador = new Adaptador(getApplicationContext(), Lista);
        listaCasas.setAdapter(miaAdaptador);

        listaCasas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Datos obj = (Datos) parent.getItemAtPosition(position);
                Intent paso = new Intent(getApplicationContext(), ProductosLista.class);
                paso.putExtra("name", obj.getNombre());
                paso.putExtra("localidad", obj.getLocalitation());
                paso.putExtra("city", obj.getCiudad());
                startActivity(paso);
            }
        });
    }

    //----------EDIT ACABAR
    public void openDialogEdit(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupEdit = getLayoutInflater().inflate(R.layout.activity_popup_edit, null);
        //DECLARACIONES cambiar ojito
        spinerCategoriaLugar = (Spinner) contactPopupEdit.findViewById(R.id.spinnerCategoriaLugar);
        bttAlmacenEditar = (Button) contactPopupEdit.findViewById(R.id.editarPopUp);
        listaCasaEdit = (ListView) contactPopupEdit.findViewById(R.id.listaCasa);
        NameEdit = (EditText) contactPopupEdit.findViewById(R.id.editname);
        LocalitationEdit = (EditText) contactPopupEdit.findViewById(R.id.editlocalitation);
        CityEdit = (EditText) contactPopupEdit.findViewById(R.id.editcity);
        AdreesEdit = (EditText) contactPopupEdit.findViewById(R.id.editadress);
        //-------------------onMenuItemClick
        ArrayList al = new ArrayList();
        String lecturaCasa = LeerEdit("Casas");
        System.out.println(lecturaCasa + " fdfdsfdfd piola");
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = lecturaCasa.indexOf(";");
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = lecturaCasa.indexOf(";", posicion + 1);
        }
        System.out.println(contador + "    ------numero de veces que aparece ;");
        String[] parts = lecturaCasa.split(";");
        String ficherito = "";
        Lista = new ArrayList<Datos>();
        for (int a = 0; a < contador; a++) {
            String[] productos = parts[a].split("/");
            ficherito += productos[0] + ";";
            Lista.add(new Datos(a, productos[0], productos[1], productos[2], R.drawable.grocery, productos[3], productos[4], productos[5]));
        }


        Adaptador miaAdaptador = new Adaptador(getApplicationContext(), Lista);
        listaCasaEdit.setAdapter(miaAdaptador);


        String casas = "";
        for (int a = 0; a < contador; a++) {
            al.add(parts[a]);
            String[] productos = parts[a].split("/");
            casas += productos[0] + ";";


        }

        String[] nameCasas = casas.split(";");

        spinerCategoriaLugar.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nameCasas));

        dialogBuilder.setView(contactPopupEdit);
        dialog = dialogBuilder.create();
        dialog.show();

        int finalContador = contador;


        String finalFicherito = ficherito;
        bttAlmacenEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(finalContador + "   gfgdsgfs");
                String text = spinerCategoriaLugar.getSelectedItem().toString();
                int index = spinerCategoriaLugar.getSelectedItemPosition();
                if (searchList(parts, parts[index])) {
                    if (searchList(parts, "")) {
                        int pos2 = al.indexOf("");
                        al.remove(pos2);
                    } else {
                        System.out.println("Todo Correcto");
                    }
                    al.get(index);
                    System.out.println(al.get(index) + "  gfgdgdfgdfg");
                    String editarCasita = al.get(index).toString();
                    String[] editCasa = editarCasita.split("/");

                    System.out.println(editCasa[0] + "  primera ");
                    System.out.println(editCasa[1] + "  segunda ");
                    System.out.println(editCasa[2] + "  tercera ");
                    System.out.println(editCasa[3] + "  cuarta ");
                    System.out.println(editCasa[4] + "  quinta ");
                    System.out.println(editCasa[5] + "  sexta ");

                    System.out.println(finalFicherito);
                    finalFicherito.substring(0, finalFicherito.length() - 1);
                    String[] nombreFicheroChange = finalFicherito.split(";");

                    System.out.println(nombreFicheroChange[index] + "  este es tu hombreee");
                    String cambioCasa = NameEdit.getText().toString() + "/" + editCasa[1] + "/" + LocalitationEdit.getText().toString() + "/" + CityEdit.getText().toString() + "/" + AdreesEdit.getText().toString() + "/" + editCasa[5];


                    al.set(index, cambioCasa);

                    Guardar(NameEdit.getText().toString(), cambioCasa);

                    al.get(index);
                    System.out.println(al.get(index) + "  jajjajajajajjajjajaja");
                    String[] miarray = new String[al.size()];
                    miarray = (String[]) al.toArray(miarray);

                    for (String s : miarray)
                        System.out.println(s);

                    StringBuffer cadena = new StringBuffer();
                    String solu = "";
                    for (int save = 0; save < finalContador; save++) {

                        cadena = cadena.append(miarray[save]).append(";");
                        solu = cadena.substring(0, cadena.length() - 1);

                    }
                    //spinerCategoriaLugar.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nameCasas));
                    Guardar("Casas", solu);
                    System.out.println("SOLUCIONNNN   " + solu);
                    LeerEdit("Casas");
                    System.out.println(parts[index] + " e3s esta casita nene");

                    System.out.println(nombreFicheroChange[index] + "  esta cogiendo esta casa nene");

                    String leerFicheroOLD = LeerEdit(nombreFicheroChange[index] + "Casa");

                    System.out.println(leerFicheroOLD + "  antess fdsfsdfds");
                    System.out.println(leerFicheroOLD.length() + "  lopnmgitu");

                    if (leerFicheroOLD.length() == 0) {
                        Guardar(NameEdit.getText().toString() + "Casa", "");

                        File file3 = new File(getApplicationContext().getFilesDir(), nombreFicheroChange[index]);
                        file3.delete();
                        File file4 = new File(getApplicationContext().getFilesDir(), nombreFicheroChange[index] + "Casa");
                        file4.delete();

                        init();

                        showCasas();
                        dialog.dismiss();
                    } else if (leerFicheroOLD.length() != 0) {

                        String sCadena = leerFicheroOLD.substring(0, leerFicheroOLD.length() - 2);
                        System.out.println(sCadena + "     EOOOOOOEOOEOEOEOEOEOEOE");
                        Guardar(NameEdit.getText().toString() + "Casa", sCadena);


                        String leerFicheroOLDLista = LeerEdit(NameEdit.getText().toString() + "Casa");

                        System.out.println(leerFicheroOLDLista + "  DESPUES FFSDFSDF");


                        File file3 = new File(getApplicationContext().getFilesDir(), nombreFicheroChange[index]);
                        file3.delete();
                        File file4 = new File(getApplicationContext().getFilesDir(), nombreFicheroChange[index] + "Casa");
                        file4.delete();
                        File file5 = new File(getApplicationContext().getFilesDir(), nombreFicheroChange[index] + "Lista");
                        file5.delete();

                        init();

                        showCasas();
                        dialog.dismiss();
                    }

                } else
                    System.out.println("Valor no encontrado");
                dialog.dismiss();

            }
        });


    }

    private static boolean searchList(String[] strings, String searchString) {
        return Arrays.asList(strings).contains(searchString);
    }

    //-----------DELETE
    public void openDialogDelete(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupEdit = getLayoutInflater().inflate(R.layout.activity_popup_delete, null);

        listaDatos = (ListView) contactPopupEdit.findViewById(R.id.listDatos);


        Lista = new ArrayList<Datos>();
        String lecturaCasa = LeerEdit("Casas");
        System.out.println(lecturaCasa + " fdfdsfdfd piola");
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = lecturaCasa.indexOf(";");
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = lecturaCasa.indexOf(";", posicion + 1);
        }
        System.out.println(contador + "    ------numero de veces que aparece ;");
        ArrayList al = new ArrayList();
        String[] parts = lecturaCasa.split(";");


        for (int a = 0; a < contador; a++) {

            String[] productos = parts[a].split("/");


            System.out.println(productos[5] + "  fdsfsdfsdf");
            al.add(parts[a]);

            Lista.add(new Datos(a, productos[0], productos[1], productos[2], R.drawable.grocery, productos[3], productos[4], productos[5]));


        }

        Adaptador miaAdaptador = new Adaptador(getApplicationContext(), Lista);
        listaDatos.setAdapter(miaAdaptador);

        dialogBuilder.setView(contactPopupEdit);
        dialog = dialogBuilder.create();
        dialog.show();


        //   int finalContador = contador;
        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Datos obj = (Datos) parent.getItemAtPosition(position);
                File file2 = new File(getApplicationContext().getFilesDir(), obj.getNombre());
                file2.delete();
                File file3 = new File(getApplicationContext().getFilesDir(), obj.getNombre() + "Casa");
                file3.delete();
                File file4 = new File(getApplicationContext().getFilesDir(), obj.getNombre() + "Lista");
                file4.delete();
                try {
                    int pos = al.indexOf(obj.getNombre() + "/" + obj.getUsuario() + "/" + obj.getLocalitation() + "/" + obj.getCiudad() + "/" + obj.getAdrees() + "/" + obj.getCreacion());
                    al.remove(pos);
                    String[] miarray2 = new String[al.size()];
                    miarray2 = (String[]) al.toArray(miarray2);
                    for (String s : miarray2)
                        System.out.println(s + "   cambiosssss");

                    StringBuffer cadena = new StringBuffer();
                    String solu = "";
                    for (int save = 0; save < al.size(); save++) {

                        cadena = cadena.append(miarray2[save]).append(";");
                        solu = cadena.substring(0, cadena.length() - 1);

                    }
                    Guardar("Casas", solu);
                    System.out.println("SOLUCIONNNN   " + solu);
                    LeerEdit("Casas");
                    System.out.println(pos + "   posicion gatete");
                    System.out.println(obj.getId() + "   gfdgfdgdf");
                    System.out.println(obj.getNombre() + "   gfdgfdgdf");
                    System.out.println(obj.getUsuario() + "   gfdgfdgdf");
                    System.out.println(obj.getLocalitation() + "   gfdgfdgdf");
                    System.out.println(obj.getCiudad() + "   gfdgfdgdf");
                    System.out.println(obj.getAdrees() + "   gfdgfdgdf");
                    System.out.println(obj.getCreacion() + "   gfdgfdgdf");

                    init();
                    dialog.dismiss();
                } catch (Exception e) {
                    dialog.dismiss();
                }


            }
        });

    }

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

    public void Añadir(String texto) {
        String santy = "";
        String fichero = "Casas";//**cambiar GEORGINA
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
            santy = stringBuilder.toString();
            System.out.println("El texto es " + santy);
        } catch (Exception e) {

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {

                }
            }
        }
        FileOutputStream fileOutputStream = null;

        String textoASalvar = santy + texto;
        try {
            fileOutputStream = openFileOutput(fichero, MODE_PRIVATE);
            System.out.println("Que escribe  = " + textoASalvar);
            fileOutputStream.write(textoASalvar.getBytes());

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
    //-------Info
    public void setInfo(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupInfo = getLayoutInflater().inflate(R.layout.activity_popup_info, null);
        bttInfo = findViewById(R.id.bttInfo);
        dialogBuilder.setView(contactPopupInfo);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    //-------Perfil
    public void setProfile(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupProfile = getLayoutInflater().inflate(R.layout.activity_popup_profile, null);
        bttProfile = findViewById(R.id.bttProfile);
        dialogBuilder.setView(contactPopupProfile);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    //-------Logout
    public void setLogout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("¿Seguro de salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainActivity.this, Login.class));
                        msgToast("Bye-Bye");
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

    public void msgToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}