package com.github.tharindusathis.jomodoro.util;

import javafx.scene.text.Font;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public final class Resources {
    private static final Map<CustomFonts, Font> FONTS = new EnumMap<>(CustomFonts.class);

    private Resources() {
    }

    public static Optional<Font> getFont(CustomFonts name) {
        return Optional.ofNullable(FONTS.get(name));
    }

    public static void addFont(CustomFonts name, Font font) {
        FONTS.put(name, font);
    }

}
