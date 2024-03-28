package io.github.parapata.erde.core.util.swt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * ColorRegistry.
 *
 * @author modified by parapata
 */
public class ColorRegistry {

    private Map<RGB, Color> colors = new HashMap<>();

    public Color getColor(RGB rgb) {
        Color color = colors.get(rgb);
        if (color == null) {
            color = new Color(Display.getDefault(), rgb);
            colors.put(rgb, color);
        }
        return color;
    }

    public void dispose() {
        for (Color color : colors.values()) {
            color.dispose();
        }
        colors.clear();
    }

}
