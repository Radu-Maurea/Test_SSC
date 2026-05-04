using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

namespace LAB4_TEST_GEMINI
{
    public class TransferIntreClase
    {
        public void Transfera()
        {
            using (RSACryptoServiceProvider appA = new RSACryptoServiceProvider(3072))
            {
                using (RSACryptoServiceProvider appB = new RSACryptoServiceProvider(3072))
                {
                    RSAParameters publicKey = appA.ExportParameters(false);

                    appB.ImportParameters(publicKey);

                    string mesaj = "Un mesaj de criptat:()";
                    byte[] mesajBytes = System.Text.Encoding.UTF8.GetBytes(mesaj);

                    byte[] criptomesaj = appB.Encrypt(mesajBytes, true);
                    byte[] dec_mes = appA.Decrypt(criptomesaj, true);
                    string mesaj_decriptat = System.Text.Encoding.UTF8.GetString(dec_mes);
                    Console.WriteLine("Mesajul original: " + mesaj);
                    Console.WriteLine("Mesajul decriptat: " + mesaj_decriptat);
                }

            }
        }
    }
}
