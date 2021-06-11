package pk.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Register extends AppCompatActivity {
    private EditText ET_userName, ET_pass, ET_pass1, ET_email;
    private String email = null, user = null, pass = null, pass1 = null,dateUp= null,cliente = null,PassWordFinal = null;
    private Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        references();
    }

    private void references() {
        ET_userName = (EditText) findViewById(R.id.edittxtUser);
        ET_pass = (EditText) findViewById(R.id.edittxtPass);
        ET_pass1 = (EditText) findViewById(R.id.edittxtPass1);
        ET_email = (EditText) findViewById(R.id.edittxtEmail);
        volver = (Button) findViewById(R.id.volver);

    }

    public void Volver(View view) {
        volver = findViewById(R.id.bttRegister);
        onBackPressed();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUser(View view) {
        user = String.valueOf(ET_userName.getText());
        pass = String.valueOf(ET_pass.getText());
        pass1 = String.valueOf(ET_pass1.getText());
        email = String.valueOf(ET_email.getText());
        if (pass.equals(pass1)) {
            msgToast("Password acceptable");
            if (email.contains("@")) {
                LocalDate fechaCreacion = LocalDate.now();
                dateUp = fechaCreacion.toString();
                PassWordFinal=  encriptar(pass, fechaCreacion);
                cliente = "usuario";
                Function_CalltoPHP_FILE_Register();

            } else {
                msgToast("email is not correct");
                ET_email.setText("");
            }
        } else {
            msgToast("Passwords must be the same");
            ET_pass.setText("");
            ET_pass1.setText("");
        }
    }

    private void Function_CalltoPHP_FILE_Register()
    {
        String link = "http://172.17.41.21:80/PHPfiles/registerUser.php";

        //String link = "http://localhost/PHPfiles/registerUser.php";
        new ClassCallPhpRegister().execute(link,user,email,PassWordFinal,dateUp,cliente);
    }

    public void msgToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public String encriptar(String contra, LocalDate fecha) {
        System.out.println("contra = " + contra);
        String fechaHash = fecha.toString();
        fechaHash = fechaHash.replace("-", "");

        System.out.println("fecha = " + fechaHash);
        //-----------
        String hashDate = md5(fechaHash);
        System.out.println("fecha hasheada = " + hashDate);
        //-----------
        String hashpass = md5(contra);
        System.out.println(" contra = " + hashpass);
        //-----------
        String salContra = md5(hashDate + hashpass);
        //-----------
        System.out.println(hashDate + hashpass);
        //msgToast(salContra);
        System.out.println("fecha y contra hasheados unidos y vueltos a hashear = " + salContra);

        //-----------(date+pass) hasheados
        String pebre = salContra;
        int lengh = pebre.length();
        System.out.println(lengh);
        String sSubCadena = pebre.substring(0, 17);
        System.out.println("parte 1 :    " + sSubCadena);
        String sSubCadena2 = pebre.substring(17, lengh);
        System.out.println("parte 2 :    " + sSubCadena2);
        String Contrapebre = sSubCadena2 + sSubCadena;
        System.out.println(Contrapebre);
        //lengh conocer la lingtud de la string luego lo separamos por X numero y cambiarlo de posicion
        String contraFinal = md5(salContra + Contrapebre);
        System.out.println("La contraseña final es = " + contraFinal);

        return contraFinal;

    }


    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}