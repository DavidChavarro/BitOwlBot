KERNEL=$(uname -s)

if [["$KERNEL" == "Linux"]] then
    ID_LIKE=$(cat /etc/os-release | grep ID_LIKE)
    i=0
    ID_L_ARR=$(echo $ID_LIKE | tr "=" "\n")
    for iter in $ID_L_ARR
        if [[i == 1]] then
            ID_LIKE=$iter
            break
        else
            i=$i + 1
    fi
    if [["$ID_LIKE" == "debian"]]; then
        echo "Installing dependencies...\n"
        sudo apt-get install libffi-dev libnacl-dev python3-dev
    else
        echo "You are using a(n) $ID_LIKE distro. Please install the following dependencies:\n"
        dependencies=("libffi" "libnacl" "python3-dev")
        for dependency in $dependencies
            echo "$dependency\n"
        fi
        echo "Please refer to https://discordpy.readthedocs.io/en/stable/intro.html for more info.\n"
        read -n 1 -s -r -p "Press enter once you are done."
    echo "Installing Discord API...\n"
    source bot-env/bin/activate
    python3 -m pip install -r requirements.txt

else
    echo "You are not running Linux. Please refer to https://discordpy.readthedocs.io/en/stable/intro.html for more info.\n"
fi
