package br.app.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import br.app.view.controller.UsuarioController;
import br.app.view.model.bean.Usuario;

public class MainActivity extends AppCompatActivity {

    ViewGroup container;
    Switch vaiCadastrar;
    EditText email;
    EditText senha;
    EditText apelido;
    TextView txtRegister;
    Button button;
    boolean check = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UsuarioController controller;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        vaiCadastrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                check = vaiCadastrar.isChecked();
                if (check) {
                    TransitionManager.beginDelayedTransition(container);
                    apelido.setVisibility(View.VISIBLE);
                    txtRegister.setVisibility(View.VISIBLE);

                } else {
                    zerarCampos();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificacaoCampos()){
                    if(check){
                        usuario = new Usuario(apelido.getText().toString(), email.getText().toString(), Integer.parseInt(senha.getText().toString()));
                        if(cadastrarUsuario(usuario.getNome(), usuario.getEmail(), usuario.getSenha())){
                            zerarCampos();
                            Toast.makeText(MainActivity.this, R.string.msg_success, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, R.string.msg_error_register, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Usuario userLog = logar(email.getText().toString(), Integer.parseInt(senha.getText().toString()));
                        if( userLog != null){

                            editor.putString("usuario", userLog.getNome());
                            editor.putString("email", userLog.getEmail());
                            editor.putInt("senha", userLog.getSenha());
                            editor.apply();

                            Intent i = new Intent(getApplicationContext(), Menu.class);
                                startActivity(i);
                                finish();
                        }else{
                            Toast.makeText(MainActivity.this, R.string.msg_error_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public boolean verificacaoCampos(){

        if((apelido.getVisibility() == View.INVISIBLE && email.getText().toString().isEmpty() && senha.getText().toString().isEmpty())){
            email.setError("Preencha o campo Email obrigatório!");
            senha.setError("Preencha o campo senha corretamente!");
            return true;
        }

        if(apelido.getVisibility() == View.VISIBLE && apelido.getText().toString().isEmpty() && email.getText().toString().isEmpty() && senha.getText().toString().isEmpty()){
            email.setError("Preencha o campo Email obrigatório!");
            apelido.setError("Preencha o campo Apelido Obrigatório!");
            senha.setError("Preencha o campo senha corretamente!");
            return true;
        }

        if(apelido.getVisibility() == View.VISIBLE && apelido.getText().toString().isEmpty()){
            apelido.setError("Preencha o campo Apelido Obrigatório!");
            return true;
        }

        if(email.getText().toString().isEmpty()){
            email.setError("Preencha o campo Email obrigatório!");
            return true;
        }

        if(senha.getText().toString().isEmpty()){
            senha.setError("Preencha o campo senha corretamente!");
            return true;
        }

        return false;
    }
    public void zerarCampos(){
        TransitionManager.beginDelayedTransition(container);
        apelido.setVisibility(View.INVISIBLE);
        txtRegister.setVisibility(View.INVISIBLE);
        vaiCadastrar.setChecked(false);
        check = false;
        email.setText("");
        apelido.setText("");
        senha.setText("");
    }
    public boolean cadastrarUsuario(String apelido, String email, int senha){
        Usuario usuario = new Usuario(apelido, email, senha);
        return controller.cadastrarUsuario(usuario);
    }

    public Usuario logar(String email, int senha){
        return controller.logar(email, senha);
    }

    public void init(){
        vaiCadastrar = findViewById(R.id.cadastro_switch);
        email = findViewById(R.id.editTextTextEmail);
        senha = findViewById(R.id.editTextTextPassword);
        apelido = findViewById(R.id.editTextTextApelido);
        button = findViewById(R.id.button);
        txtRegister = findViewById(R.id.txt_register);
        controller = new UsuarioController();
        container = findViewById(R.id.container_group);


        sharedPreferences = getSharedPreferences(String.valueOf(R.string.pref_key), MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}