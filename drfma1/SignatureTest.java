/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drfma1;

/**
 *
 * @author owner
 */
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;
import java.io.FileInputStream;
import java.util.*;
import javax.rmi.CORBA.Util;
import sun.misc.BASE64Encoder;
public class SignatureTest {
  private static byte[] sign(String datafile, PrivateKey prvKey,String sigAlg) throws Exception {
    Signature sig = Signature.getInstance(sigAlg);
    sig.initSign(prvKey);
    FileInputStream fis = new FileInputStream(datafile);
    byte[] dataBytes = new byte[1024];
    int nread = fis.read(dataBytes);
    while (nread > 0) {
      sig.update(dataBytes, 0, nread);
      nread = fis.read(dataBytes);
    };
    return sig.sign();
  }
  private static boolean verify(String datafile, PublicKey pubKey,String sigAlg, byte[] sigbytes) throws Exception {
    Signature sig = Signature.getInstance(sigAlg);
    sig.initVerify(pubKey);
    FileInputStream fis = new FileInputStream(datafile);
    byte[] dataBytes = new byte[1024];
    int nread = fis.read(dataBytes);
    while (nread > 0) {
      sig.update(dataBytes, 0, nread);
      nread = fis.read(dataBytes);
    };
    return sig.verify(sigbytes);
  }
  public static void main(String[] unused) throws Exception {
  KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(512);
    KeyPair keyPair = kpg.genKeyPair();

    byte[] data = "test".getBytes("UTF8");
String s1="bt/fo5cG/DHVUH3FI6ZVAuVGjSc8pOoTJeYBTSOU1H15g0DXcLx07NXwwc0Qg90XstxNrf80WjeP";
    Signature sig = Signature.getInstance("MD5WithRSA");
    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
    System.out.println(s1.length());
    System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));

    sig.initVerify(keyPair.getPublic());
    sig.update(data);

   // System.out.println(sig.verify(signatureBytes));  
  }
}