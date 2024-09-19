import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

abstract class Conta implements ITaxas, Serializable{
    final private static long serialVertionUID = 1L;
    private int numero;
    private Cliente dono;
    private int agencia;
    private double saldo;
    protected double limite;
    private ArrayList<Operacao> operacoes;
    private transient static int totalContas = 0;

    public Conta(int numero, Cliente dono, double saldo, double limite, int agencia) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
        this.limite = limite;
        this.agencia = agencia;

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

    public int getAgencia() {
        return agencia;
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

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    // Set limite
    public abstract boolean setLimite(double limite) throws IllegalArgumentException;

    public void salvarEmArquivo() {
        String nomeArquivo = this.agencia + "-" + this.numero + ".ser";

        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream(nomeArquivo);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(this);

            System.out.println("Conta serializada com sucesso");

        } catch (IOException e) {
            System.err.println("Erro ao serializar a conta");
            System.err.println(e.getMessage());

        } finally {
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.err.println("Erro ao serializar a conta");
                    System.err.println(e.getMessage());
                }
            }
            if (fileOut!=null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    System.err.println("Erro ao serializar a conta");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public static Conta carregaDados(int agencia, int numero) {
        String nomeArquivo = agencia + "-" + numero + ".ser";

        FileInputStream fileIn = null;
        ObjectInputStream in = null;

        try {
            fileIn = new FileInputStream(nomeArquivo);
            in = new ObjectInputStream(fileIn);
            Conta conta = (Conta)in.readObject();

            System.out.println("Conta desserializada com sucesso");

            return conta;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao desserializar a conta");
            System.err.println(e.getMessage());

        } finally {
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.err.println("Erro ao desserializar a conta");
                    System.err.println(e.getMessage());
                }
            }
            if (fileIn!=null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    System.err.println("Erro ao desserializar a conta");
                    System.err.println(e.getMessage());
                }
            }
        }
        return null;
    }
}
