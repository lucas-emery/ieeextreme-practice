
def cin = System.in.newReader()

def t = Integer.parseInt(cin.readLine())

for (int p = 0; p < t; p++) {
    def params = cin.readLine().split(' ')*.toInteger()
    def n = params[0] //dogs
    def k = params[1] //employees
    long[] dogs = new long[n]
    long[] diffs = new long[n]
    long total

    for (int i = 0; i < n; i++) {
        dogs[i] = Long.parseLong(cin.readLine())
    }

    Arrays.sort(dogs)

    for (int i = 0; i < n - 1; i++) {
        diffs[i] = dogs[i+1] - dogs[i]
    }

    Arrays.sort(diffs)

    total = dogs[n-1] - dogs[0]

    for (int i = 1; i < k; i++) {
        total -= diffs[n-i]
    }

    println total
}