package fr.farmeurimmo.backenddemolocation.utils;

import java.util.UUID;

public class UuidUtils {

    public static final String REGEX = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
    public static final String REGEX_REPLACEMENT = "$1-$2-$3-$4-$5";

    public static UUID convertHexToUUID(String hexString) {
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        return UUID.fromString(hexString.replaceFirst(REGEX, REGEX_REPLACEMENT));
    }
}
