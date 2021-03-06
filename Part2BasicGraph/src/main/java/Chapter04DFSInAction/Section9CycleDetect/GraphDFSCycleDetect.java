/***********************************************************
 * @Description : 以Chapter03DepthFirstTraversal.GraphDFS作为基础，进行无向图中的环检测
 * 原理：当检测到一个节点(当前节点current)的相邻节点已经被visited但是这个相邻节点不是current的上一个visited节点，就说明图中有环了
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2019-08-06 23:20
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package Chapter04DFSInAction.Section9CycleDetect;

import Chapter02GraphExpress.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphDFSCycleDetect {
    private Graph graph;

    /**
     * 存储顶点是否被访问的数组
     */
    private boolean[] visited;

    private boolean hasCycle = false;

    /**
     * 存放图的深度优先遍历的结果
     */
    private List<Integer> orderList = new ArrayList<>();

    public GraphDFSCycleDetect(Graph graph) {
        this.graph = graph;
        // 初始化访问数组，用图的顶点个数来访问
        visited = new boolean[graph.V()];
        // 从dfs(0)改成下面的代码，可以支持非连通的图,不用考虑连通分量的时候直接用dfs(v)即可
        for (int v = 0; v < graph.V(); v++) {
            if (!visited[v]) {
                if (dfs(v, v)){
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    public Iterable<Integer> getOrderList() {
        return orderList;
    }

    /**
     * 返回当前图是否有环
     */
    public boolean hasCycle() {
        return hasCycle;
    }

    /**
     * 深度优先遍历核心实现
     *
     * @param v      当前遍历到的点
     * @param parent v的上一个访问节点
     */
    private boolean dfs(int v, int parent) {
        visited[v] = true;
        orderList.add(v);
        for (Integer w : graph.adj(v)) {
            if (!visited[w]) {
                // w点没被访问的话就递归接着访问
                if (dfs(w, v)) {
                    return true;
                }
            } else if (w != parent) { // 新增代码：这个else分支就是无向图环检测的核心
                // 原理：当检测到一个节点(当前节点current)的相邻节点已经被visited但是这个相邻节点不是current的上一个visited节点，就说明图中有环了
                hasCycle = true;
            }
        }
        return false;
    }
}
