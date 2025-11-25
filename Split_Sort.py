def main():
    try:
        line = input().split()
        if not line:
            return
        n = int(line[0])
        q = int(line[1])
    except (EOFError, ValueError):
        return

    kebab = {}
    mophie = [0] * (n + 1)
    next_id = 1
    
    initial_keb = set(range(1, n + 1))
    kebab[0] = initial_keb
    
    for _ in range(q):
        try:
            line = input().split()
            if not line:
                break
            u = int(line[0])
            v = int(line[1])
        except (EOFError, ValueError):
            break

        id = mophie[u]
        split_energy = 0

        if id == mophie[v]:
            orig_keb_ref = kebab[id] 
            
            i, j = min(u, v), max(u, v)

            set_size = len(orig_keb_ref)
            range_size = j - i + 1

            old_keb = set() 
            if set_size < range_size:
                for moph in orig_keb_ref:
                    if i <= moph <= j:
                        old_keb.add(moph)
            else:
                for m in range(i, j + 1):
                    if mophie[m] == id: 
                        old_keb.add(m)

            old_size = len(old_keb)
            new_size = set_size - old_size

            if old_size > 0 and new_size > 0:
                split_energy = old_size * new_size
                new_id = next_id
                next_id += 1
                
                orig_keb_popped = kebab.pop(id) 

                if old_size < new_size:
                    small_set = old_keb
                    large_set_new = set()
                    for moph in orig_keb_popped:
                        if moph not in small_set:
                            large_set_new.add(moph)
                    
                    kebab[id] = large_set_new
                    kebab[new_id] = small_set
                    
                    for moph in small_set:
                        mophie[moph] = new_id
                else:
                    large_set = old_keb
                    small_set_new = set()
                    for moph in orig_keb_popped:
                        if moph not in large_set:
                            small_set_new.add(moph)

                    kebab[id] = large_set
                    kebab[new_id] = small_set_new

                    for moph in small_set_new:
                        mophie[moph] = new_id

        print(split_energy)

if __name__ == "__main__":
    main()