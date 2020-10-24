def read_frequency(part2=False):
    frequency = 0
    frequencies = set()
    with open('input/01') as f:
        lines = f.readlines()
    while True:
        for line in lines:
            frequency += int(line)
            if frequency in frequencies:
                return frequency
            frequencies.add(frequency)
        if not part2:
            return frequency
print(read_frequency())
print(read_frequency(True))
