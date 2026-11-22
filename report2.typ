= Laboratory Report - Formatters, Sinks, and Display Shop

*Student:* Andrei Varvariuc, FAF-251 \
*Programming language:* Java \
*Track:* Display Shop

*Answer each section briefly, using your own code as examples.
Do not write textbook definitions without showing where the concept
appears in your program.*

== 1. The logger, split in three
- What is the single responsibility of Logger, Formatter, and ConsoleSink?
*ConsoleSink* takes care of writing any text into the console (as if flushing it down a sink). The *Formatter* takes care of, well, formatting how the log message looks like.
- What happens in your formatter if the message itself contains "%n"?
- Which console prints did you remove, and where do they live now?

== 2. Composition and aggregation
- Show the two Logger constructors. Which one is composition,
which one is aggregation? Explain with object lifetimes.
- Find one more example of each in your track. Include snippets.
- Track 1: the same Display was assigned to Bob and Alice, and Bob
sold it. What did Alice show? Why?
- Track 2: what would happen if two agents shared one Memory object?

== 3. Static vs shared objects
- In Lab 1 the format was static. Now it is an object that can be
shared. What did you gain? What did you lose?
- Why is the level still static? Would you change that?

== 4. Your track
- Track 1: how does your parsing method turn a line into an action?
Which mistakes in shop-tuesday.txt did you find, and which did
you decide are WARN instead of ERROR? Why?
- Track 2: how does the tool protocol work, step by step?
Include your measurements (tokens, tokens per second, memory tiers).

== 5. Reflection
- Where did you stare at an empty method the longest?
What finally unblocked you?
- If you had more time, what would you improve, and why?

**Submission requirements:** Answer all sections in your own words.
About 2 pages. Be prepared to explain your answers during the
laboratory presentation.
