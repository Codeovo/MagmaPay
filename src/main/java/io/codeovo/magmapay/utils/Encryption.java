package io.codeovo.magmapay.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Encryption {
    public static String securePass(String pin) {
        return BCrypt.hashpw(pin, BCrypt.gensalt());
    }

    public static boolean isSame(String originalHash, String checkPin) {
        return BCrypt.checkpw(checkPin, originalHash);
    }
}
