= Laboratory Report - Formatters, Sinks, and Display Shop

*Student:* Andrei Varvariuc, FAF-251 \
*Programming language:* Java \
*Track:* Display Shop

*Answer each section briefly, using your own code as examples.
Do not write textbook definitions without showing where the concept
appears in your program.*

== 1. The logger, split in three
- What is the single responsibility of Logger, Formatter, and ConsoleSink?
*ConsoleSink* takes care of writing any text into the console (as if flushing it down a sink). The *Formatter* takes care of, well, formatting how the log message looks like. And in the end, the *Logger* is responsible for taking a message and using both the *ConsoleSink* and the *Formatter* to complete the process of logging a message.
- What happens in your formatter if the message itself contains "%"?
If no additional arguments were passed, then it will be printed just as that, a "%". I thought this is the best option, as otherwise the solution would be to throw a runtime error, and we don't want that in a compiled language, and ESPECIALLY not from a logger.
- Which console prints did you remove, and where do they live now?
The only console prints I removed where the ones located in the *Logger* class directly. Now they live in the *ConsoleSink* class.

== 2. Composition and aggregation
- Show the two Logger constructors. Which one is composition,
which one is aggregation? Explain with object lifetimes.
```java
  Logger(String name) {
    this.name = name;
    this.formatter = new Formatter();
    this.sink = new ConsoleSink();
  }

  Logger(String name, Formatter formatter, Sink sink) {
    this.name = name;
    this.formatter = formatter;
    this.sink = sink;
  }
```

The first instance creates a Formatter and a ConsoleSink specifically for that logger and for that logger only. That means, that those two class instances will not live longer than the Logger class itself, since no other class has a reference to those instances. This is the "composition". The second instance has the "aggregation" principle, since it takes references to those two class instances from the outside, meaning they probably exist somewhere else, and with the termination of the Logger instance those two instances will not be garbage collected.
- Find one more example of each in your track. Include snippets.

```java
  Formatter() {
    this.template = "[%t] [%l] %n: %m";  // composition
  }

  Formatter(String template) {
    this.template = template;  // aggregation
  }
```

```java
  Display(String model, Integer width, Integer height, double ppi) {
    this(model, width, height, ppi, new Logger(model));  // composition
  }

  Display(String model, Integer width, Integer height, double ppi, Logger logger) {
    this.model = model;  // aggregation
    this.width = width;
    this.height = height;
    this.ppi = ppi;
    this.logger = logger;

    this.logger.trace("A display was created: '%'", this.model);
  }
```


- The same Display was assigned to Bob and Alice, and Bob
sold it. What did Alice show? Why?
Alice showed that that display was still there. This is because Alice and Bob cannot share information between each other, since the current design does not entail the DisplayManager class to know the existence of other DisplayManagers. In other words, Alice doesn't know Bob exists, and Bob doesn't know that Alice exists and works in the same shop. As a result of that, every action one manager takes is not reflected on other managers. Is this possible to solve? Yes, but that requires granting the meta-physical knowledge of existence of other managers and an array of all available displays.

== 3. Static vs shared objects
- In Lab 1 the format was static. Now it is an object that can be
shared. What did you gain? What did you lose?
A static object would guarantee, that each instance of Logger would get the same format. That means that even after changing format for one logger, the change would be reflected on all other loggers. Now that is not the case, since formatting is handled by another class, and that class can be changed without being reflected on other logger so long as those loggers have differente instances of the Formatter class.

- Why is the level still static? Would you change that?
Logs should be easy to control, and the level being static is like a switch located in one place. It is enough to flip it once and the change is reflected everywhere. This is to ensure that log levels are easily controlled in case we don't need to see certain information.

== 4. Your track
- How does your parsing method turn a line into an action?
Which mistakes in shop-tuesday.txt did you find, and which did
you decide are WARN instead of ERROR? Why?
To turn a string into an action, we do the following:
1) tokenize the string. We split the string into separate words which can be compared to a library of known words to determine what we want to do
2) check the first word - it decides what action to do
3) check what that action requires against what we actually got in the following tokens. Each action expects to get some information, and we pass that information via space-separated words
4) execute the command based on information obtained, or throw an error if something does not match.

There were a total of 8 mistakes in the 'shop-tuesday.txt':
```
[17:31:01.957] [ERROR] main: Error when executing command:
DISPLAY AOC-24G2 1920 1080 abc
                           ^^^ <- 'ppi' parameter of DISPLAY has wrong double format.

[17:31:01.957] [ERROR] main: Incorrect number of arguments passed into the DISPLAY command
Usage: DISPLAY <model> <w> <h> <ppi>
[17:31:01.958] [ERROR] main: Error when executing command:
ASSIGN BenQ-PD2705U Dave
                    ^^^^ <- Display manager 'Dave' does not exist.

[17:31:01.958] [ERROR] main: Error when executing command:
ASSIGN Philips-Evnia Carol
       ^^^^^^^^^^^^^ <- Display 'Philips-Evnia' does not exist.

[17:31:01.959] [ERROR] main: Error when executing command:
BUY Asus-PA278QV Carol
    ^^^^^^^^^^^^ <- Unknown display: 'Asus-PA278QV'

[17:31:01.959] [ERROR] main: Unknown command: 'SELL'
[17:31:01.959] [ERROR] main: Unknown command: 'assist'
[17:31:01.960] [ERROR] main: Incorrect number of arguments passed into the COMPARE command
Usage: COMPARE <model1> <model2>
```

For warnings there are only two examples:
```
[17:31:01.957] [WARN] main: A manager with this name (Carol) already exists.
[17:31:01.959] [WARN] Carol: 'Carol' manager has no displays assigned and cannot assist!
```

These are not errors, since they do not break the code, but were most probably unintended by the developer.

== 5. Reflection
- Where did you stare at an empty method the longest?
What finally unblocked you?
The longest stare got this method:
```java
  private static void parseCommand(String line) { ... }
```

I didn't know how to best process the line and how to execute the action. But then I got an idea to use a tokenizer. For the first semester we used one, but not for executing commands, but for counting certain words which can have different variations in a giant database of text. I implemented my own tokenizer (a more basic version of it), which let me more easily process the string, since now every argument is neatly packed into its own index inside the token array.

- If you had more time, what would you improve, and why?
I would probably improve the structure of some methods, since I don't at all like how they look, but since I don't actually know how I would improve them I left them the way they are. The biggest offender here is - in my opinion - the `parseCommand` method inside of `Main` class. I'm sure there must be a nicer solution, but I don't even know where to begin to think on how to improve it.
