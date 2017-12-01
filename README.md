# BitcoinAlertDemo
Get bitcoin spot price for Coindesk API using Gson and send email alerts using JavaMail and SMTP

Simple tool I created to learn Maven, parsing "nested" JSON using Gson, and JavaMail/SMTP authentication.

Prompts user for a gmail address(must be gmail) and password and then checks Coinbase API for current bitcoin spot price every 5 minutes.
If spot price crosses upper or lower bound(currently set at 9500 and 9000), automatic email notification is sent to notify user of
which bound was crossed, what time it was crossed, and what the current price is.

Currently, the email is sent back to the user but it may be interesting to play with sending SMS via email. Everything is currently output
to console but I may change it to JavaFX UI for practice. 
