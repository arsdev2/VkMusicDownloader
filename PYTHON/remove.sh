rm out.json
zip -r music.zip /music
rm -r /music
cp music.zip /var/www/html/music.zip
rm music.zip
