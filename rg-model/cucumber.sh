groovy -cp "$CUCUMBER_JVM_HOME/*:$PROB_HOME/lib/*" -e 'this.class.classLoader.loadClass("cucumber.api.cli.Main").main(args)' --glue features/step_definitions features/
