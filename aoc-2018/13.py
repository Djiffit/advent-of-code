import collections
import os
from heapq import heappop, heappush, heapify

dirs = [(-1, 0), (0, 1), (1, 0), (0, -1)]
cart = ['^', '>', 'v', '<']
grid = []
carts = []
cart_id = 0
turns = collections.defaultdict(int)
crash_ids = set()

with open('input/13') as f:
    lines = f.readlines()
    for y, line in enumerate(lines):
        grid.append(list(line[:-1]))

    for y in range(len(grid)):
        for x in range(len(grid[y])):
            if grid[y][x] in cart:
                heappush(carts, ((y, x), dirs[cart.index(grid[y][x])], cart_id))
                cart_id += 1

                if grid[y][x] in ['^', 'v']:
                    grid[y][x] = '|'
                else:
                    grid[y][x] = '-'
                #grid[y][x] = replacements.pop(0)

    while True:
        if False:
            words = []
            for y in range(len(grid)):
                word = []
                for x in range(len(grid[y])):
                    iscart = False
                    for cart in carts:
                        if cart[0][0] == y and cart[0][1] == x:
                            word.append('C')
                            iscart = True
                            
                    if not iscart: 
                        word.append(grid[y][x])
                words.append(''.join(word))

            os.system('cls' if os.name == 'nt' else "printf '\033c'")
            print('\n'.join(words))

        new_carts = []
        while len(carts) > 0:
            crash = False
            crash_cart = ''
            cart = heappop(carts)
            if cart[2] in crash_ids:
                continue
            cart = ((cart[0][0] + cart[1][0], cart[0][1] + cart[1][1]), cart[1], cart[2])
            assert(cart[0][0] >= 0)
            assert(cart[0][1] >= 0)
            assert(grid[cart[0][0]][cart[0][1]] != ' ')
            for o_cart in carts + new_carts:
                if o_cart[2] == cart[2]:
                    continue
                if o_cart[0] == cart[0]:
                    if len(crash_ids) == 0:
                        print(cart[0], "CRAHS")
                    crash = True
                    crash_cart = o_cart
                    crash_ids.add(o_cart[2])
                    crash_ids.add(cart[2])

            tile = grid[cart[0][0]][cart[0][1]]
            dir_ind = dirs.index(cart[1])
            if tile == '/':
                if dir_ind == 0:
                    cart = (cart[0], dirs[1], cart[2])
                elif dir_ind == 1:
                    cart = (cart[0], dirs[0], cart[2])
                elif dir_ind == 2:
                    cart = (cart[0], dirs[3], cart[2])
                else:
                    cart = (cart[0], dirs[2], cart[2])
            elif tile == '\\':
                if dir_ind == 0:
                    cart = (cart[0], dirs[3], cart[2])
                elif dir_ind == 1:
                    cart = (cart[0], dirs[2], cart[2])
                elif dir_ind == 2:
                    cart = (cart[0], dirs[1], cart[2])
                else:
                    cart = (cart[0], dirs[0], cart[2])
            elif tile == '+':
                turn = turns[cart[2]]
                if turn == 0:
                    cart = (cart[0], dirs[(dir_ind - 1) % 4], cart[2])
                elif turn == 1:
                    cart = (cart[0], cart[1], cart[2])
                else:
                    cart = (cart[0], dirs[(dir_ind + 1) % 4], cart[2])
                turns[cart[2]] = (turn + 1) % 3
            new_carts.append(cart)
        carts = [x for x in new_carts if x[2] not in crash_ids]
        heapify(carts)
        if len(carts) == 1:
            print(carts[0][0])
            exit()

