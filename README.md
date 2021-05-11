Simple Axon Framework application

1. docker-compose up
2. Run the application
3. go to localhost:8080/altair and send createOrder mutation 
mutation{
  createOrder(name: "serf")
}
4. In conslole will be logged three events, this is all this application has been created to, to demostrante sequence of events and find them in jaeger dashboard
  #1 order has been created
  #2 order has been payed
  #3 order has been delivered
5. Traces could be seen in http://localhost:16686/search
choose axon_app_example service and click "find traces"
under [Post] trace you will see sequence of commands & events
6. to see what's wrong with axon amqp, uncomment in pom.xml the axon amqp dependency and rerun the application and run createOrder mutation again, you will see that sequence of events has been lost 
