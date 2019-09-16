package com.zebra.printstationcard.Medic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.printstationcard.BluetoothPrinter.BluetoothMainActivity;
import com.zebra.printstationcard.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MedicActivity extends AppCompatActivity {


    private ImageView imgPrimeiroSocorros;
    private EditText etTipoExame, etNomePaciente, etDescricao;
    private Button btnGoToPrinter;
    private TextView tvOlaMedico;

    private String tipoExame, nomePaciente, Descricao;

    private String userName = "";
    private String exame = "";

    private ArrayList<String> Arquivos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medic);

        init();

    }


    private void init(){

        //RECEIVE DATA
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            userName = extras.getString("userName");
        }

        imgPrimeiroSocorros = (ImageView) findViewById(R.id.imgPrimeiroSocorro);
        etTipoExame = (EditText) findViewById(R.id.etTipoExame);
        etNomePaciente = (EditText) findViewById(R.id.etNomePaciente);
        etDescricao = (EditText) findViewById(R.id.etDescricao);
        btnGoToPrinter = (Button) findViewById(R.id.btnGoToPrinter);
        tvOlaMedico = (TextView) findViewById(R.id.txtOlaMedico);
        tvOlaMedico.setText("Olá, " + userName);

        btnGoToPrinter.setOnClickListener(this::onClick);

        etNomePaciente.setText("Daniel Paciente");
        etTipoExame.setText("Exame de sangue");
        etDescricao.setText("Paciente estava sofrendo problemas no organismo devido a alta quantidade de gadisse inserida \n" +
                "mas agora\njá esta se sentindo\nbem melhor do que nunca\npois conquistou a\nM\nO\nR\nE\nN\nA\n<3");
    }

    private void setTextos(){
        etNomePaciente.setText("Daniel Paciente");
        etTipoExame.setText("Exame de sangue");
        etDescricao.setText("Paciente estava sofrendo problemas no organismo devido a alta quantidade de gadisse inserida \n" +
                "mas agora\njá esta se sentindo\nbem melhor do que nunca\npois conquistou a\nM\nO\nR\nE\nN\nA\n<3");
    }

    private void getString(){
        tipoExame = etTipoExame.getText().toString();
        nomePaciente = etNomePaciente.getText().toString();
        Descricao = etDescricao.getText().toString();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGoToPrinter:
                Intent printIntent = new Intent(this, BluetoothMainActivity.class);
                /*exame = String.format("%-15s", userName + " \n");
                exame = exame + String.format("%-15s",etNomePaciente.getText().toString() + " \n");
                exame = exame + String.format("%-15s",etTipoExame.getText().toString() + " \n");
                exame = exame + String.format("%-15s",etDescricao.getText().toString() + " \n");*/

                exame = userName + " \n";
                exame = exame + etNomePaciente.getText().toString() + " \n";
                exame = exame + etTipoExame.getText().toString() + " \n";
                exame = exame + etDescricao.getText().toString() + " \n";
                //Toast.makeText(this, "EXAME: " +exame, Toast.LENGTH_SHORT).show();
                String laudoMedico = "NOME:" + etNomePaciente.getText().toString() +"£"
                                    + "TIPOEXAME:" + etTipoExame.getText().toString() +"£"
                                    + "DESCRICAO:" + etDescricao.getText().toString() +";";

                click_Salvar(laudoMedico);

                printIntent.putExtra("imprimir", exame);
                startActivity(printIntent);
                break;
        }
    }

    public void click_Salvar(String laudoMedico) {
        String lstrNomeArq;
        File arq;
        byte[] dados;
        try {
            lstrNomeArq = "arquivo.txt";

            //arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
            arq = new File(Environment.getExternalStoragePublicDirectory("ProjetoMedico/Medicos"), lstrNomeArq);

            FileOutputStream fos;


            dados = laudoMedico.getBytes();

            fos = new FileOutputStream(arq, true);
            fos.write(dados);
            fos.flush();
            fos.close();
            Mensagem("Dados salvos com sucesso!");
            Listar();
        } catch (Exception e) {
            Mensagem("Error: " + e.getMessage());
        }
    }

    private void Mensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void Listar() {
        File diretorio = new File(ObterDiretorio());
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            int length = arquivos.length;
            for (int i = 0; i < length; ++i) {
                File f = arquivos[i];
                if (f.isFile()) {
                    Arquivos.add(f.getName());
                }
            }

        }
    }

    private String ObterDiretorio() {
        File root = android.os.Environment.getExternalStorageDirectory();
        return root.toString();
    }

}
