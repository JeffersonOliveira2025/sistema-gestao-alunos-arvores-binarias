package com.unicsul.projeto;

import com.unicsul.projeto.view.TelaPrincipalView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.EventQueue;

@SpringBootApplication
public class ProjetoUnicsulApplication {

    public static void main(String[] args) {
        // Inicializa o contexto do Spring Boot desativando o modo headless para permitir janelas Swing
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ProjetoUnicsulApplication.class)
                .headless(false)
                .run(args);

        // Abre a Tela Principal utilizando a thread de eventos do Swing (EventQueue)
        EventQueue.invokeLater(() -> {
            TelaPrincipalView tela = context.getBean(TelaPrincipalView.class);
            tela.setVisible(true);
        });
    }
}