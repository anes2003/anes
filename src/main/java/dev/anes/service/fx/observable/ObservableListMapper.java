package dev.anes.service.fx.observable;

import java.lang.ref.WeakReference;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.WeakListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/*
*
*	<< Author: Sol Silvrash >>
*
*/

public class ObservableListMapper {

    public static <E, F> void mapContent(ObservableList<F> mapped, ObservableList<? extends E> source,
            Function<? super E, ? extends F> mapper) {
        map(mapped, source, mapper);
    }

    private static <E, F> Object map(ObservableList<F> mapped, ObservableList<? extends E> source,
            Function<? super E, ? extends F> mapper) {
        final ListContentMapping<E, F> contentMapping = new ListContentMapping<E, F>(mapped, mapper);
        mapped.setAll(source.stream().map(mapper).collect(Collectors.toList()));
        source.removeListener(contentMapping);
        source.addListener(contentMapping);
        return contentMapping;
    }

    private static class ListContentMapping<E, F> implements ListChangeListener<E>, WeakListener {
        private final WeakReference<List<F>> mappedRef;
        private final Function<? super E, ? extends F> mapper;
        private IdentityHashMap<E, F> cache = null;

        public ListContentMapping(List<F> mapped, Function<? super E, ? extends F> mapper) {
            this.mappedRef = new WeakReference<List<F>>(mapped);
            this.mapper = mapper;
        }

        @Override
        public void onChanged(Change<? extends E> change) {
            final List<F> mapped = mappedRef.get();
            if (mapped == null) {
                change.getList().removeListener(this);
            } else {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        List<? extends E> orig = change.getList().subList(change.getFrom(), change.getTo());
                        List<F> sub = mapped.subList(change.getFrom(), change.getTo());
                        cache(orig, sub);
                        sub.clear();
                        mapped.addAll(change.getFrom(),
                                orig.stream().map(e -> computeIfAbsent(e)).collect(Collectors.toList()));
                    } else {
                        if (change.wasRemoved()) {
                            List<F> sub = mapped.subList(change.getFrom(), change.getFrom() + change.getRemovedSize());
                            if (change.wasAdded()) {
                                List<? extends E> orig = change.getRemoved();
                                cache(orig, sub);
                            }
                            sub.clear();
                        }
                        if (change.wasAdded())
                            mapped.addAll(change.getFrom(), change.getAddedSubList().stream()
                                    .map(e -> computeIfAbsent(e)).collect(Collectors.toList()));
                    }
                }
                cache = null;
            }
        }

        private void cache(List<? extends E> orig, List<F> mapped) {
            if (cache == null)
                cache = new IdentityHashMap<>();
            for (int i = 0; i < orig.size(); i++)
                cache.put(orig.get(i), mapped.get(i));
        }

        private F computeIfAbsent(E e) {
            F f = null;
            if (cache != null)
                f = cache.get(e);
            if (f == null)
                f = mapper.apply(e);
            return f;
        }

        @Override
        public boolean wasGarbageCollected() {
            return mappedRef.get() == null;
        }

        @Override
        public int hashCode() {
            final List<F> list = mappedRef.get();
            return (list == null) ? 0 : list.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final List<F> mapped1 = mappedRef.get();
            if (mapped1 == null) {
                return false;
            }

            if (obj instanceof ListContentMapping) {
                final ListContentMapping<?, ?> other = (ListContentMapping<?, ?>) obj;
                final List<?> mapped2 = other.mappedRef.get();
                return mapped1 == mapped2;
            }
            return false;
        }
    }
}
