package com.vfcorp.bugreport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Report implements Serializable {
    private String descricao;
    private String prioridade;
    private String sugestaoTecnica;
    private String dataHoraFormatada;

    public Report(String descricao) {
        this.descricao = descricao;
        this.prioridade = "PENDENTE";
        this.sugestaoTecnica = "Aguardando análise da IA...";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault());
        this.dataHoraFormatada = sdf.format(new Date());
    }

    public String getDescricao() { return descricao; }
    public String getPrioridade() { return prioridade; }
    public String getSugestaoTecnica() { return sugestaoTecnica; }
    public String getDataHora() { return dataHoraFormatada; }

    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public void setSugestao(String sugestao) { this.sugestaoTecnica = sugestao; }

    public int getPrioridadeValor() {
        switch (prioridade.toUpperCase()) {
            case "ALTA": return 1;
            case "MEDIA": return 2;
            case "BAIXA": return 3;
            default: return 4;
        }
    }
}