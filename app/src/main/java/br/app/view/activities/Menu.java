package br.app.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import br.app.view.controller.UsuarioController;
import br.app.view.model.bean.Usuario;

public class Menu extends AppCompatActivity {

    ViewGroup container;
    Button btnEditInfo;
    Button btnSaveAlt;
    Button logout;

    Switch switchPassword;

    EditText edtNameView;
    EditText edtEmailView;
    EditText edtPasswordView;
    TextView txtSenha;
    TextView nameView;
    TextView emailView;
    LinearLayout edtForm;
    LinearLayout showDataForm;
    Usuario user;
    String nome, email;
    int senha;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Usuario userOld;
    Usuario userAlter;
    UsuarioController controller;
    boolean formCheck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //na tela criar um botão alterar que quando clicado altera os dados do usuário no banco, emita
        //
        //uma mensagem de confimação
        init();
        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showForm()){
                    TransitionManager.beginDelayedTransition(container);
                    btnSaveAlt.setVisibility(View.VISIBLE);
                    txtSenha.setVisibility(View.VISIBLE);
                    edtForm.setVisibility(View.VISIBLE);
                    showDataForm.setVisibility(View.INVISIBLE);
                    setDataUser(nome, email, String.valueOf(senha));
                }else{
                    TransitionManager.beginDelayedTransition(container);
                    btnSaveAlt.setVisibility(View.INVISIBLE);
                    txtSenha.setVisibility(View.INVISIBLE);
                    edtForm.setVisibility(View.INVISIBLE);
                    showDataForm.setVisibility(View.VISIBLE);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSaveAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAlter = new Usuario(edtNameView.getText().toString(),
                        edtEmailView.getText().toString(), Integer.parseInt(edtPasswordView.getText().toString()));

                controller.alterar(userOld, userAlter);

                Usuario newUser = controller.getUser(userAlter);

                nome = newUser.getNome();
                email = newUser.getEmail();
                senha = newUser.getSenha();

                setDataUser(nome, email, String.valueOf(senha));

                formCheck = false;

                TransitionManager.beginDelayedTransition(container);
                btnSaveAlt.setVisibility(View.INVISIBLE);
                txtSenha.setVisibility(View.INVISIBLE);
                edtForm.setVisibility(View.INVISIBLE);
                showDataForm.setVisibility(View.VISIBLE);

                editor.putString("usuario", newUser.getNome());
                editor.putString("email", newUser.getEmail());
                editor.putInt("senha", newUser.getSenha());
                editor.apply();

                Toast.makeText(Menu.this, "Alteração Feita com Sucesso", Toast.LENGTH_SHORT).show();

            }
        });

        switchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int inputType;
                if (isChecked) {
                    inputType = InputType.TYPE_CLASS_NUMBER;
                    edtPasswordView.setTransformationMethod(null);
                } else {
                    inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
                    edtPasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edtPasswordView.setInputType(inputType);
            }
        });


    }

    public void setDataUser(String userNome, String userEmail, String userSenha){
        edtNameView.setText(userNome);
        edtEmailView.setText(userEmail);
        edtPasswordView.setText(String.valueOf(userSenha));

        nameView.setText(userNome);
        emailView.setText(userEmail);

        userOld = new Usuario(userNome, userEmail, Integer.parseInt(userSenha));
    }
    public boolean showForm(){
        return formCheck = !formCheck;
    }
    public void init(){

        //Controller
        controller = new UsuarioController();
        //Layouts
        container = findViewById(R.id.menu_container);
        edtForm = findViewById(R.id.edit_form);
        showDataForm = findViewById(R.id.show_form_data);

        //EditTexts
        edtNameView = findViewById(R.id.edtViewName);
        edtEmailView = findViewById(R.id.edtViewEmail);
        edtPasswordView = findViewById(R.id.edtViewSenha);

        //Buttons
        btnEditInfo = findViewById(R.id.btn_edt_info);
        btnSaveAlt = findViewById(R.id.btn_save_alt);
        logout = findViewById(R.id.logout);

        //TextViews
        txtSenha = findViewById(R.id.txt_senha);
        nameView = findViewById(R.id.viewName);
        emailView = findViewById(R.id.viewEmail);

        //Switch
        switchPassword = findViewById(R.id.switch_password);

        //SharedPreferences
        sharedPref = getSharedPreferences(String.valueOf(R.string.pref_key), MODE_PRIVATE);
        editor = sharedPref.edit();

        nome = sharedPref.getString("usuario", "null");
        email = sharedPref.getString("email", "null");
        senha = sharedPref.getInt("senha", 0);

        nameView.setText(nome);
        emailView.setText(email);

        userOld = new Usuario(nome, email, senha);

        //Log.e("User: ",  email+ ", " + nome + ", " + senha+" ");

    }
}