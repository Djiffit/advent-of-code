elems = []

with open('input/10') as f:
    lines = f.readlines()
    for line in lines:
        parts = line.split('> velocity=<')
        position = [int(x) for x in parts[0].split('<')[1].split(',')]
        velocity = [int(x) for x in parts[1][:-2].split(',')]
        elems.append((position, velocity))

def check_all_connected(elems):
    points = set([e[0] for e in elems])

    for (x, y) in points:
        if not ((x + 1, y) in points or (x - 1, y) in points or (x, y + 1) in points or (x, y - 1) in points):
            return False

    return True

time = 0

while True:
    elems = [((x[0] + y[0], x[1] + y[1]), (y)) for x, y in elems]
    time += 1
    
    points = set([e[0] for e in elems])
    min_x = min([x[0] for x, y in elems])
    max_x = max([x[0] for x, y in elems])
    
    min_y = min([x[1] for x, y in elems])
    max_y = max([x[1] for x, y in elems])
    
    if abs(min_y -  max_y) > 12:
        continue

    for y in range(min_y, max_y + 1):
        row = []

        for x in range(min_x, max_x + 1):
            if (x, y) in points:
                row.append('.')
            else:
                row.append('#')

        print(''.join(row))

    print(time)
    break

