package main;

public class Processo {
    Integer id;
    String nome;
    String mensagem;
    public Processo(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Processo(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }


}
