package com.github.xt449.primerunningcipher;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public abstract class FileExample {

	public static void main(String[] args) {
		final PrimeRunningCipher primeRunningCipher = new PrimeRunningCipher(449, (byte) 2, 255);

		try {
			final JFileChooser fileChooser = new JFileChooser();
			if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				final File file = fileChooser.getSelectedFile();

				// Encrypt
				final File encryptedFile = new File(file.getParent(), "encrypted_" + file.getName());
				Files.write(encryptedFile.toPath(), PrimeRunningCipher.charsToBytePairs(primeRunningCipher.encrypt(PrimeRunningCipher.bytePairsToChars(Files.readAllBytes(file.toPath())))));

				// Decrypt
				final File decryptedFile = new File(file.getParent(), "decrypted_" + file.getName());
				Files.write(decryptedFile.toPath(), PrimeRunningCipher.charsToBytePairs(primeRunningCipher.decrypt(PrimeRunningCipher.bytePairsToChars(Files.readAllBytes(encryptedFile.toPath())))));
			}
		} catch(IOException exc) {
			exc.printStackTrace();
		}
	}
}
