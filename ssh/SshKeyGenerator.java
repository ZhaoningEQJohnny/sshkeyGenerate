package aws.ssh;

import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SshKeyGenerator {
    public static SshKey getKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            Key pub = keyPair.getPublic();
            Key pvt = keyPair.getPrivate();


            String outFile = "mykeypair";

            Base64.Encoder encoder = Base64.getEncoder();
            InputStream inputStream;

            Writer out = new FileWriter(outFile);
            out.write("-----BEGIN RSA PRIVATE KEY-----\n");
            out.write(encoder.encodeToString(pvt.getEncoded()));
            out.write("\n-----END RSA PRIVATE KEY-----\n");
            out.close();
            out = new FileWriter(outFile + ".pub");
            out.write("-----BEGIN RSA PUBLIC KEY-----\n");
            out.write("ssh-rsa "+encoder.encodeToString(pub.getEncoded()));
            out.write("\n-----END RSA PUBLIC KEY-----\n");
            out.close();

            SshKey  sshKey = new SshKey();
            sshKey.setPrivateKey(encoder.encodeToString(pvt.getEncoded()));
            sshKey.setPublicKey(encoder.encodeToString(pub.getEncoded()));

            return  sshKey;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        getKeyPair();

    }
}
