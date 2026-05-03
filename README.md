# Report App - VF Corp (AI Integrated)

<p align="center">
  <img src="https://img.shields.io/badge/Status-Produção%20Finalizada-green" alt="Status">
  <img src="https://img.shields.io/badge/Stack-Android%20Java-blue" alt="Stack">
  <img src="https://img.shields.io/badge/Parceiro-Pensar%20Consultoria-orange" alt="Parceiro">
</p>

O **Report App** é uma plataforma de gestão de incidentes técnicos projetada para otimizar a comunicação entre prestadores de serviço e clientes finais. O sistema substitui fluxos informais e descentralizados por um canal estruturado, focado em rastreabilidade, evidências visuais e inteligência de dados.

## A Solução

A aplicação resolve o problema de perda de informações em canais de mensagens comuns. Ela permite o registro de falhas com evidências fotográficas e descrições técnicas, que são processadas instantaneamente por um motor de Inteligência Artificial para acelerar o tempo de resposta do suporte técnico.

### Inteligência Artificial (Gemini Engine)

Integramos o modelo de linguagem do Google Gemini para transformar dados brutos em decisões estratégicas:

- **Triagem Automatizada**: A IA analisa o contexto do report e define a criticidade em tempo real: 🔴 **ALTA** (Bloqueios/Login), 🟠 **MÉDIA** (Layout/Usabilidade) ou 🟡 **BAIXA** (Ajustes Estéticos).
- **Sugestão de Resolução**: O sistema gera automaticamente uma recomendação técnica para o desenvolvedor, reduzindo o tempo de diagnóstico.
- **Arquitetura Resiliente**: Parsing de dados em Java com higienização via Regex, garantindo que o fluxo de suporte nunca seja interrompido por falhas de integração.

## Stack Técnica

- **Mobile**: Android Nativo (Java).
- **IA**: Google Generative AI (Gemini Pro API).
- **Versionamento**: Git com padrões de documentação.
- **Metodologia**: Ciclos iterativos com foco em Qualidade de Software (QA).

## Funcionalidades e Impacto de Negócio

| Recurso | Benefício |
| :--- | :--- |
| **Documentação Fotográfica** | Prova incontestável do erro, evitando ambiguidades no atendimento. |
| **Semáforo de Criticidade** | Organização automática da fila de trabalho por urgência real. |
| **Insights do Gemini** | Redução da carga cognitiva dos técnicos através de sugestões automatizadas. |

## Garantia de Qualidade (QA)

Sob a perspectiva de um **QA Analyst**, o **Report App** foi submetido a uma estratégia de testes multifacetada para garantir a confiabilidade e a robustez do produto final:

*   **Testes Funcionais:** Validação pontual de cada requisito do sistema, incluindo o fluxo de captura de mídia, validação de campos obrigatórios e submissão de formulários.
*   **Testes de Integração:** Verificação da comunicação entre o front-end Android e o motor de Inteligência Artificial do Google Gemini, garantindo a integridade do tráfego de dados via JSON.
*   **Testes de Usabilidade:** Foco na experiência do usuário (UX) para garantir que microempreendedores consigam reportar incidentes de forma intuitiva, rápida e sem atritos.
*   **Testes de sanidade e regressão:** Executados a cada nova iteração no motor de IA para garantir que a implementação de novas lógicas de triagem não afetasse as funções base de câmera e persistência de dados.
*   **Testes de aceite:** Homologação final realizada em cenário real pela **Pensar Consultoria**, validando a ferramenta como apta para substituir processos manuais e informais.
*   **Feedback Loop:** Coleta de métricas qualitativas e quantitativas para o refinamento contínuo das sugestões técnicas e da precisão da classificação gerada pela IA.

---
**Desenvolvido por: Fábio Gabriel** -
*QA Analyst*
