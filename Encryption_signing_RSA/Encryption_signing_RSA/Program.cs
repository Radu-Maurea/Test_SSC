using LAB2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;


namespace Encryption_signing_RSA
{
    internal class Program
    {
        static ConversionHandler conv = new ConversionHandler();
        static RSACryptoServiceProvider myRSA = new RSACryptoServiceProvider(2048);
        static AesManaged myAES = new AesManaged();

        public static void Encrypt()
        {
            byte[] RSAciphertext;
            byte[] plaintext;

            myAES.GenerateKey(); //generate an AES key
            RSAciphertext = myRSA.Encrypt(myAES.Key, true); //encrypt an AES key with RSA
            plaintext = myRSA.Decrypt(RSAciphertext, true); //decrpyt and recover the AES key


            string originalKeyHex = conv.ByteArrayToHexString(myAES.Key);
            Console.WriteLine($"Original AES Key:   {originalKeyHex}");

            string encryptedKeyHex = conv.ByteArrayToHexString(RSAciphertext);
            Console.WriteLine($"Encrypted AES Key:  {encryptedKeyHex}");

            string decryptedKeyHex = conv.ByteArrayToHexString(plaintext);
            Console.WriteLine($"Decrypted AES Key:  {decryptedKeyHex}");
            Console.WriteLine("-----------------------------------------------------");

            //Export PUBLIC key only (false = public only)
            string publicKey = myRSA.ToXmlString(false);
            Console.WriteLine("=== PUBLIC KEY ===");
            Console.WriteLine(publicKey);

            // Export PRIVATE key (true = include private key)
            string privateKey = myRSA.ToXmlString(true);
            Console.WriteLine("\n=== PRIVATE KEY ===");
            Console.WriteLine(privateKey);
            Console.WriteLine("\n");
        }

        public static void Sign()
        {
            SHA256Managed myHash = new SHA256Managed();
            string some_text = "un mesaj important";
            byte[] signature;
            signature = myRSA.SignData(conv.StringToByteArray(some_text), myHash);

            string another_text = some_text + ":'(";

            bool verification = myRSA.VerifyData(conv.StringToByteArray(some_text), myHash, signature);
            bool verification2 = myRSA.VerifyData(conv.StringToByteArray(another_text), myHash, signature);
            if (verification)
                Console.WriteLine("Signature is valid.");
            else
                Console.WriteLine("Signature is invalid.");
            if (verification2)
                Console.WriteLine("Signature is valid.");
            else
                Console.WriteLine("signature is invalid.");
        }
        static void Main(string[] args)
        {
            Encrypt();
            Sign();
            while (true)
            {
                Console.WriteLine("\npress any key...");
                string a = Console.ReadLine();
                if (a != null)
                    return; 

            }

        }
    }
}
