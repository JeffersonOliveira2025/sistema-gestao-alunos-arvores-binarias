package com.unicsul.projeto.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

@Service
public class CampusManager {

    private final Map<String, ArvoreBinariaCampus> campis;

    public CampusManager() {
        campis = new HashMap<>();
        // Inicializa a árvore estritamente para cada um dos 7 campi oficiais obrigatórios
        campis.put("Anália Franco", new ArvoreBinariaCampus());
        campis.put("Guarulhos", new ArvoreBinariaCampus());
        campis.put("Liberdade", new ArvoreBinariaCampus());
        campis.put("Paulista", new ArvoreBinariaCampus());
        campis.put("São Miguel", new ArvoreBinariaCampus());
        campis.put("Santo Amaro", new ArvoreBinariaCampus());
        campis.put("Villa Lobos", new ArvoreBinariaCampus());
    }

    // Retorna a lista incluindo o separador visual no topo do ComboBox
    public String[] getListaCampis() {
        return new String[] {
            "--- Selecione o Campus ---",
            "Anália Franco",
            "Guarulhos",
            "Liberdade",
            "Paulista",
            "São Miguel",
            "Santo Amaro",
            "Villa Lobos"
        };
    }

    /**
     * Valida se a matrícula ou o nome completo já existem em QUALQUER campus do sistema.
     * Retorna uma mensagem descritiva de erro se houver duplicidade, ou null se estiver livre.
     */
    public String validarDuplicidade(String matriculaStr, String nomeCompleto) {
        String matriculaLimpa = matriculaStr.trim();
        String nomeLimpo = nomeCompleto.toLowerCase().trim();

        for (Map.Entry<String, ArvoreBinariaCampus> entry : campis.entrySet()) {
            String nomeCampus = entry.getKey();
            List<String> alunos = entry.getValue().listarEmOrdem();
            
            for (String alunoInfo : alunos) {
                String infoLower = alunoInfo.toLowerCase();
                
                // Verifica se a matrícula já existe
                if (alunoInfo.contains(matriculaLimpa)) {
                    return "Erro: Já existe um registro com a Matrícula " + matriculaLimpa + " no " + nomeCampus + ".";
                }

                // Verifica se o Nome Completo já existe no sistema
                if (infoLower.contains(nomeLimpo)) {
                    return "Erro: O discente '" + nomeCompleto + "' já possui matrícula ativa no " + nomeCampus + ". Não é permitido duplicar o cadastro.";
                }
            }
        }
        return null; // Nenhuma duplicidade encontrada
    }

    // Cadastra o aluno no campus escolhido formatando o registro para a árvore
    public boolean cadastrarAluno(String campus, int matricula, String nome) {
        if (campus == null || campus.equals("--- Selecione o Campus ---")) {
            return false;
        }

        // Validação global prévia de duplicidade antes de inserir
        String erroDuplicidade = validarDuplicidade(String.valueOf(matricula), nome);
        if (erroDuplicidade != null) {
            JOptionPane.showMessageDialog(null, erroDuplicidade, "Aluno já Cadastrado", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        ArvoreBinariaCampus arvore = campis.get(campus);
        if (arvore != null) {
            // Formata o registro unificado para a árvore binaria aceitar (Matrícula - Nome)
            String registroFormatado = matricula + " - " + nome;
            return arvore.inserir(registroFormatado);
        }
        return false;
    }

    // Procura o aluno em todos os campi e retorna o nome do campus onde foi achado (ou null)
    public String localizarAlunoEmTodosCampis(String termo) {
        String termoBusca = termo.toLowerCase().trim();
        for (Map.Entry<String, ArvoreBinariaCampus> entry : campis.entrySet()) {
            List<String> alunos = entry.getValue().listarEmOrdem();
            for (String aluno : alunos) {
                if (aluno.toLowerCase().contains(termoBusca)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    // Lista os alunos de um campus específico em ordem alfabética com validação
    public List<String> listarAlunosCampus(String campus) {
        if (campus == null || campus.equals("--- Selecione o Campus ---")) {
            return null;
        }
        ArvoreBinariaCampus arvore = campis.get(campus);
        if (arvore != null) {
            return arvore.listarEmOrdem();
        }
        return null;
    }
}