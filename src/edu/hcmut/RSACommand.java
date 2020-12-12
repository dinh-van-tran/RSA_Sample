package edu.hcmut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSACommand {
    public static void main(String[] args) {
        long p = generateRandomPrimeNumber();
        long q = generateRandomPrimeNumber();
        System.out.println("p: " + p);
        System.out.println("q: " + q);

        long n = p * q;

        var lamdaN = calculateLeastCommonMultiple(p - 1, q - 1);
        var e = findCoprime(lamdaN);
        var d = findModularInverse(e, lamdaN);

        long sampleMessage = 65;
        var encryptedMessage = calculateModularExponentiation(sampleMessage, e, n);
        var decryptedMessage = calculateModularExponentiation(encryptedMessage, d, n);

        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static long calculateGreatestCommonDivisor(long a, long b) {
        while (b > 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    public static long calculateLeastCommonMultiple(long a, long b) {
        if (a == 0 && b == 0) {
            return 0;
        }

        long gcd = calculateGreatestCommonDivisor(a, b);
        return (a / gcd) * b;
    }

    public static long generateRandomPrimeNumber() {
        List<Long> primeList = new ArrayList<>();

        Random rand = new Random();
        var resultRand = rand.nextInt(1000);

        long b = 2;
        double upperBound = Math.sqrt(Long.MAX_VALUE);
        while (b < upperBound) {
            boolean isPrime = true;
            for (long i : primeList) {
                if (b % i == 0) {
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

            b++;
        }

        return primeList.get(primeList.size() - 1);
    }

    public static long findCoprime(long a) {
        List<Long> primeList = new ArrayList<>();

        long b = 2;
        double upperBound = Math.sqrt(a);
        while (b < upperBound) {
            boolean isPrime = true;
            for (long i : primeList) {
                if (b % i == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
//            if (calculate_greatest_common_divisor(a, b) == 1) {
//                return b;
//            }
                // TODO: Test. Remove it
                if (b == 17) {
                    return b;
                }

                primeList.add(b);
            }
            b++;
        }

        return 1;
    }

    public static long findModularInverse(long base, long modules) {
        base = base % modules;
        for (long x = 1; x < modules; x++) {
            if ((base * x) % modules == 1) {
                return x;
            }
        }

        return 1;
    }

    public static long calculateModularExponentiationBase(long base, long exponent, long modulus) {
        long result = 1;
        for (long index = 1; index <= exponent; index++) {
            result = (result * base) % modulus;
        }

        return result;
    }

    public static long calculateModularExponentiation(long base, long exponent, long modulus) {
        if (modulus == 1) {
            return 0;
        }

        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }

            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }

        return result;
    }

}
