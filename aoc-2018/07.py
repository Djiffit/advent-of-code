
import heapq
import collections
def solve(data):
    deps = collections.defaultdict(set)
    nexts = collections.defaultdict(set)
    nodes = set()
    for line in data:
        parts = line.split(" must be finished before step ")
        deps[parts[1][0]].add(parts[0][-1])
        nexts[parts[0][-1]].add(parts[1][0])
        nodes.add(parts[1][0])
        nodes.add(parts[0][-1])

    res = []
    heap = []
    visited = set()
    processing = set()
    def dfs(node):
        if len(deps[node]) > 0 or node in visited or node in processing:
            return
        visited.add(node)
        res.append(node)
        for dep in sorted(list(nexts[node])):
            deps[dep].remove(node)
            if len(deps[dep]) == 0 and dep not in visited and dep not in processing:
                heapq.heappush(heap, dep)
                processing.add(dep)

    workers = 5
    def get_duration(node):
        return 60 + ord(node) - ord('A') + 1

    active_nodes = []
    starts = []
    time = 0
    for node in nodes:
        if len(deps[node]) == 0:
            starts.append(node)
    for start in sorted(nodes):
        if len(deps[start]) == 0:
            heapq.heappush(heap, start)
            processing.add(start)

    while len(heap) or len(active_nodes):
        while len(heap) and len(active_nodes) < workers:
            curr = heapq.heappop(heap)
            active_nodes.append((curr, get_duration(curr)))
        new_nodes = []
        for node, dur in active_nodes:
            if dur == 1:
                processing.remove(node)
                dfs(node)
            else:
                new_nodes.append((node, dur -1))
        active_nodes = new_nodes
        time += 1
            
    print(res, nodes, nexts)
    print(''.join(list((res))))
    print(time)
    

with open('input/07') as f:
    lines = f.readlines()
    solve(lines)
