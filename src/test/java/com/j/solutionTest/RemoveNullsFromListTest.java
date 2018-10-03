package com.j.solutionTest;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RemoveNullsFromListTest {
    private List<Integer> dummy;
    private List<Integer> foo;

    @Before
    public void setUp() {
        dummy = Lists.newArrayList(null, 1, null);
        foo = Lists.newArrayList(null, 1, 2, null, 3, null);
    }

    @Test
    public void cleanWithLoop() {
        while (dummy.remove(null));
        assertThat(dummy, hasSize(1));
    }

    @Test
    public void cleanWithPlainJava() {
        dummy.removeAll(Collections.singleton(null));
        assertThat(dummy, hasSize(1));
    }

    @Test
    public void cleanWithGuava() {
        Iterables.removeIf(dummy, Predicates.isNull());
        assertThat(dummy, hasSize(1));
    }

    @Test
    public void cleanWithGuavaII() {
        List<Integer> listWithoutNulls = Lists.newArrayList(
                Iterables.filter(dummy, Predicates.notNull())
        );
        assertThat(listWithoutNulls, hasSize(1));
    }

    @Test
    public void cleanWithLambda() {
        List<Integer> listWithoutNulls = foo.stream().filter(Objects::nonNull).collect(Collectors.toList());
        assertThat(listWithoutNulls, hasSize(3));
    }

    @Test
    public void cleanWithLambdaII() {
        foo.removeIf(Objects::isNull);
        assertThat(foo, hasSize(3));
    }
}
