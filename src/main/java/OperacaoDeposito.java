public class OperacaoDeposito extends Operacao {

    public OperacaoDeposito(double valor) {
        super('d', valor);
    }

    @Override
    public String toString() {
        String str = this.getData() + "\t" + this.getTipo() + "\t" + this.getValor();

        return str;
    }

    @Override
    public double calculaTaxas() {
        return 0;
    }

}
