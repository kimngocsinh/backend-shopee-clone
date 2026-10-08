package com.shopee.backend.util;

import java.security.SecureRandom;

public final class CodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {}

    public static String otp() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

}
