# java19virtual

Test Java 19 Virtual Threads.

This demo shows that Virtual threads can be scaled much better than regular Java threads.

## Pre-requisites

Install java 19

```
brew install openjdk@19
```

## Compile and run

```
javac --enable-preview --release 19 MyVirtualApp.java
java --enable-preview  MyVirtualApp
```

Example output
```
Regular threads 1000:
sum = 1000; time = 10049 ms
Virtual threads 1000:
sum = 1000; time = 1036 ms
Virtual threads 100_000:
sum = 100000; time = 1244 ms
```

# Summary
It took 10 seconds for regular Thread pool (size 100) to execute 1_000 delayed by 1 second tasks.

It took 1 second for Virtual Thread pool to execute 1_000 delayed by 1 second tasks.

It took 1.2 second for Virtual Thread pool to execute 100_000: delayed by 1 second tasks.

# Credits
Examples are taken from https://www.happycoders.eu/java/virtual-threads/