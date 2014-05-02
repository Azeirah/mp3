mp3
===

Project mp3 door studenten helden
We hebben twee branches

**dev** en **master**

Als je dingen wilt uitproberen, gebruik dan de dev branch

**Een stuk code uploaden**

git add .
git add commit -m "wat heb je gedaan, zet een korte beschrijving hier"
git push

**Je code updaten**

git pull

**switchen van branch**

git checkout dev (ga naar dev branch)
of
git checkout master (ga naar master branch)

**mergen van branches**

Als je vind dat dev goed genoeg is voor master, dan kan je mergen.

git merge master
Note: het is mogelijk dat hier merge conflicten voorkomen, je moet zelf uitzoeken hoe je deze oplost.
git checkout master
git merge dev (there won't be any conflicts now)
