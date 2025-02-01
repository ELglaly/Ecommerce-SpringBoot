$files = git status --short
foreach ($line in $files) {
    $parts = $line -split "\s+", 2
    $status = $parts[0]
    $file = $parts[1]

    if ($status -eq "??") {
        git add$file"
        git commit -m "Create $file"
        Write-Host "Committed: Create $file"
    } elseif ($status -eq "M") {
        git add "$file"
        git commit -m "Update $file"
        Write-Host "Committed: Update $file"
    } elseif ($status -eq "D") {
        git rm "$file"
        git commit -m "Remove $file"
        Write-Host "Committed: Remove $file"
    }
}
