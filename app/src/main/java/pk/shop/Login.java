package pk.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.ArrayList;

public class Login extends MainActivity {
    String login;
    String password;
    private EditText edit1, edit2;
    private Button LoginButton, RegisterButton;
    TextView textView, aviso;
    private boolean sesion = false;
    private String resultado = "", prueba1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        reference();
        textView.setVisibility(View.GONE);
        aviso.setVisibility(View.GONE);
    }

    private void reference() {
        edit1 = (EditText) findViewById(R.id.etUser);
        edit2 = (EditText) findViewById(R.id.etPass);
        textView = (TextView) findViewById(R.id.textView);
        aviso = (TextView) findViewById(R.id.aviso);
        RegisterButton = findViewById(R.id.bttRegister);
        LoginButton = findViewById(R.id.bttLogin);
    }

    private void Function_CalltoPHP_FILE_LOGIN() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String link = "http://172.17.20.59:80/PHPfiles/loginUser.php?usr=" + login + "&pass=" + password + "";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                resultado = response;
                textView.setText("Response: " + response + "ddd");

                String cadena = resultado;
                ArrayList<Character> lista = new ArrayList<>();
                for (int i = 0; i < cadena.length(); i++) {
                    if (Character.isDigit(cadena.charAt(i)))
                        lista.add(cadena.charAt(i));

                }

                if (lista.get(0) == '1') {
                    System.out.println("Entrada");
                    aviso.setVisibility(View.GONE);
                    home();
                } else if (lista.get(0) == '0') {
                    System.out.println("Salida");
                    aviso.setVisibility(View.GONE);

                } else {
                    System.out.println("Error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Eroor:  " + error.toString());
            }
        });

        queue.add(stringRequest);
        //System.out.println(resultado+ "------------");
        prueba1 = resultado;

    }

    public void requestMensage() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://172.17.41.21/PHPfiles/loginUser.php?usr=d&pass=d";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText("Response: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Eroor:  " + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    public void LoginPage(View view) {
        login = edit1.getText().toString();
        password = edit2.getText().toString();
        Function_CalltoPHP_FILE_LOGIN();
        home();
    }

    public void home() {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("usuario", login);
        startActivity(home);
        finish();
    }

    //Register
    public void registerPage(View view) {
        setRegisterButton();
    }
    public void setRegisterButton(){
        RegisterButton.setOnClickListener(v -> {
            Intent register = new Intent(this, Register.class);
            startActivity(register);
        });
    }
}