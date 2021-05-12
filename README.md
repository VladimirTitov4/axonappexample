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
6. To see what's wrong with axon amqp, uncomment in pom.xml the axon amqp dependency and rerun the application and run createOrder mutation again, you will see that grouping of commands&events has been lost, and previously all commands, hanling commands, handling events has been visible, but after adding axon amqp dependency I handle only 3 Events and handle them separetely![2021-05-11_19-15]
7. First image is how traces have been handled without axon amqp. Second image how traces handled with axon amqp
8. (https://user-images.githubusercontent.com/48067168/117940481-c8d2b480-b311-11eb-8880-75ad27a10304.png)
![2021-05-11_19-15_1](https://user-images.githubusercontent.com/48067168/117940499-cc663b80-b311-11eb-8e38-23d4ee741e96.png)
