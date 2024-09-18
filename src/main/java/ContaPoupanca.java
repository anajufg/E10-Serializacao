public class ContaPoupanca extends Conta {
    public ContaPoupanca(int numero, Cliente dono, double saldo, double limite, String agencia) {
        super(numero, dono, saldo, limite, agencia);
    }

    @Override
    public boolean setLimite(double limite){
        if (limite < 100 || limite > 1000) {
            throw new IllegalArgumentException("Limite inválido");
        } else {
            this.limite = limite;
            return true;
        }
    }

    @Override
    public double calculaTaxas() {
        return 0;
    }
}
