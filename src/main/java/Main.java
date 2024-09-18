import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Cliente joao = new PessoaFisica("João", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-11", 36, 'm');

        Cliente ana = new PessoaFisica("Ana", "Av. Antonio Carlos, 6627",
                new Date(), "111.111.111-12", 17, 'f');

        Cliente lojinha = new PessoaJuridica("Loja R$1,99", "Av. Afonso Pena, 3000",
                new Date(), "000.00000.0000/0001", 25, "Comércio");

        Conta conta1 = new ContaCorrente(1234, joao, 0, 1500, "Banco do Brasil");
        Conta conta2 = new ContaCorrente(1234, lojinha, 10000, 150, "Santander");




    }
}
