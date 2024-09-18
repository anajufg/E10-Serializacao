import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

public class Main {
    public static Conta carregaDados(int agencia, int numero) throws IOException, ClassNotFoundException{
        String nomeArquivo = agencia + "-" + numero + ".ser";

        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(nomeArquivo);
        } catch (FileNotFoundException e) {
            System.out.println("A conta " + numero + " da agencia " + agencia + " não foi encontrada.");
        }

        ObjectInputStream in = new ObjectInputStream(fileIn);

        Conta conta = (Conta)in.readObject();

        in.close();
        if (fileIn!=null) fileIn.close();

        return conta;
    }

    public static void main(String[] args) {

        Cliente joao = new PessoaFisica("João", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-11", 36, 'm');

        Cliente ana = new PessoaFisica("Ana", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-12", 17, 'f');

        Cliente lojinha = new PessoaJuridica("Loja R$1,99", "Av. Afonso Pena, 3000",
                new Date(), "000.00000.0000/0001", 25, "Comércio");

        Conta conta1 = new ContaCorrente(1234, joao, 400, 1500, 1);
        Conta conta2 = new ContaCorrente(1234, lojinha, 10000, 150, 2);

        try {
            conta1.sacar(200);
        } catch (ValorNegativoException | SemLimiteException e) {
            System.out.println(e.getMessage());
        }

        try {
            conta1.depositar(400);
        } catch (ValorNegativoException e) {
            System.out.println(e.getMessage());
        }

        try {
            conta1.salvarEmArquivo();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Conta contaCarregada = null;

        try {
            contaCarregada = carregaDados(conta1.getAgencia(), conta1.getNumero());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
