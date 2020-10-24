from collections import defaultdict

claims = defaultdict(int)

def get_details(claim):
    coords, size = claim.split("@")[1].split(":")
    x, y = (int(coords.split(",")[0]), int(coords.split(",")[1]))
    size = (int(size.split("x")[0]), int(size.split("x")[1]))
    return size, x, y

def overlap(data):
    total = 0
    for claim in data:
        size, x, y = get_details(claim)
        for x_inc in range(size[0]):
            for y_inc in range(size[1]):
                if claims[(x + x_inc, y + y_inc)] == 1:
                    total += 1
                claims[(x + x_inc, y + y_inc)] += 1

    return total

def find_free(data):
    for claim in data:
        size, x, y = get_details(claim)
        total = 0
        for x_inc in range(size[0]):
            for y_inc in range(size[1]):
                if claims[(x + x_inc, y + y_inc)] > 1:
                    total += 1

        if total == 0:
            return claim

with open('input/03') as f:
    lines = f.readlines()
    print(overlap(lines))
    print(find_free(lines))

