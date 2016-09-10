package ua.vodnik.mushroomsbook;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Admin on 09.09.2016.
 */
public class SearchableProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "ua.vodnik.mushroomsbook.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
