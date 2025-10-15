import java.time.LocalDateTime;

public class Movimentacao  {
    private int id;
    private Veiculo veiculo;
    private Colaborador colaborador;
    private LocalDateTime retirada;
    private LocalDateTime devolucao;
    private Checklist checklistSaida;
    private Checklist checklistRetorno;

    public Movimentacao(Veiculo veiculo, Colaborador colaborador, Checklist checklistSaida) {
        this.veiculo = veiculo;
        this.colaborador = colaborador;
        this.retirada = LocalDateTime.now();
        this.checklistSaida = checklistSaida;
    }

    public void registrarDevolucao(Checklist checklistRetorno) {
        this.devolucao = LocalDateTime.now();
        this.checklistRetorno = checklistRetorno;
        veiculo.setDisponivel(true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getRetirada() {
        return retirada;
    }

    public LocalDateTime getDevolucao() {
        return devolucao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public Checklist getChecklistSaida() {
        return checklistSaida;
    }

    public Checklist getChecklistRetorno() {
        return checklistRetorno;
    }
    public void setRetirada(LocalDateTime retirada) {
        this.retirada = retirada;
    }
}