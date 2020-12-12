package edu.hcmut;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSACommand {
    public static void main(String[] args) {
//        BigInteger p = generateRandomPrimeNumber();
//        BigInteger q = generateRandomPrimeNumber();
        BigInteger q = BigInteger.valueOf(61);
        BigInteger p = BigInteger.valueOf(53);
        System.out.println("p: " + p);
        System.out.println("q: " + q);

        BigInteger n = p.multiply(q);

        var lamdaN = calculateLeastCommonMultiple(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
        var e = findCoprime(lamdaN);
//        var d = lamdaN.modInverse(e);
        var d = findModularInverse(e, lamdaN) ;

        System.out.println("e: " + e);
        System.out.println("d: " + d);

        BigInteger sampleMessage = BigInteger.valueOf(65);

//        var encryptedMessage = sampleMessage.modPow(e, n);
//        var decryptedMessage = sampleMessage.modPow(d, n);

        var encryptedMessage = calculateModularExponentiation(sampleMessage, e, n);
        var decryptedMessage = calculateModularExponentiation(encryptedMessage, d, n);

        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static BigInteger calculateGreatestCommonDivisor(BigInteger a, BigInteger b) {
        while (b.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = a.mod(b);
            a = b;
            b = temp;
        }

        return a;
    }

    public static BigInteger calculateLeastCommonMultiple(BigInteger a, BigInteger b) {
        if (BigInteger.ZERO.equals(a) && BigInteger.ZERO.equals(b)) {
            return BigInteger.ZERO;
        }

        BigInteger gcd = calculateGreatestCommonDivisor(a, b);
        return a.divide(gcd).multiply(b);
    }

    public static BigInteger generateRandomPrimeNumber() {
        List<BigInteger> primeList = new ArrayList<>();

        Random rand = new Random();
        var resultRand = rand.nextInt(1000);

        BigInteger b = BigInteger.TWO;

        // TODO: change it
        BigInteger upperBound = BigInteger.valueOf(Long.MAX_VALUE);

        while (b.compareTo(upperBound) < 0) {
            boolean isPrime = true;
            for (BigInteger i : primeList) {
                if (b.mod(i) == BigInteger.ZERO) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                if (resultRand == rand.nextInt(1000)) {
                    return b;
                }

                primeList.add(b);
            }

            b = b.add(BigInteger.ONE);
        }

        return primeList.get(primeList.size() - 1);
    }

    public static BigInteger findCoprime(BigInteger a) {
        List<BigInteger> primeList = new ArrayList<>();

        BigInteger b = BigInteger.TWO;
        var upperBound = a.sqrt();

        while (b.compareTo(upperBound) < 0) {
            boolean isPrime = true;
            for (BigInteger i : primeList) {
                if (b.mod(i) == BigInteger.TWO) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
//            if (calculate_greatest_common_divisor(a, b) == 1) {
//                return b;
//            }
                // TODO: Test. Remove it
                if (b.equals(BigInteger.valueOf(17))) {
                    return b;
                }

                primeList.add(b);
            }

            b = b.add(BigInteger.ONE);
        }

        return BigInteger.ONE;
    }

    public static BigInteger findModularInverse(BigInteger base, BigInteger modular) {
        base = base.mod(modular);

        var x = BigInteger.ONE;
        while (x.compareTo(modular) < 0) {
            if (base.multiply(x).mod(modular).compareTo(BigInteger.ONE) == 0) {
                return x;
            }

            x = x.add(BigInteger.ONE);
        }

        return BigInteger.ONE;
    }

    public static long calculateModularExponentiationBase(long base, long exponent, long modulus) {
        long result = 1;
        for (long index = 1; index <= exponent; index++) {
            result = (result * base) % modulus;
        }

        return result;
    }

    public static BigInteger calculateModularExponentiation(BigInteger base, BigInteger exponent, BigInteger modulus) {
        if (BigInteger.ONE.equals(modulus)) {
            return BigInteger.ONE;
        }

        var result = BigInteger.ONE;
        base = base.mod(modulus);
        while (exponent.compareTo(BigInteger.ONE) > 0) {
            if (exponent.mod(BigInteger.TWO).compareTo(BigInteger.ONE) > 0) {
                result = result.multiply(base).mod(modulus);
            }

            exponent = exponent.shiftRight(1);
            base = base.multiply(base).mod(modulus);
        }

        return result;
    }

}
