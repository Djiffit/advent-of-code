from collections import defaultdict
import random

with open('input/06') as f:
    lines = f.readlines()
    points = []

    for line in lines:
        points.append(line.split(", "))

    points = [(int(x[0]), int(x[1])) for x in points]

    min_x = min([int(x[0] )for x in points])
    min_y = min([int(x[1] )for x in points])
    max_x = max([int(x[0]) for x in points])
    max_y = max([int(x[1] )for x in points])

    def part1():
        grid = defaultdict(int)

        def get_closest(y, x):
            best_dist = float('inf')
            closest = ''
            tied = False

            for point in points:
                dist = abs(y - point[1]) + abs(x - point[0])
                if dist == best_dist:
                    tied = True
                elif dist < best_dist:
                    closest = point
                    best_dist = dist
                    tied = False
            if not tied:
                return closest
            return (-1, -1)
        
        for y in range(min_y, max_y + 1):
            for x in range(min_x, max_x + 1):
                closest = get_closest(y, x)
                grid[closest] += 1
                if y == min_y or y == max_y or x == min_x or x == max_x:
                    grid[closest] -= 10000

        print(sorted(grid.values())[-1])

    def part2():
        que = [points[0]]
        visited = set()
        total = 0

        while len(que) > 0:
            curr = que.pop()
            if curr in visited:
                continue
            visited.add(curr)
            valid = True
            dist = 0
            for point in points:
                dist += abs(point[0] - curr[0]) + abs(point[1] - curr[1])
            if dist > 10000:
                continue
            total += 1
            que.append((curr[0] + 1, curr[1]))
            que.append((curr[0] - 1, curr[1]))
            que.append((curr[0], 1 + curr[1]))
            que.append((curr[0], -1 + curr[1]))

        print(total)

    part1()
    part2()
        
