package com.compassouol.backendrecruitment.utils;

import java.text.Normalizer;

public class StringUtil {
    public String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD).toLowerCase().replaceAll(" ", "_")
                .replaceAll("[\u0300-\u036f]", "");
    }
}
