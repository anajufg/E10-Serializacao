public class ContaUniversitaria extends Conta {
    public ContaUniversitaria(int numero, Cliente dono, double saldo, double limite, String agencia) {
        super(numero, dono, saldo, limite, agencia);
    }

    @Override
    public boolean setLimite(double limite){
        if (limite < 0 || limite > 500) {
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
