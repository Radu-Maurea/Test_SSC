using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

namespace LAB4_TEST_GEMINI
{
    public class SemnareVerificareHash
    {
        DSACryptoServiceProvider dsa = new DSACryptoServiceProvider(1024);
        SHA1Managed sha = new SHA1Managed();
        public string mesaj { get; set; }

        public SemnareVerificareHash(string mesaj)
        {
            this.mesaj = mesaj;
        }

        public byte[] Sign()
        {
            byte[] hash = sha.ComputeHash(System.Text.Encoding.UTF8.GetBytes(mesaj));
            byte[] semnatura = dsa.SignHash(hash, "SHA1");
            return semnatura;
        }
        public bool Verify(byte[] semnatura)
        {
            byte[] hash = sha.ComputeHash(System.Text.Encoding.UTF8.GetBytes(mesaj));
            return dsa.VerifyHash(hash, "SHA1", semnatura);
        }
    }
}
