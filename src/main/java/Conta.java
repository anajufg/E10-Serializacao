import java.util.ArrayList;
import java.util.Collections;

abstract class Conta implements ITaxas{

    private int numero;

    private Cliente dono;

    private double saldo;

    protected double limite;

    private ArrayList<Operacao> operacoes;

    private static int totalContas = 0;

    public Conta(int numero, Cliente dono, double saldo, double limite) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
        this.limite = limite;

        this.operacoes = new ArrayList<>();
        Conta.totalContas++;
    }

    public void depositar(double valor) throws ValorNegativoException {
        if (valor < 0) {
            throw new ValorNegativoException("Deposito negativo (valor: " + valor + ")");
        }
        this.saldo += valor;
        this.operacoes.add(new OperacaoDeposito(valor));
    }

    public boolean sacar(double valor) throws ValorNegativoException, SemLimiteException {

        if (valor < 0) {
            throw new ValorNegativoException("Saque negativo (valor: " + valor + ")");
        }

        if (valor > this.limite) {
            throw new SemLimiteException("Valor de saque fora do limite (valor: " + valor +")");
        }

        if (valor > 0.0 && valor <= this.limite) {
            this.operacoes.add(new OperacaoSaque(valor));
            this.saldo -= valor;
            return true;
        } else {
            return false;
        }
    }

    public boolean transferir(Conta contaDestino, double valor) throws ValorNegativoException, SemLimiteException{
        if (this.sacar(valor)) {
            contaDestino.depositar(valor);
            return true;
        } else {
            System.out.print("<Não foi possível realizar a transferência>");
            return false;
        }
    }

    @Override
    public String toString() {
        String str = "=========== Conta " + this.numero + " ===========\n" +
                this.dono.toString() + "\n" +
                "Saldo: " + this.saldo + "\n" +
                "Limite: " + this.limite + "\n" +
                "==================================\n";
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Conta) {
            Conta objConta = (Conta) obj;

            if(this.numero == objConta.numero) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void imprimirExtrato(int ordenacao) {
        if (ordenacao == 1) {
            System.out.println("=========== Extrato Conta " + this.numero + " ===========");
            for (Operacao atual : this.operacoes) {
                if (atual != null) {
                    System.out.println(atual.toString());
                }
            }
            System.out.println("==========================================\n");
        } else if (ordenacao == 2) {
            Collections.sort(operacoes);

            System.out.println("=========== Extrato Conta " + this.numero + " ===========");
            for (Operacao atual : this.operacoes) {
                if (atual != null) {
                    System.out.println(atual.toString());
                }
            }
            System.out.println("==========================================\n");
        } else {
            System.out.println("<Ordenação Inválida>");
        }
    }

    public void imprimirExtratoTaxas() {
        double total = 0;

        System.out.printf("====== Extrato de Taxas Conta " + this.numero + " ======\n" +
                           "Manutenção da conta: %.2f \n\n" +
                           "OPERAÇÕES\n", calculaTaxas());
        total += calculaTaxas();
        for(Operacao atual : this.operacoes) {
            if (atual != null) {
                if(atual instanceof OperacaoSaque) {
                    System.out.printf("Saque: %.2f\n", atual.calculaTaxas());
                    total += atual.calculaTaxas();
                }
            }
        }
        System.out.printf("\nTotal: %.2f \n==========================================\n", total);
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    // Set limite
    public abstract boolean setLimite(double limite) throws IllegalArgumentException;
}
