package com.vfcorp.bugreport;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;

public class AITriageService {

    private static final String API_KEY = "API KEY AQUI";

    public static ListenableFuture<GenerateContentResponse> getTriage(String userDescription) {
        SafetySetting safety = new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH);

        GenerationConfig config = new GenerationConfig.Builder()
                .build();

        GenerativeModel gm = new GenerativeModel(
                "gemini-2.5-flash",
                API_KEY,
                config,
                Collections.singletonList(safety)
        );

        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        String prompt = "Responda apenas com JSON para o erro: '" + userDescription + "'. " +
                "Se for login, prioridade: ALTA. Se for imagem/layout, prioridade: MEDIA. " +
                "Se for escrita, prioridade: BAIXA. " +
                "Formato: {\"prioridade\": \"VALOR\", \"sugestao_tecnica\": \"SUGESTAO\"}";

        Content content = new Content.Builder().addText(prompt).build();
        return model.generateContent(content);
    }
}