package com.github.xt449.primerunningcipher;

import java.util.Arrays;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public final class PrimeRunningCipher {

	private final int divisor;
	private final byte offset;
	private final int shift;

	public PrimeRunningCipher(int divisor, byte offset, int shift) {
		this.divisor = divisor;
		this.offset = offset;
		this.shift = shift;
	}

	public char[] encrypt(final char[] chars) {
		int nextOffset = offset;
		final char[] charsEncrypted = new char[chars.length];
		for(int i = 0; i < chars.length; i++) {
			int replacement = chars[i] + nextOffset + shift;
			if(replacement > Character.MAX_VALUE) {
				replacement -= Character.MAX_VALUE;
			}
			charsEncrypted[i] = (char) replacement;
			nextOffset = replacement % (divisor - offset);
		}

		return charsEncrypted;
	}

	public char[] decrypt(final char[] chars) {
		int nextOffset = offset;
		final char[] charsDecrypted = new char[chars.length];
		for(int i = 0; i < chars.length; i++) {
			int replacement = chars[i] - nextOffset - shift;
			if(replacement < Character.MIN_VALUE) {
				replacement += Character.MAX_VALUE;
			}
			charsDecrypted[i] = (char) replacement;
			nextOffset = chars[i] % (divisor - offset);
		}

		return charsDecrypted;
	}

	// Does not work with signed data types
	@Deprecated
	public byte[] xtEncryptBytes(final byte[] bytes) {
		int nextOffset = offset;
		final byte[] bytesEncrypted = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			int replacement = bytes[i] + nextOffset + shift;
			if(replacement > Byte.MAX_VALUE) {
				replacement -= Byte.MAX_VALUE;
			}
			bytesEncrypted[i] = (byte) replacement;
			nextOffset = replacement % (divisor - offset);
		}

		return bytesEncrypted;
	}

	// Does not work with signed data types
	@Deprecated
	public byte[] xtDecryptBytes(final byte[] bytes) {
		int nextOffset = offset;
		final byte[] bytesDecrypted = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			int replacement = bytes[i] - nextOffset - shift;
			if(replacement < Byte.MIN_VALUE) {
				System.out.print("(" + replacement + ")");
				replacement += Byte.MAX_VALUE;
			}
			bytesDecrypted[i] = (byte) replacement;
			nextOffset = bytes[i] % (divisor - offset);
		}

		return bytesDecrypted;
	}

	// Static

	public static char[] bytePairsToChars(final byte[] data) {
		final byte[] bytes;
		if(data.length % 2 == 1) {
			bytes = Arrays.copyOf(data, data.length + 1);
		} else {
			bytes = data;
		}
		final char[] chars = new char[bytes.length / 2];
		for(int i = 0; i < bytes.length; i += 2) {
			int part = (bytes[i] << Byte.SIZE) | (bytes[i + 1] & 0x00FF);
			chars[i / 2] = (char) part;
		}
		return chars;
	}

	public static byte[] charsToBytePairs(final char[] chars) {
		final boolean extraEmptyByte = (chars[chars.length - 1] & 0x00FF) == 0;
		final byte[] bytes = new byte[chars.length * 2];
		for(int i = 0; i < chars.length; i++) {
			int part1 = chars[i] >> Byte.SIZE;
			bytes[2 * i] = (byte) part1;
			int part2 = chars[i] & 0x00FF;
			bytes[2 * i + 1] = (byte) part2;
		}
		if(extraEmptyByte) {
			return Arrays.copyOf(bytes, bytes.length - 1);
		} else {
			return bytes;
		}
	}
}
