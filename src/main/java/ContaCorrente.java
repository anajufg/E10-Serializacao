public class ContaCorrente extends Conta {

    public ContaCorrente(int numero, Cliente dono, double saldo, double limite, int agencia) {
        super(numero, dono, saldo, limite, agencia);
    }

    @Override
    public boolean setLimite(double limite) {
        if (limite < -100) {
            throw new IllegalArgumentException("Limite inválido");
        } else {
            this.limite = limite;
            return true;
        }
    }

    @Override
    public double calculaTaxas() {
        if(getDono() instanceof PessoaFisica) {
            return 10;
        } else {
            return 20;
        }
    }
}
