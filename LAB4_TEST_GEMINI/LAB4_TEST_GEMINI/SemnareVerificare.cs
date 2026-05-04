using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

namespace LAB4_TEST_GEMINI
{
    public class SemnareVerificare
    {
        DSACryptoServiceProvider dsa = new DSACryptoServiceProvider(1024);
        public string mesaj { get; set; }
        public SemnareVerificare(string mesaj)
        {
            this.mesaj = mesaj;
        }
        public byte[] Semneaza()
        {
            byte[] mesajBytes = System.Text.Encoding.UTF8.GetBytes(mesaj);
            byte[] semnatura = dsa.SignData(mesajBytes);
            Console.WriteLine("Mesajul original: " + mesaj);
            Console.WriteLine("Semnatura: " + Convert.ToBase64String(semnatura));
            return semnatura;
        }

        public bool Verificare(byte[] semnatura)
        {
            return dsa.VerifyData(System.Text.Encoding.UTF8.GetBytes(mesaj),semnatura);

        }
    }
}
