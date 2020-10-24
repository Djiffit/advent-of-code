from collections import Counter

def read_checksum(data):
    doubles = 0
    triples = 0
    for line in data:
        counts = Counter(line)
        doubles += 1 if (len([x for x in set(line) if counts[x] == 2]) > 0) else 0
        triples += 1 if (len([x for x in set(line) if counts[x] == 3]) > 0) else 0
    return doubles * triples
    
def common_ids(data):
    for i in range(len(data)):
        for j in range(i + 1, len(data)):
            diff = 0
            word = []
            for c in range(len(data[0])):
                if data[i][c] != data[j][c]:
                    diff += 1
                else:
                    word.append(data[i][c])
            if diff == 1:
                return ''.join(word)

with open('input/02') as f:
    lines = f.readlines()
    print(read_checksum(lines))
    print(common_ids(lines))

