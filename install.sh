KERNEL=$(uname -s)

echo $KERNEL

if [[ $KERNEL == "Linux" ]]
then
    ID_LIKE=$(cat /etc/os-release | grep ID_LIKE)
    i=0
    ID_L_ARR=$(echo $ID_LIKE | tr "=" "\n")
    for iter in $ID_L_ARR; do
        if [[ $i == 1 ]]
        then
            echo $iter
            ID_LIKE=$iter
            break;
        else
            i=$((i+1))
        fi
    done;

    if [[ $ID_LIKE == "debian" ]]; then
        echo "Installing dependencies..."
        sudo apt-get install libffi-dev libnacl-dev python3-dev
    else
        echo "You are using a(n) $ID_LIKE distro. Please install the following dependencies:"
        dependencies=("libffi" "libnacl" "python3-dev")
        for dependency in $dependencies; do
            echo "$dependency"
        done;
        echo "Please refer to https://discordpy.readthedocs.io/en/stable/intro.html for more info."
        read -n 1 -s -r -p "Press enter once you are done."
    fi

    echo "Installing Discord API..."
    source bot-env/bin/activate
    python3 -m pip install -r requirements.txt
else
    echo "You are not running Linux. Please refer to https://discordpy.readthedocs.io/en/stable/intro.html for more info."
fi