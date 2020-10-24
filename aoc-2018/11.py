with open('input/11') as f:
    lines = f.readlines()

    inp = 8141
    grid = [[0] * 300 for _ in range(300)]
    
    for y in range(len(grid)):
        for x in range(len(grid)):
            rack_id = x + 1 + 10
            power = int(str((rack_id * (y + 1) + inp) * rack_id)[-3]) - 5
            grid[x][y] = power

    record = 0
    best = ''

    for y in range(len(grid) - 2):
        for x in range(len(grid) - 2):
            total = 0
            for y_d in range(3):
                for x_d in range(3):
                    total += grid[x + x_d][y+y_d]

            if total > record:
                record = total
                best = (x + 1, y + 1)

    print(best, record)

    colsums = [[0] * 301 for _ in range(301)]
    rowsums = [[0] * 301 for _ in range(301)]

    for y in range(len(grid)):
        for x in range(len(grid)):
            rowsums[x][y] = grid[x][y] + (0 if x == 0 else rowsums[x - 1][y])
            colsums[x][y] = grid[x][y] + (0 if y == 0 else colsums[x][y - 1])
            
    record = 0
    best = ''
    
    for y in range(300):
        for x in range(300):
            for size in range(min(20, 300 - max(y, x))):
                total = 0
                for d in range(size):
                    total += rowsums[x + d][y + d] - (0 if x == 0 else rowsums[x - 1][y + d])
                    total += colsums[x + d][y + d] - (0 if y == 0 else colsums[x + d][y - 1])
                    total -= grid[x+d][y+d]

                if total > record:
                    best = (x + 1, y + 1, size)
                    record = total

    print(best, record)
