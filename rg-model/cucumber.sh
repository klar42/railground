if [ -z "$1" ]; then
    echo "Usage: cucumber.sh <Event-B Machine file>.bum"
    exit 1
fi
groovy -cp ".:$CUCUMBER_JVM_HOME/*:$PROB_HOME/lib/*" -e 'this.class.classLoader.loadClass("cucumber.api.cli.Main").main(args)' -Deventb=$1 --glue features/step_definitions features/
