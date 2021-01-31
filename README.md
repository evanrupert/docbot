# docbot

## Inspiration
Whenever working with a team on a software project there is always code being shared back and forth between developers, but to people new to the technology some parts may be unclear.  DocBot aims to help bring programming language documentation into the conversation via a Slack bot.

## What it does

DocBot will reply to any clojure code snippet posted in slack with a short summary of all the functions used as well as links to see more examples of each function used.  DocBot also provides a slack command that can be used to get the documentation for a function given the name.

## How we built it

DocBot is written entirely in clojure and interfaces directly with the slack web API without the use of any slack bot sdk.  It uses the ring library for listening to HTTP updates from slack and the clj-http library for posting messages to the slack API.

## Challenges we ran into

The biggest difficulty would be handling all the edge cases that can occur when trying to parse the code snippets.  Things like invalid code, empty snippets, and unknown functions had to be handled without crashing creating an interesting challenge.

## Accomplishments that we're proud of

I am most proud of the slack response formatting as well as the link to the online documentation since it was more difficult than I originally thought it would be.  It required handling all sorts of edge cases such as function names with special characters and random newline characters in the descriptions.

## What we learned

Throughout this project I gained a much better understanding of the clojure programming language specifically metaprogramming techniques used for traversing unknown code.  I also learned more about how the slack bot API works with event listening and posting updates.

## What's next for DocBot

In the future I would like to enable users to have more control of how DocBot behaves.  For example, an option allowing users to select whether DocBot should respond to all code snippets, or only when DocBot is mentioned.  I would also love to add the ability for DocBot to understand functions that are not part of the clojure.core library.
