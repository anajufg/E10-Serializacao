import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Cliente joao = new PessoaFisica("João", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-11", 36, 'm');

        Cliente ana = new PessoaFisica("Ana", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-12", 17, 'f');

        Cliente lojinha = new PessoaJuridica("Loja R$1,99", "Av. Afonso Pena, 3000",
                new Date(), "000.00000.0000/0001", 25, "Comércio");

        Conta conta1 = new ContaCorrente(1234, joao, 400, 1500, 1);
        Conta conta2 = new ContaCorrente(4321, lojinha, 10000, 150, 2);


        conta1.salvarEmArquivo();

        Conta contaCarregada = Conta.carregaDados(conta1.getAgencia(), conta1.getNumero());

    }
}
