def solve(data):
    rec = float('inf')
    for i in range(26):
        char = chr(ord('a') + i)
        rec = min(rec, (len(react(data.replace(char, '').replace(char.upper(), '')))))
        if rec == 6694:
            return rec

def react(word):
    loop = True
    while loop:
        loop = False
        for i in range(26):
            curr = chr(ord('a') + i)
            c1 = curr + curr.upper()
            c2 = curr.upper() + curr
            if c1 in word or c2 in word:
                target = chr(ord(curr) - 1)
                t1 = '' if curr == 'a' else target + target.upper()
                t2 = '' if curr == 'a' else target.upper() + target
                word = word.replace(c1, t1)
                word = word.replace(c2, t2)
                loop = True
    return word

with open('input/05') as f:
    line = f.readlines()[0].strip()
    print(len(react(line)))
    print(solve(line))

