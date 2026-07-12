package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.Debug;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Tree<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int MINI_LEVEL = 1;

    @Nullable private T a;
    @Nullable private Tree<T> b;
    @NotNull private List<Tree<T>> c = new ArrayList<>();
    private int d;

    public Tree() {}

    @Nullable
    public final T getItem() {
        return a;
    }

    public final void setItem(@Nullable T value) {
        a = value;
    }

    @Nullable
    public final Tree<T> getParent() {
        return b;
    }

    public final void setParent(@Nullable Tree<T> value) {
        b = value;
    }

    @NotNull
    public final List<Tree<T>> getChildren() {
        return c;
    }

    public final void setChildren(@NotNull List<Tree<T>> value) {
        Intrinsics.checkNotNullParameter(value, "<set-?>");
        c = value;
    }

    public final int getLevel() {
        return d;
    }

    public final void setLevel(int value) {
        d = value;
    }

    public final boolean hasChildren() {
        return !c.isEmpty();
    }

    @Nullable
    public final Tree<T> remove(@NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Tree<T> found = find(predicate);
        if (found != null && found.b != null) {
            found.b.c.remove(found);
        }
        return found;
    }

    @Nullable
    public final Tree<T> find(@NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (a != null && Boolean.TRUE.equals(predicate.invoke(a))) {
            return this;
        }
        for (Tree<T> child : c) {
            Tree<T> found = child.find(predicate);
            if (found != null) return found;
        }
        return null;
    }

    @Nullable
    public final Tree<T> findChild(@NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        LinkedList<Tree<T>> queue = new LinkedList<>(c);
        while (!queue.isEmpty()) {
            Tree<T> node = queue.removeFirst();
            if (node.a != null && Boolean.TRUE.equals(predicate.invoke(node.a))) {
                return node;
            }
            queue.addAll(node.c);
        }
        return null;
    }

    @NotNull
    public final List<Tree<T>> findChildren(@NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        List<Tree<T>> result = new ArrayList<>();
        LinkedList<Tree<T>> queue = new LinkedList<>(c);
        while (!queue.isEmpty()) {
            Tree<T> node = queue.removeFirst();
            if (node.a != null && Boolean.TRUE.equals(predicate.invoke(node.a))) {
                result.add(node);
            }
            queue.addAll(node.c);
        }
        return result;
    }

    public final int getChildrenCount(@NotNull Function1<? super T, Boolean> predicate) {
        return findChildren(predicate).size();
    }

    public final boolean hasChild(@NotNull Function1<? super T, Boolean> predicate) {
        return findChild(predicate) != null;
    }

    private int a(Tree<T> branchNode) {
        int max = 0;
        for (Tree<T> child : branchNode.c) {
            max = Math.max(max, a(child));
        }
        return max + 1;
    }

    public final int getMaxDepth() {
        return a(this);
    }

    public final boolean canAdopt(@NotNull Tree<T> targetBranchNode, int maxLimit) {
        Intrinsics.checkNotNullParameter(targetBranchNode, "targetBranchNode");
        return getMaxDepth() + targetBranchNode.d <= maxLimit;
    }

    @NotNull
    public final List<Tree<T>> childrenToList() {
        List<Tree<T>> result = new ArrayList<>();
        LinkedList<Tree<T>> pending = new LinkedList<>(c);
        while (!pending.isEmpty()) {
            Tree<T> node = pending.removeFirst();
            result.add(node);
            if (node.hasChildren()) pending.addAll(0, node.c);
        }
        return result;
    }

    @NotNull
    public final <R> List<R> mapChildren(
            @NotNull Function1<? super Tree<T>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        List<R> result = new ArrayList<>();
        for (Tree<T> child : childrenToList()) {
            result.add(transform.invoke(child));
        }
        return result;
    }

    public final void reParent(@NotNull Tree<T> target, int insertPos,
                              @NotNull Function2<? super T, ? super T, Unit> predicate) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (b != null) b.c.remove(this);
        d = target.d + 1;
        b = target;
        target.c.add(insertPos, this);
        if (a != null && target.a != null) predicate.invoke(a, target.a);
    }

    public static void reParent$default(Tree tree, Tree target, int insertPos,
                                       Function2 predicate, int mask, Object marker) {
        if ((mask & 2) != 0) insertPos = 0;
        tree.reParent(target, insertPos, predicate);
    }

    public final void addToChildren(@NotNull Tree<T> tree) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        tree.b = this;
        tree.d = d + 1;
        c.add(tree);
    }

    public final void addToChildren(@NotNull T node, int index) {
        Intrinsics.checkNotNullParameter(node, "node");
        Tree<T> tree = new Tree<>();
        tree.a = node;
        tree.d = d + 1;
        tree.b = this;
        c.add(Math.min(index, c.size()), tree);
    }

    public static void addToChildren$default(Tree tree, Object node, int index,
                                            int mask, Object marker) {
        if ((mask & 2) != 0) index = 0;
        tree.addToChildren(node, index);
    }

    public final boolean exceedMiniLevel() {
        return d <= MINI_LEVEL;
    }

    public final boolean exceedMaxLevel(int maxLevel) {
        return d >= maxLevel;
    }

    public static final class Companion {
        private Companion() {}

        public Companion(DefaultConstructorMarker marker) {
            this();
        }

        @NotNull
        public final <T> Tree<T> create(@NotNull List<? extends T> list,
                                       @NotNull Function1<? super T, String> keyMap,
                                       @NotNull Function1<? super T, String> parentIdMap) {
            Intrinsics.checkNotNullParameter(list, "list");
            Intrinsics.checkNotNullParameter(keyMap, "keyMap");
            Intrinsics.checkNotNullParameter(parentIdMap, "parentIdMap");
            Tree<T> root = new Tree<>();
            Map<String, Tree<T>> nodes = new LinkedHashMap<>();
            for (T item : list) {
                Tree<T> node = new Tree<>();
                node.setItem(item);
                nodes.put(keyMap.invoke(item), node);
            }
            for (T item : list) {
                Tree<T> node = nodes.get(keyMap.invoke(item));
                String parentId = parentIdMap.invoke(item);
                Tree<T> parent = parentId == null || parentId.isEmpty()
                        ? root : nodes.get(parentId);
                if (node != null && parent != null) {
                    node.setParent(parent);
                    parent.getChildren().add(node);
                }
            }
            LinkedList<Tree<T>> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Tree<T> parent = queue.removeFirst();
                for (Tree<T> child : parent.getChildren()) {
                    child.setLevel(parent.getLevel() + 1);
                    queue.add(child);
                }
            }
            return root;
        }

        public final <T> void log(@NotNull Tree<T> tree,
                                  @NotNull Function1<? super T, String> getText) {
            Intrinsics.checkNotNullParameter(tree, "tree");
            Intrinsics.checkNotNullParameter(getText, "getText");
            for (Tree<T> child : tree.getChildren()) {
                StringBuilder prefix = new StringBuilder();
                for (int i = 0; i <= child.getLevel(); i++) prefix.append('-');
                if (child.getItem() != null) {
                    Debug.d("treeItem", prefix + getText.invoke(child.getItem()), new Object[0]);
                }
                if (child.hasChildren()) log(child, getText);
            }
        }
    }
}
