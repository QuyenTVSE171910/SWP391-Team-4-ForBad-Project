echo "Building app..."
./mvnw clean package

echo "Deploy files to server..."
scp -r -i ~/Desktop/forbad-system-be target/forbad-system-be.jar root@167.99.67.127:/var/www/forbad-system-be/

ssh -i ~/Desktop/forbad-system-be root@167.99.67.127 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/forbad-system-be
java -jar forbad-system-be.jar
EOF
exit
echo "Done!"