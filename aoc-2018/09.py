import collections

class Node:
    prev = None
    next = None
    val = -1
    def __init__(self, val):
        self.val = val

for last_marble in (71019, 71019 * 100):
    players = 432   

    node = Node(0)
    node.next = node
    node.prev = node
    marble = 1
    player = 0

    def add_marble(val, curr):
        node = Node(val)
        node.prev = curr
        node.next = curr.next
        curr.next = node
        node.next.prev = node
        return node

    def remove(node):
        prev = node.prev
        next = node.next
        prev.next = next
        next.prev = prev
        return next

    scores = collections.defaultdict(int)
    active = node
    while marble <= last_marble:
        if marble % 23 == 0:
            scores[player] += marble
            for _ in range(7):
                active = active.prev
            scores[player] += active.val
            active = remove(active)
        else:
            active = add_marble(marble, active.next)
        player = (player +1) % players
        marble += 1

    print(max(scores.values()))


