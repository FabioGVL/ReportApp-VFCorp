package com.vfcorp.bugreport;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.json.JSONObject;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardActivity extends AppCompatActivity {

    private LinearLayout containerReports;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        containerReports = findViewById(R.id.containerReports);

        findViewById(R.id.btnAnalisar).setOnClickListener(v -> {
            if (MainActivity.reportsEnviados.isEmpty()) {
                Toast.makeText(this, "A fila de reports está vazia.", Toast.LENGTH_SHORT).show();
            } else {
                iniciarTriageTecnica();
            }
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        atualizarInterface();
    }

    private void iniciarTriageTecnica() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pensar QA - Inteligência de Dados");
        progressDialog.show();

        progressDialog.setMessage("Sincronizando com o motor da IA...");
        new Handler().postDelayed(() -> progressDialog.setMessage("Avaliando impacto no negócio..."), 1500);
        new Handler().postDelayed(() -> progressDialog.setMessage("Definindo prioridades e soluções..."), 3000);

        processarAnaliseIA();
    }

    private void processarAnaliseIA() {
        AtomicInteger contador = new AtomicInteger(0);
        int total = MainActivity.reportsEnviados.size();

        for (int i = 0; i < total; i++) {
            final int index = i;
            Report r = MainActivity.reportsEnviados.get(index);

            // Força a análise apenas para o que não foi processado
            if (r.getPrioridade().equals("PENDENTE") || r.getPrioridade().equals("BAIXA")) {
                Futures.addCallback(AITriageService.getTriage(r.getDescricao()), new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        try {
                            String textResponse = result.getText();
                            // Limpeza para garantir que pegamos apenas o JSON
                            if (textResponse.contains("{") && textResponse.contains("}")) {
                                textResponse = textResponse.substring(textResponse.indexOf("{"), textResponse.lastIndexOf("}") + 1);
                            }

                            JSONObject json = new JSONObject(textResponse);

                            // Atualiza diretamente na lista global usando o index
                            MainActivity.reportsEnviados.get(index).setPrioridade(json.optString("prioridade", "BAIXA").toUpperCase());
                            MainActivity.reportsEnviados.get(index).setSugestao(json.optString("sugestao_tecnica", "Verificar logs."));

                        } catch (Exception e) {
                            android.util.Log.e("QA_DEBUG", "Erro no JSON: " + e.getMessage());
                            MainActivity.reportsEnviados.get(index).setPrioridade("BAIXA");
                            MainActivity.reportsEnviados.get(index).setSugestao("Falha na análise automática.");
                        } finally {
                            concluirTarefa(contador.incrementAndGet(), total);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        concluirTarefa(contador.incrementAndGet(), total);
                    }
                }, ContextCompat.getMainExecutor(this));
            } else {
                concluirTarefa(contador.incrementAndGet(), total);
            }
        }
    }

    private void concluirTarefa(int atual, int total) {
        if (atual >= total) {
            new Handler().postDelayed(() -> {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Collections.sort(MainActivity.reportsEnviados, (r1, r2) ->
                        Integer.compare(r1.getPrioridadeValor(), r2.getPrioridadeValor()));

                atualizarInterface();
                Toast.makeText(this, "Painel atualizado!", Toast.LENGTH_SHORT).show();
            }, 800);
        }
    }

    private void atualizarInterface() {
        containerReports.removeAllViews();
        for (Report r : MainActivity.reportsEnviados) {
            TextView card = new TextView(this);
            card.setBackgroundColor(Color.WHITE);
            card.setPadding(50, 50, 50, 50);
            card.setElevation(12f);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.setMargins(25, 15, 25, 45);
            card.setLayoutParams(lp);

            String icone = r.getPrioridade().equals("ALTA") ? "🔴" :
                    r.getPrioridade().equals("MEDIA") ? "🟠" :
                            r.getPrioridade().equals("BAIXA") ? "🟡" : "🔵";

            String preview = r.getSugestaoTecnica();
            if (preview.length() > 48) {
                preview = preview.substring(0, 45) + "...";
            }

            String infoCard = "📋 ITEM DE INCIDENTE\n" +
                    "📅 Relato em: " + r.getDataHora() + "\n" +
                    "📝 Relato: " + r.getDescricao() + "\n" +
                    icone + " Criticidade: " + r.getPrioridade() + "\n" +
                    "💡 Solução IA: " + preview + "\n" +
                    "📸 Evidência: imagem_anexa.png";

            card.setText(infoCard);
            card.setTextColor(Color.parseColor("#333333"));
            card.setTextSize(14);

            card.setOnClickListener(v -> abrirDetalhamento(r));

            containerReports.addView(card);
        }
    }

    private void abrirDetalhamento(Report r) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 40, 60, 40);

        TextView textoFull = new TextView(this);
        textoFull.setTextSize(15);
        textoFull.setTextColor(Color.BLACK);
        textoFull.setText("🕒 Timestamp: " + r.getDataHora() + "\n\n" +
                "📝 Relato Original:\n" + r.getDescricao() + "\n\n" +
                "🤖 SOLUÇÃO RECOMENDADA:\n" + r.getSugestaoTecnica() + "\n\n" +
                "🖼️ EVIDÊNCIA DO TESTE:");

        ImageView previewImagem = new ImageView(this);
        previewImagem.setImageResource(android.R.drawable.ic_menu_gallery);
        previewImagem.setLayoutParams(new LinearLayout.LayoutParams(-1, 550));
        previewImagem.setPadding(0, 30, 0, 0);

        layout.addView(textoFull);
        layout.addView(previewImagem);

        new AlertDialog.Builder(this)
                .setTitle("Inspeção de Report")
                .setView(layout)
                .setPositiveButton("Fechar", null)
                .show();
    }
}