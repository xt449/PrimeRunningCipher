package com.github.xt449.primerunningcipher;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class StringExample {

	public static void main(String[] args) {
		final PrimeRunningCipher primeRunningCipher = new PrimeRunningCipher(449, (byte) 2, 255);

		final String text = new Scanner(System.in).nextLine();

		char[] data = text.toCharArray();
		System.out.println("Input: " + Arrays.toString(data));

		// Encrypt
		data = primeRunningCipher.encrypt(data);
		System.out.println("Encrypted: " + Arrays.toString(data));

		// Decrypt
		data = primeRunningCipher.decrypt(data);
		System.out.println("Decrypted: " + Arrays.toString(data));
	}
}
