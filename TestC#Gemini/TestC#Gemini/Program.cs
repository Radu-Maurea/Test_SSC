using System;
using System.Security.Cryptography;
using System.Text;

namespace LabHibridCrypto
{
    class Program
    {
        static void Main(string[] args)
        {
            // --- SERVER: Pregatire (Generare RSA 3072) ---
            using (RSACryptoServiceProvider rsaServer = new RSACryptoServiceProvider(3072))
            {
                // Exportam DOAR cheia publica pentru Client
                RSAParameters rsaPublicKey = rsaServer.ExportParameters(false);

                // --- CLIENT: Generare date si Criptare ---
                using (RSACryptoServiceProvider rsaClient = new RSACryptoServiceProvider())
                using (DSACryptoServiceProvider dsaClient = new DSACryptoServiceProvider(1024))
                using (AesManaged aes = new AesManaged())
                using (SHA1Managed sha1 = new SHA1Managed())
                {
                    // Importam cheia publica a serverului
                    rsaClient.ImportParameters(rsaPublicKey);

                    // Pregatim cheia simetrica AES-256
                    aes.KeySize = 256;
                    aes.GenerateKey();
                    byte[] rawAesKey = aes.Key;

                    // 1. Integritate: Semnam cheia AES folosind DSA (SHA-1)
                    byte[] hashToSign = sha1.ComputeHash(rawAesKey);
                    byte[] signature = dsaClient.SignHash(hashToSign, "SHA1");

                    // Exportam cheia publica DSA pentru ca Serverul sa poata verifica semnatura[cite: 1]
                    DSAParameters dsaPublicKey = dsaClient.ExportParameters(false);

                    // 2. Confidențialitate: Criptam cheia AES cu RSA (OAEP Padding)[cite: 1]
                    byte[] encryptedAesKey = rsaClient.Encrypt(rawAesKey, true);

                    Console.WriteLine("Client: Cheia AES a fost semnata si criptata.");

                    // --- SERVER: Receptie, Decriptare si Verificare ---

                    // A. Decriptare RSA folosind cheia privata proprie[cite: 1]
                    byte[] decryptedAesKey = rsaServer.Decrypt(encryptedAesKey, true);

                    // B. Verificare Semnatura DSA[cite: 1]
                    using (DSACryptoServiceProvider dsaVerifier = new DSACryptoServiceProvider())
                    {
                        // Importam cheia publica a Clientului[cite: 1]
                        dsaVerifier.ImportParameters(dsaPublicKey);

                        // Recalculam hash-ul cheii decriptate[cite: 1]
                        byte[] hashToVerify = sha1.ComputeHash(decryptedAesKey);

                        // Verificam daca semnatura corespunde cheii decriptate[cite: 1]
                        bool isAuthentic = dsaVerifier.VerifyHash(hashToVerify, "SHA1", signature);

                        // Afisare Rezultate
                        Console.WriteLine("\n--- Rezultate Server ---");
                        Console.WriteLine($"Cheie AES decriptata (HEX): {BitConverter.ToString(decryptedAesKey).Replace("-", "")}[cite: 1]");
                        Console.WriteLine($"Verificare Integritate DSA: {(isAuthentic ? "SUCCES" : "ESEC")}[cite: 1]");

                        if (isAuthentic)
                        {
                            Console.WriteLine("Server: Cheia a fost primita corect si este autentica.");
                        }
                    }
                }

                // Curatare explicita a resurselor serverului conform recomandarii din lab[cite: 1]
                rsaServer.PersistKeyInCsp = false;
                rsaServer.Clear();
            }

            Console.WriteLine("\nApasa orice tasta pentru a inchide...");
            Console.ReadKey();
        }
    }
}