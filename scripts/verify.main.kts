// OK Foods release verifier — checks a downloaded release asset against SHA256SUMS.txt
//
// Usage (from the repository root or any directory holding the files):
//   kotlinc -script scripts/verify.main.kts            # verifies files listed in ./SHA256SUMS.txt
//   kotlinc -script scripts/verify.main.kts /path/dir  # verifies files listed in /path/dir/SHA256SUMS.txt
//
// Exit code 0 = all checksums match (or match was checked successfully), 1 = mismatch/missing, 2 = usage error.

import java.io.File
import java.security.MessageDigest
import kotlin.system.exitProcess

fun sha256Hex(file: File): String {
    val digest = MessageDigest.getInstance("SHA-256")
    file.inputStream().use { input ->
        val buffer = ByteArray(64 * 1024)
        while (true) {
            val read = input.read(buffer)
            if (read == -1) break
            digest.update(buffer, 0, read)
        }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
}

val root = File(args.getOrNull(0) ?: ".").canonicalFile
val sums = File(root, "SHA256SUMS.txt")

if (!sums.isFile) {
    System.err.println("SHA256SUMS.txt not found in $root")
    exitProcess(2)
}

var checked = 0
var failed = 0
for (rawLine in sums.readLines()) {
    val line = rawLine.trim()
    if (line.isEmpty() || line.startsWith("#")) continue

    // "<64-hex>  <file name>" — single or multiple spaces between the fields.
    val match = Regex("""^([0-9a-fA-F]{64})\s+(.+)$""").matchEntire(line)
    if (match == null) {
        System.err.println("Ignoring unparseable line: $line")
        continue
    }
    val (expected, fileName) = match.destructured
    val target = File(root, fileName)
    if (!target.isFile) {
        println("FAIL  $fileName — file not found (looked in $root)")
        checked++
        failed++
        continue
    }
    val actual = sha256Hex(target)
    if (actual.equals(expected, ignoreCase = true)) {
        println("OK    $fileName (${target.length()} bytes)")
    } else {
        println("FAIL  $fileName — expected $expected, got $actual")
        failed++
    }
    checked++
}
if (checked == 0) {
    System.err.println("SHA256SUMS.txt contains no entries")
    exitProcess(2)
}
println()
println(if (failed == 0) "PASS — $checked/$checked file(s) match" else "FAILED — $failed of $checked file(s) did not match")
exitProcess(if (failed == 0) 0 else 1)
