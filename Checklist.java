public class Checklist {
    private boolean pneus;
    private boolean oleo;
    private boolean lataria;
    private boolean combustivel;
    private String observacoes;

    public Checklist(boolean pneus, boolean oleo, boolean lataria, boolean combustivel, String observacoes) {
        this.pneus = pneus;
        this.oleo = oleo;
        this.lataria = lataria;
        this.combustivel = combustivel;
        this.observacoes = observacoes;
    }

    public boolean isPneus() {
        return pneus;
    }

    public void setPneus(boolean pneus) {
        this.pneus = pneus;
    }

    public boolean isOleo() {
        return oleo;
    }

    public void setOleo(boolean oleo) {
        this.oleo = oleo;
    }

    public boolean isLataria() {
        return lataria;
    }

    public void setLataria(boolean lataria) {
        this.lataria = lataria;
    }

    public boolean isCombustivel() {
        return combustivel;
    }

    public void setCombustivel(boolean combustivel) {
        this.combustivel = combustivel;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}