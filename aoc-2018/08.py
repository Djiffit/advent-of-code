from collections import defaultdict

nums = []
total_meta = 0

class Solution:
    def __init__(self, nums):
        self.total_meta = 0
        self.nums = nums
        self.values = defaultdict(int)

    def read_metadata(self, pos):
        start = pos
        children = self.nums[pos]
        metadata = self.nums[pos + 1]
        act_children = []
        pos += 2

        if children:
            for _ in range(children):
                act_children.append(pos)
                pos += self.read_metadata(pos)
            act_children = [self.values[x] for x in act_children]

        total_meta = 0
        val = 0

        for _ in range(metadata):
            if children and 0 <= self.nums[pos] - 1 < len(act_children):
                val += act_children[self.nums[pos] - 1]

            self.total_meta += self.nums[pos]
            total_meta += self.nums[pos]
            pos += 1

        if not children:
            self.values[start] = total_meta
        else:
            self.values[start] = val

        return pos - start

with open('input/08') as f:
    nums = [int(x) for x in f.readlines()[0].split()]
    sol = Solution(nums)
    sol.read_metadata(0)
    print(sol.total_meta)
    print(sol.values[0])

