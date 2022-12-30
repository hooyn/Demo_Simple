package hooyn.base.global.util;

import java.util.UUID;

public class UUIDUtil {

    public static String UUIDToString(UUID uuid){
        return String.valueOf(uuid);
    }

    public static UUID StringToUUID(String uuid){
        return UUID.fromString(uuid);
    }
}
