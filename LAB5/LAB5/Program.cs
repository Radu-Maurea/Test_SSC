using System.Security.Cryptography;

class Program
{
    static void Semnatura_Hash()
    {
        SHA256Managed sHA256Managed = new SHA256Managed();
        string mesaj = "Un mesaj important!";
        byte[] mesajBytes = System.Text.Encoding.UTF8.GetBytes(mesaj);
        byte[] hash = sHA256Managed.ComputeHash(mesajBytes);

        DSACryptoServiceProvider dsa = new DSACryptoServiceProvider();
        byte[] semnatura = dsa.SignHash(hash, "SHA256");
        bool verifiare = dsa.VerifyHash(hash, "SHA256", semnatura);

        if (verifiare)
        {
            Console.WriteLine("Semnatura este valida.");
        }


    }
    static void Main(string[] args)
    {
        DSACryptoServiceProvider dsa = new DSACryptoServiceProvider();

        string mesaj = "Un mesaj important!";
        byte[] mesajBytes = System.Text.Encoding.UTF8.GetBytes(mesaj);

        byte[] semnatura = dsa.SignData(mesajBytes);
        bool valid = dsa.VerifyData(mesajBytes, semnatura);

        if(valid)
        {
            Console.WriteLine("Semnatura este valida.");
        }
        else
        {
            Console.WriteLine("Semnatura nu este valida.");
        }
        Semnatura_Hash();


    }
}