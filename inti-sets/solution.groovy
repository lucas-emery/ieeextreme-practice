import groovy.ui.view.MacOSXDefaults

/*def euclides(long a, long b) {
    if(b == 0)
        return a

    euclides(b, a%b)
}*/

/*def coprime(long u, long v)
{
    if (((u | v) & 1) == 0)
        return false;

    while ((u & 1) == 0)
        u >>= 1;
    if (u == 1)
        return true;

    do {
        while ((v & 1) == 0)
            v >>= 1;
        if (v == 1)
            return true;

        if (u > v) {
            long t = v; v = u; u = t;
        }
        v -= u;
    } while (v != 0);

    return false;
}*/


def primeFactors(long n) {
    def factors = new ArrayList<Long>()
    long md = 2
    if (n % md == 0l) {
        factors.add(md)
        while (true) {
            n /= md
            if (n % md != 0l)
                break
        }
    }
    md = 3
    while (md <= Math.sqrt(n) + 1) {
        if (n % md == 0l) {
            factors.add(md)
            while (true) {
                n /= md
                if (n % md != 0l)
                    break
            }
        }
        md += 2
    }
    if (n > 1) {
        factors.add(n)
    }
    return factors
}

def sumUpTo(long k) {
    long smallk = k % 1000000007l
    long sum = ((smallk * (smallk+1))/ 2).toBigInteger() % 1000000007l
    return sum
}

def incExc(long a, long b, long k, List<Long> factors) {
    long smallk = k % 1000000007l
    long s1 = (sumUpTo((b/k).longValue()) * smallk) % 1000000007
    long s2 = (sumUpTo(((a-1)/k).longValue()) * smallk) % 1000000007

    long sum = (s1 - s2) % 1000000007l

    for (int i = 0; i < factors.size(); i++) {
        sum -= incExc(a, b, (k * factors[i]).longValue(), factors.subList(i+1, factors.size()))
        sum %= 1000000007l
    }

    return sum
}

def cin = System.in.newReader()

def queries = Integer.parseInt(cin.readLine())

for (int i = 0; i < queries; i++) {
    def params = cin.readLine().split(' ')*.toLong()
    long n = params[0]
    long a = params[1]
    long b = params[2]

    def factors = primeFactors(n)

    def sum = incExc(a, b, 1, factors)

    if(sum < 0)
        sum += 1000000007l

    println sum
}