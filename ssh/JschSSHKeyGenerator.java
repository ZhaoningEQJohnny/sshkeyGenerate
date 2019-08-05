package aws.ssh;
import com.jcraft.jsch.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public class JschSSHKeyGenerator {
    public static void main(String[] arg){

        String filename="keypair";
        String comment="";

        JSch jsch=new JSch();

        String passphrase="11121";

        try{
            KeyPair kpair=KeyPair.genKeyPair(jsch, KeyPair.RSA);
            kpair.setPassphrase(passphrase);
            kpair.writePrivateKey(filename);
            kpair.writePublicKey(filename+".pub", comment);
            System.out.println("Finger print: "+kpair.getFingerPrint());
            kpair.dispose();
            File pvtKey = new File("keypair");
            pvtKey.setWritable(false);

            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);

            Files.setPosixFilePermissions(pvtKey.toPath(), perms);
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.exit(0);
    }
}
