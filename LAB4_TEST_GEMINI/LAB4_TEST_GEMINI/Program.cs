using LAB4_TEST_GEMINI;
using System.Security.Cryptography;

class Program
{
    static void Main(string[] args)
    {

        TransferIntreClase transfer = new TransferIntreClase();
        transfer.Transfera();

        SemnareVerificare semnare = new SemnareVerificare("ana");
        byte[] semnatura = semnare.Semneaza();
        bool verificare = semnare.Verificare(semnatura);
        if (verificare)
        {
            Console.WriteLine("Semnatura este valida.");
        }

        SemnareVerificareHash semnarehash = new SemnareVerificareHash("ana");
        byte[] semnaturaHash = semnarehash.Sign();
        bool verificareHash = semnarehash.Verify(semnaturaHash);
        if(verificareHash)
        {
            Console.WriteLine("Semnatura hash este valida.");
        }
    }
}
