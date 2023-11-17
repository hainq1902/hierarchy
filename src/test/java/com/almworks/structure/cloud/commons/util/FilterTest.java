package com.almworks.structure.cloud.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class FilterTest {
    @Test
    public void testFilter() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         x  x     x  x     x  x     x   x
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2, 5, 8, 10, 11},
                new int[] {0, 1, 1, 0, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterManySiblingAndMultiRoot() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[] {1, 2, 3, 4,  9,  33,  7,  6, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3,  3,  3,  3,  3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2, 5, 8, 10, 11},
                new int[] {0, 1, 1, 0, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterWhenOnlyLeftBranch() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[] {1, 2, 3, 4, 5, 5, 7},
                new int[] {0, 1, 2, 3, 4, 5, 6}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2},
                new int[] {0, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterWhenOnlyLeftBranchMultiRoot() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[] {1, 2, 3, 4, 9, 33, 7, 5, 5, 7, 1, 2, 3, 4, 5, 5, 7},
                new int[] {0, 1, 2, 3, 3, 3, 3, 4, 5, 6, 0, 1, 2, 3, 4, 5, 6}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2, 1, 2},
                new int[] {0, 1, 0, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterWhenOnlyMultiRoot() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[] {1, 1},
                new int[] {0, 0}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 1},
                new int[] {0, 0}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterTrue() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         0  1  2  3  4  5  6  7  8  9   10
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> true);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterFalse() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         0  1  2  3  4  5  6  7  8  9   10
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> false);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {},
                new int[] {}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterManySiblings() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         0  1  2  3  4  5  6  7  8  9   10
                new int[] {1, 2, 31, 4, 5, 61, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 3, 3, 3, 3, 4, 4, 4}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {1, 2, 31, 4, 5, 61, 7, 8, 10, 11},
                new int[] {0, 1, 2, 3, 3, 3, 3, 3, 4, 4}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterWithFailRoot() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         x  x     x  x     x  x     x   x
                new int[] {3, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[] {0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = Filter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[] {8, 10, 11},
                new int[] {0, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }
}
