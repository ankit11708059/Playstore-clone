package com.example.cloneplaystore.dependencies.modules;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.ferfalk.simplesearchview.SimpleSearchView;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

public class RxSearch {

    public static Observable<String> fromFloatingSearchView(@NonNull final FloatingSearchView searchView) {
        final BehaviorSubject<String> subject = BehaviorSubject.create();

        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!newQuery.isEmpty()) {
                subject.onNext(newQuery);
            }
        });
      return subject;
    }
        public static Observable<String> fromSearchView(@NonNull final SimpleSearchView searchView) {
            final BehaviorSubject<String> subject = BehaviorSubject.create();

            searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(!newText.isEmpty())
                    {
                        subject.onNext(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextCleared() {
                    return false;
                }
            });
        return subject;
    }
}