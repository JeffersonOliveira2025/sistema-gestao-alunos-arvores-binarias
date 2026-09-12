package com.unicsul.projeto.view;

import com.unicsul.projeto.service.CampusManager;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

@Component
public class TelaPrincipalView extends JFrame {

    private final CampusManager campusManager;

    private JTextField txtMatriculaCadastro, txtNomeCadastro, txtNomeBusca;
    private JComboBox<String> cbCampisCadastro, cbCampisListagem;
    private JTextArea txtAreaResultados;

    private final Color COR_PRIMARIA = new Color(139, 0, 0);      
    private final Color COR_SECUNDARIA = new Color(240, 242, 245);  
    private final Color COR_TEXTO_TITULO = new Color(80, 0, 0);     

    public TelaPrincipalView(CampusManager campusManager) {
        this.campusManager = campusManager;
        setTitle("Portal Acadêmico | Sistema de Gestão por Árvores Binárias");
        setSize(750, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(COR_SECUNDARIA);

        JPanel painelBanner = new JPanel(new BorderLayout());
        painelBanner.setBackground(COR_PRIMARIA);
        painelBanner.setBorder(new EmptyBorder(12, 15, 12, 15));
        JLabel lblTituloTopo = new JLabel("UNIVERSIDADE - GESTÃO DE CADASTRO DE ALUNOS CAMPUS UNICSUL");
        lblTituloTopo.setForeground(Color.WHITE);
        lblTituloTopo.setFont(new Font("Serif", Font.BOLD, 18));
        painelBanner.add(lblTituloTopo, BorderLayout.WEST);
        
        painelPrincipal.add(painelBanner);
        painelPrincipal.add(Box.createVerticalStrut(12));

        // --- SEÇÃO 1: CADASTRO ---
        JPanel painelCadastro = criarPainelEstilizado("1. Registro Acadêmico de Novo Aluno");
        painelCadastro.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCadastro.add(new JLabel("Nº Matrícula:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtMatriculaCadastro = new JTextField(20);
        
        // RESTRIÇÃO: O campo Matrícula aceita apenas números
        txtMatriculaCadastro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        painelCadastro.add(txtMatriculaCadastro, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNomeCadastro = new JTextField(20);
        
        // RESTRIÇÃO: O campo Nome Completo aceita apenas letras e espaços
        txtNomeCadastro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        painelCadastro.add(txtNomeCadastro, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("Unidade / Campus:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        cbCampisCadastro = new JComboBox<>(campusManager.getListaCampis());
        painelCadastro.add(cbCampisCadastro, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        JButton btnCadastrar = criarBotaoEstilizado("Efetuar Matrícula", COR_PRIMARIA);
        painelCadastro.add(btnCadastrar, gbc);

        painelPrincipal.add(painelCadastro);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // --- SEÇÃO 2: BUSCA GLOBAL ---
        JPanel painelBusca = criarPainelEstilizado("2. Consulta de Discentes (Todos os Campus)");
        painelBusca.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        painelBusca.add(new JLabel("Pesquisar Aluno:"));
        txtNomeBusca = new JTextField(20);
        painelBusca.add(txtNomeBusca);
        JButton btnBuscar = criarBotaoEstilizado("Pesquisar Sistema", new Color(50, 50, 50));
        painelBusca.add(btnBuscar);

        painelPrincipal.add(painelBusca);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // --- SEÇÃO 3: LISTAGEM ---
        JPanel painelListagem = criarPainelEstilizado("3. Unidade de Campus (Árvore In-Order)");
        painelListagem.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        painelListagem.add(new JLabel("Selecione o Campus:"));
        cbCampisListagem = new JComboBox<>(campusManager.getListaCampis());
        painelListagem.add(cbCampisListagem);
        JButton btnListar = criarBotaoEstilizado("Gerar Listagem", new Color(50, 50, 50));
        painelListagem.add(btnListar);

        painelPrincipal.add(painelListagem);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // --- SEÇÃO 4: RESULTADOS ---
        JPanel painelResultados = criarPainelEstilizado("Registros do Sistema");
        painelResultados.setLayout(new BorderLayout());
        
        txtAreaResultados = new JTextArea(6, 20);
        txtAreaResultados.setEditable(false);
        txtAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaResultados);
        painelResultados.add(scrollPane, BorderLayout.CENTER);

        painelPrincipal.add(painelResultados);

        add(painelPrincipal);

        // --- AÇÕES DOS BOTÕES ---
        btnCadastrar.addActionListener(e -> {
            String nome = txtNomeCadastro.getText().trim();
            String matriculaStr = txtMatriculaCadastro.getText().trim();
            String campusSelecionado = (String) cbCampisCadastro.getSelectedItem();

            if (matriculaStr.isEmpty() || nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha a matrícula e o nome do aluno.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (campusSelecionado == null || campusSelecionado.equals("--- Selecione o Campus ---")) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um campus válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int matricula = Integer.parseInt(matriculaStr);

                // Utilizando a assinatura correta do método cadastrarAluno(campus, matricula, nome)
                boolean cadastrado = campusManager.cadastrarAluno(campusSelecionado, matricula, nome);

                if (cadastrado) {
                    JOptionPane.showMessageDialog(this, "Aluno matriculado com sucesso no campus " + campusSelecionado + "!");
                    
                    // Limpeza automática dos campos para o próximo cadastro
                    txtMatriculaCadastro.setText("");
                    txtNomeCadastro.setText("");
                    cbCampisCadastro.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Operação Negada: Nome já Cadastrado!", "Aluno já Cadastrado", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "A matrícula deve conter apenas números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            String termoBusca = txtNomeBusca.getText().trim();
            if (termoBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome ou matrícula para a consulta.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String campusEncontrado = campusManager.localizarAlunoEmTodosCampis(termoBusca);
            if (campusEncontrado != null) {
                List<String> alunosNoCampus = campusManager.listarAlunosCampus(campusEncontrado);
                String registroEncontrado = "";
                for (String a : alunosNoCampus) {
                    if (a.toLowerCase().contains(termoBusca.toLowerCase())) {
                        registroEncontrado = a;
                        break;
                    }
                }
                
                txtAreaResultados.setText("[STATUS: LOCALIZADO]\n -> Registro: " + registroEncontrado + "\n -> Unidade Vinculada: " + campusEncontrado);
            } else {
                txtAreaResultados.setText("[STATUS: NÃO LOCALIZADO]\nRegistro não encontrado em nenhuma das bases dos campi.");
            }
        });

        btnListar.addActionListener(e -> {
            String campusSelecionado = (String) cbCampisListagem.getSelectedItem();
            
            if (campusSelecionado == null || campusSelecionado.equals("--- Selecione o Campus ---")) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um campus válido para listar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> alunos = campusManager.listarAlunosCampus(campusSelecionado);

            StringBuilder sb = new StringBuilder();
            sb.append("=== RELATÓRIO DE ALUNOS: CAMPUS ").append(campusSelecionado.toUpperCase()).append(" ===\n");
            if (alunos == null || alunos.isEmpty()) {
                sb.append("Nenhum registro acadêmico encontrado nesta unidade.");
            } else {
                int contador = 1;
                for (String aluno : alunos) {
                    sb.append(String.format(" %02d. %s\n", contador++, aluno));
                }
            }
            txtAreaResultados.setText(sb.toString());
        });
    }

    private JPanel criarPainelEstilizado(String titulo) {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        titulo,
                        0, 0,
                        new Font("Serif", Font.BOLD, 13),
                        COR_TEXTO_TITULO
                ),
                new EmptyBorder(4, 4, 4, 4)
        ));
        painel.setBackground(Color.WHITE);
        return painel;
    }

    private JButton criarBotaoEstilizado(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        return botao;
    }
}