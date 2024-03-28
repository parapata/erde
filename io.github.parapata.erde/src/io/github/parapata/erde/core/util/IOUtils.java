package io.github.parapata.erde.core.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IOUtils.
 *
 * @author modified by parapata
 */
public class IOUtils {

    public static void copyStream(InputStream in, OutputStream out) {
        try (in; out) {
            byte[] buf = new byte[in.available()];
            in.read(buf);
            out.write(buf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadStream(InputStream in, String charset) {
        try (in) {
            byte[] buf = new byte[in.available()];
            in.read(buf);
            return new String(buf, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
