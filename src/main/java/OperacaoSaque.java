public class OperacaoSaque extends Operacao {

    public OperacaoSaque(double valor) {
        super('s', valor);
    }

    @Override
    public String toString() {
        String str = this.getData() + "\t" + this.getTipo() + "\t" + this.getValor();

        return str;
    }

    @Override
    public double calculaTaxas() {
        return 0.05;
    }
}
