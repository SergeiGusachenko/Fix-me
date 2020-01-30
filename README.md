# Fix-me
 Simulates stock exchanges and deals with trading algorithms, with networking and socket implementations.

## Mandatory part
You need to implement simulation tools for the financial markets that exchange a simplified version of FIX messages. The tools will be able to communicate over a network
using the TCP protocol.
• All messages will respect the FIX notation.<br />
• All messages will start with the ID asigned by the router and will be ended by the checksum.<br />
• You store all transactions in a database<br />
• You concieve a fail-over mechanism so that ongoing transactions are restored in
case one component goes down.<br />
