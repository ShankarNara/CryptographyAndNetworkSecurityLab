import java.util.*;
import java.lang.*;
import java.io.*;
import java.security.*;
import java.math.*;

class DSS{
	public String SIGNING_ALGORITHM = "SHA256withRSA"; 

	public KeyPair generateRSAKeyPair() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		return kpg.generateKeyPair();
	}

	public byte[] createDigitalSignature(
			byte[] input,
			PrivateKey Key
		) throws Exception{
		Signature Sign = Signature.getInstance(SIGNING_ALGORITHM);
		Sign.initSign(Key);
		Sign.update(input);
		return Sign.sign(); 
	}

	public boolean verifyDigitalSignature(
			byte[] input,
			byte[] sign,
			PublicKey Key
		) throws Exception{

		Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
		signature.initVerify(Key);
		signature.update(input);

		return signature.verify(sign);

	}
}

public class DSSDriver{

	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(System.in);

		String input;
		System.out.println("Enter the text to sign : ");
		input = in.nextLine();

		DSS dss = new DSS();
		KeyPair keypair = dss.generateRSAKeyPair();

		byte[] signature = dss.createDigitalSignature(input.getBytes(), keypair.getPrivate());

		BigInteger s = new BigInteger(signature);
		System.out.println("\n\nSignature : "+s.toString(16));

		if(dss.verifyDigitalSignature(input.getBytes(), signature, keypair.getPublic())){
			System.out.println("Verification : successful");	
		} else {
			System.out.println("Verification : failure");
		}
		

	}
}










