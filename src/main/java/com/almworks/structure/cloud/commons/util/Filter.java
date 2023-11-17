package com.almworks.structure.cloud.commons.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

class Filter {
    /**
     * A node is present in the filtered hierarchy iff its node ID passes the predicate and all of its ancestors pass it as well.
     * */
    static Hierarchy filter(Hierarchy hierarchy, IntPredicate nodeIdPredicate) {
        // todo implement
        final List<Integer> nodeIds = new LinkedList<>();
        final List<Integer> depths = new LinkedList<>();

        int currentIndex = 0;
        final Deque<Integer> siblingStack = new ArrayDeque<>();

        while (currentIndex < hierarchy.size()) {
            findNextSibling(hierarchy,
                            currentIndex,
                            Optional.ofNullable(siblingStack.peek())
                                    .orElse(hierarchy.size()))
                .ifPresent(siblingStack::push);

            if (nodeIdPredicate.test(hierarchy.nodeId(currentIndex))) {
                nodeIds.add(hierarchy.nodeId(currentIndex));
                depths.add(hierarchy.depth(currentIndex));

                currentIndex++;
                if (Objects.equals(siblingStack.peek(), currentIndex)) {
                    siblingStack.poll();
                }
            }
            else {
                currentIndex = Optional.ofNullable(siblingStack.poll())
                                       .orElse(hierarchy.size());
            }
        }

        return new ArrayBasedHierarchy(nodeIds.stream().mapToInt(Integer::intValue).toArray(),
                                        depths.stream().mapToInt(Integer::intValue).toArray());
    }

    static OptionalInt findNextSibling(Hierarchy hierarchy, int currentIndex, int lastIndex) {
        return IntStream.range(currentIndex + 1, lastIndex)
                .filter(index -> hierarchy.depth(index) == hierarchy.depth(currentIndex))
                .findFirst();
    }
}

