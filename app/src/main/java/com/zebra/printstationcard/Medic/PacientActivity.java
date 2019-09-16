package com.zebra.printstationcard.Medic;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.printstationcard.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PacientActivity extends AppCompatActivity {


    private ImageView imgPaciente;
    private TextView tvDescricao, tvOlaPaciente;
    private ListView listViewPaciente;
    private Button btnMostrarExames;

    ArrayList<String> listaConsultas = new ArrayList<>();
    ArrayList<String> listaDescricoes = new ArrayList<>();

    private String userName = "";
    private String userID = "";
    private String dados[] = new String[]{"Exame esta tudo bem", "somente frebe leve", "vai morrer logo", "apesar de passar bem, passa mal"};

    //Historico de consultas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient);

        tvDescricao = (TextView) findViewById(R.id.tvDescricao);
        tvOlaPaciente = (TextView) findViewById(R.id.tvOlaPaciente);
        listViewPaciente = (ListView) findViewById(R.id.listViewPaciente);
        btnMostrarExames = (Button) findViewById(R.id.btnMostrarExames);

        btnMostrarExames.setVisibility(View.GONE);

        btnMostrarExames.setOnClickListener(view -> {
            listViewPaciente.setVisibility(View.VISIBLE);
            btnMostrarExames.setVisibility(View.GONE);
            tvDescricao.setVisibility(View.GONE);
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            userName = extras.getString("userName");
            userID = extras.getString("userID");
        }
        tvOlaPaciente.setText("Ola, " + userName);
        /*
        listaConsultas.add("Exame de sangue");
        listaConsultas.add("Consulta geral");
        listaConsultas.add("Radiografia");
        listaConsultas.add("Consulta geral");
        */

        click_Carregar();

    }

    private void setListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaConsultas);
        listViewPaciente.setAdapter(adapter);

        listViewPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvDescricao.setText(listaDescricoes.get(i));{
                    listViewPaciente.setVisibility(View.GONE);
                    btnMostrarExames.setVisibility(View.VISIBLE);
                    tvDescricao.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void click_Carregar()
    {
        List<String> datas = new ArrayList<>();

        String lstrNomeArq;
        File arq;
        String lstrlinha;
        try
        {


            //arq = new File(Environment.getExternalStorageDirectory(), "arquivo.txt");
            arq = new File(Environment.getExternalStoragePublicDirectory("ProjetoMedico/Medicos"), "arquivo.txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));

            String[] dataStringSplited = {};
            String[] dataUserSplited = {};

            while ((lstrlinha = br.readLine()) != null)
            {
                dataStringSplited = lstrlinha.split(";");
                for (String dataString : dataStringSplited) {
                    dataUserSplited = dataString.split("£");
                    for(String userString : dataUserSplited){
                        datas.add(userString);
                    }
                }
            }
            //Toast.makeText(mContext, "TAMANHO: "  + dataUserSplited.length, Toast.LENGTH_LONG).show();
            int count = 0;
            if(datas != null){
                for(String dataInfos : datas){
                    count++;
                    if(dataInfos.equals("NOME:"+userName)){
                        //Toast.makeText(this, "Here i go: " + dataInfos + " NOMESS: " + (dataInfos.substring(dataInfos.lastIndexOf(":") + 1)), Toast.LENGTH_SHORT).show();
                        //if((dataInfos.substring(dataInfos.lastIndexOf(":") + 1)).equals(userName))
                        //tvTest.setText("USER ID AQUI: >> " + dataInfos + "Plus TEST: " + datas.indexOf(dataInfos) + " PROXIMO E NOME : " + datas.get(datas.indexOf(dataInfos)+1));
                           /* for(int i = 0; i<dataUserSplited.length; i++) {
                                String info = datas.get((datas.indexOf(dataInfos)+(3*count)) + i);
                                count++;
                                String infoSubstring = info.substring(info.lastIndexOf(":") + 1);
                                listaConsultas.add(infoSubstring);
                                //tvTest.setText(tvTest.getText() + infoSubstring + "<>");
                                switch (i) {
                                    case 1:
                                        //listaConsultas.add(infoSubstring);
                                        break;
                                    case 2:
                                        listaDescricoes.add(infoSubstring);
                                        break;
                                }
                            }*/
                        String info = datas.get(count);
                        String infoSubstring = info.substring(info.lastIndexOf(":") + 1);
                        listaConsultas.add(infoSubstring);
                        info = datas.get(count+1);
                        infoSubstring = info.substring(info.lastIndexOf(":") + 1);
                        listaDescricoes.add(infoSubstring);
                    }
                }
            }
            setListView();
            //Mensagem("Dados carregados com sucesso!");
        }
        catch (Exception e)
        {
            Mensagem("Error: " + e.getMessage());
        }
    }

    private void Mensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
