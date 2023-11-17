package com.almworks.structure.cloud.commons.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Filter {
    /**
     * A node is present in the filtered hierarchy iff its node ID passes the predicate and all of its ancestors pass it as well.
     * */
    public static Hierarchy filter(Hierarchy hierarchy, IntPredicate nodeIdPredicate) {
        // todo implement
        final List<Integer> nodeIds = new LinkedList<>();
        final List<Integer> depths = new LinkedList<>();

        int currentIndex = 0;
        final Deque<Integer> siblingStack = new ArrayDeque<>();

        while (currentIndex < hierarchy.size()) {
            findNextSibling(hierarchy,
                            currentIndex,
                            Optional.ofNullable(siblingStack.peek()).orElse(hierarchy.size()))
                .ifPresent(siblingStack::push);

            if (nodeIdPredicate.test(hierarchy.nodeId(currentIndex))) {
                addToResult(hierarchy, nodeIds, depths, currentIndex);

                currentIndex = moveToNextIndex(currentIndex, siblingStack);
            }
            else {
                currentIndex = moveToNextSibling(hierarchy, siblingStack);
            }
        }

        return new ArrayBasedHierarchy(nodeIds.stream().mapToInt(Integer::intValue).toArray(),
                                        depths.stream().mapToInt(Integer::intValue).toArray());
    }

    private static int moveToNextSibling(Hierarchy hierarchy, Deque<Integer> siblingStack) {
        return Optional.ofNullable(siblingStack.poll())
                       .orElse(hierarchy.size());
    }

    private static int moveToNextIndex(int currentIndex, Deque<Integer> siblingStack) {
        currentIndex++;
        if (Objects.equals(siblingStack.peek(), currentIndex)) {
            siblingStack.poll();
        }
        return currentIndex;
    }

    private static void addToResult(Hierarchy hierarchy, List<Integer> nodeIds, List<Integer> depths, int currentIndex) {
        nodeIds.add(hierarchy.nodeId(currentIndex));
        depths.add(hierarchy.depth(currentIndex));
    }

    private static OptionalInt findNextSibling(Hierarchy hierarchy, int currentIndex, int lastIndex) {
        return IntStream.range(currentIndex + 1, lastIndex)
                        .filter(index -> hierarchy.depth(index) == hierarchy.depth(currentIndex))
                        .findFirst();
    }
}

