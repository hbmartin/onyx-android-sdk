package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.Debug;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010!\n\u0002\u0008\u000b\n\u0002\u0010\u0008\n\u0002\u0008\n\n\u0002\u0010\u0002\n\u0002\u0008\u0005\n\u0002\u0010\u000b\n\u0002\u0008\u0008\n\u0002\u0018\u0002\n\u0002\u0008\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0006\u0018\u0000 ?*\u0008\u0008\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0001?B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00028\u00002\u0008\u0008\u0002\u0010\u001e\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u001fJ\u0014\u0010\u001b\u001a\u00020\u001c2\u000c\u0010 \u001a\u0008\u0012\u0004\u0012\u00028\u00000\u0000J\u001c\u0010!\u001a\u00020\"2\u000c\u0010#\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010$\u001a\u00020\u0011J\u0012\u0010%\u001a\u000e\u0012\n\u0012\u0008\u0012\u0004\u0012\u00028\u00000\u00000\u0005J\u000e\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020\u0011J\u0006\u0010(\u001a\u00020\"J\"\u0010)\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00002\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+J\"\u0010,\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00002\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+J&\u0010-\u001a\u000e\u0012\n\u0012\u0008\u0012\u0004\u0012\u00028\u00000\u00000\u00052\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+J\u001a\u0010.\u001a\u00020\u00112\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+J\u0006\u0010/\u001a\u00020\u0011J\u0016\u0010/\u001a\u00020\u00112\u000c\u00100\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u0000H\u0002J\u001a\u00101\u001a\u00020\"2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+J\u0006\u00102\u001a\u00020\"J,\u00103\u001a\u0008\u0012\u0004\u0012\u0002H40\u0005\"\u0004\u0008\u0001\u001042\u0018\u00105\u001a\u0014\u0012\n\u0012\u0008\u0012\u0004\u0012\u00028\u00000\u0000\u0012\u0004\u0012\u0002H40+JV\u00106\u001a\u00020\u001c2\u000c\u00107\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u00002\u0008\u0008\u0002\u00108\u001a\u00020\u001126\u0010*\u001a2\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\u000c\u0008:\u0012\u0008\u0008;\u0012\u0004\u0008\u0008(<\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\u000c\u0008:\u0012\u0008\u0008;\u0012\u0004\u0008\u0008(=\u0012\u0004\u0012\u00020\u001c09J\"\u0010>\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00002\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0+R&\u0010\u0004\u001a\u000e\u0012\n\u0012\u0008\u0012\u0004\u0012\u00028\u00000\u00000\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\u0008\u0006\u0010\u0007\"\u0004\u0008\u0008\u0010\tR\u001e\u0010\n\u001a\u0004\u0018\u00018\u0000X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\u0008\u000b\u0010\u000c\"\u0004\u0008\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\u0008\u0012\u0010\u0013\"\u0004\u0008\u0014\u0010\u0015R\"\u0010\u0016\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\u0008\u0017\u0010\u0018\"\u0004\u0008\u0019\u0010\u001a\u00a8\u0006@"}, d2 = {"Lcom/onyx/android/sdk/data/Tree;", "T", "", "()V", "children", "", "getChildren", "()Ljava/util/List;", "setChildren", "(Ljava/util/List;)V", "item", "getItem", "()Ljava/lang/Object;", "setItem", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "level", "", "getLevel", "()I", "setLevel", "(I)V", "parent", "getParent", "()Lcom/onyx/android/sdk/data/Tree;", "setParent", "(Lcom/onyx/android/sdk/data/Tree;)V", "addToChildren", "", "node", "index", "(Ljava/lang/Object;I)V", "tree", "canAdopt", "", "targetBranchNode", "maxLimit", "childrenToList", "exceedMaxLevel", "maxLevel", "exceedMiniLevel", "find", "predicate", "Lkotlin/Function1;", "findChild", "findChildren", "getChildrenCount", "getMaxDepth", "branchNode", "hasChild", "hasChildren", "mapChildren", "R", "transform", "reParent", "target", "insertPos", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "originItem", "targetItem", "remove", "Companion", "onyxsdk-base_release"})
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

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\u0008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0008\u0002\n\u0002\u0010\u0002\n\u0002\u0008\u0003\u0008\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0002JP\u0010\u0005\u001a\u0008\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0008\u0008\u0001\u0010\u0007*\u00020\u00012\u000c\u0010\u0008\u001a\u0008\u0012\u0004\u0012\u0002H\u00070\t2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000c0\u000b2\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000c0\u000bJ2\u0010\u000e\u001a\u00020\u000f\"\u0008\u0008\u0001\u0010\u0007*\u00020\u00012\u000c\u0010\u0010\u001a\u0008\u0012\u0004\u0012\u0002H\u00070\u00062\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u000c0\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/onyx/android/sdk/data/Tree$Companion;", "", "()V", "MINI_LEVEL", "", "create", "Lcom/onyx/android/sdk/data/Tree;", "T", "list", "", "keyMap", "Lkotlin/Function1;", "", "parentIdMap", "log", "", "tree", "getText", "onyxsdk-base_release"})
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
