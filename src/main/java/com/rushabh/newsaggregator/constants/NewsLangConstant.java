package com.rushabh.newsaggregator.constants;

import java.util.Map;
import java.util.Set;

public class NewsLangConstant {

    public static final Map<String, String> NEWS_LANG = Map.of(
            "English", "en",
            "German", "de",
            "Hindi", "hi"

    );


    public static final Set<String> LANG_KEYS = NEWS_LANG.keySet();
}
