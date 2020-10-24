rules = {}

def get_paddings(state):
    left_pad = right_pad = 0

    for i in range(5):
        if state[i] == '#':
            left_pad = 5 - i
            break

    for i in range(1, 6):
        if state[-i] == '#':
            right_pad = 6 - i
            break

    return left_pad, right_pad

with open('input/12') as f:
    lines = f.readlines()
    state = list(lines[0].strip())

    for rule in lines[2:]:
        parts = rule.split(' => ')
        rules[parts[0]] = parts[1].strip()

    left_pad, right_pad = get_paddings(state)
    state = ['.'] * left_pad + state + ['.'] * right_pad
    prev_score = 0

    for gen in range(1, 200):
        state = [state[i] if i < 2 or i > len(state) - 3 or ''.join(state[i-2:i+3]) not in rules else rules[''.join(state[i-2:i+3])] for i in range(len(state))]
        l_add, r_add = get_paddings(state)
        state = ['.'] * l_add + state + ['.'] * r_add
        left_pad += l_add
        score = sum([i - left_pad for i in range(len(state)) if state[i] == '#'])
        if gen == 20:
            print(score)

        if (score - prev_score) == 22:
            print(score + (22 * (50000000000 - gen)))
            break

        prev_score = score

