import re
from collections import defaultdict

events = []
guard_sleeps = defaultdict(int)
guard_total_sleep = defaultdict(int)

def read_time(time):
    time = time[1:]
    dash = ''.join(time.split(" ")[0]).split("-")
    time_part = ''.join(time.split(" ")[1]).split(":")
    hour = time_part[0]
    minute = time_part[1]
    return (dash[0], dash[1], dash[2], hour, minute)

def read_events(lines):
    for line in lines:
        time = read_time(line.split("]")[0])
        event = line.split("]")[1]
        events.append((time, event))
    events.sort()

def track_guards(part2 = False):
    read_events()

    best_guard = ''
    best_sleep = 0

    for g, s in guard_total_sleep.items():
        if s > best_sleep:
            best_sleep = s
            best_guard = g

    best_min = 0
    max_min = 0

    if part2:
        for guard in guard_total_sleep.keys():
            for i in range(60):
                if guard_sleeps[(guard, i)] > max_min:
                    best_min = i
                    max_min = guard_sleeps[(guard, i)]
                    best_guard = guard

        return (int(best_guard[1:]) * best_min)

    for i in range(60):
        if guard_sleeps[(best_guard, i)] > max_min:
            best_min = i
            max_min = guard_sleeps[(best_guard, i)]

    return best_min * int(best_guard[1:])

with open('input/04') as f:
    lines = f.readlines()
    read_events(lines)
    print(track_guards())
    print(track_guards(True))

