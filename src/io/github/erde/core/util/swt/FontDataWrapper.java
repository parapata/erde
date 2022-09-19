package io.github.erde.core.util.swt;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;

public class FontDataWrapper {

    private FontData[] fontData;

    public FontDataWrapper(String fontData) {
        this.fontData = toFontData(fontData);
    }

    public FontDataWrapper(FontData[] fontData) {
        this.fontData = fontData;
    }

    public FontData[] getFontData() {
        return fontData;
    }

    public String getFontDataString() {
        return toFontDataString(fontData);
    }

    @Override
    public String toString() {
        if (fontData == null || fontData.length == 0) {
            return "";
        }
        return String.format("%s %s", fontData[0].getName(), fontData[0].getHeight());
    }

    public static FontData[] toFontData(String font) {
        if (font == null) {
            font = "";
        }
        return PreferenceConverter.basicGetFontData(font);
    }

    public static String toFontDataString(FontData[] fontData) {
        return PreferenceConverter.getStoredRepresentation(fontData);
    }
}
