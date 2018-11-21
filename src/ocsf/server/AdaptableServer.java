// This file contains material supporting section 6.13 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package ocsf.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
* The <code> AdaptableServer </code> is an adapter class
* that extends the <code> AbstractServer </code> class in place of
* the <code> AbstractObservableServer </code>.<p>
*
* Project Name: OCSF (Object Client-Server Framework)<p>
*
* @author Dr. Robert Lagani&egrave;re
* @version Febuary 2001
*/
class AdaptableServer extends AbstractServer
{
  //Instance variables **********************************************

  /**
   * The adapter used to simulate multiple class inheritance.
   */
  private ObservableServer ObservableServer;

// CONSTRUCTORS *****************************************************

  /**
   * Constructs the server adapter.
   *
   * @param  host  the server's host name.
   * @param  port  the port number.
   */
  public AdaptableServer(int port, ObservableServer server)
  {
    super(port);
    this.ObservableServer = server;
  }

// OVERRIDDEN METHODS ---------

  /**
   * Hook method called each time a new client connection is
   * accepted.
   *
   * @param client the connection connected to the client.
   */
  final protected void clientConnected(ConnectionToClient client)
  {
	  ObservableServer.clientConnected(client);
  }

  /**
   * Hook method called each time a client disconnects.
   *
   * @param client the connection with the client.
   */
  final protected void clientDisconnected(ConnectionToClient client)
  {
	  ObservableServer.clientDisconnected(client);
  }

  /**
   * Hook method called each time an exception
   * is raised in a client thread.
   *
   * @param client the client that raised the exception.
   * @param exception the exception raised.
   */
  final protected void clientException(ConnectionToClient client,
                                        Throwable exception)
  {
	  ObservableServer.clientException(client, exception);
  }

  /**
   * Hook method called when the server stops accepting
   * connections because an exception has been raised.
   *
   * @param exception the exception raised.
   */
  final protected void listeningException(Throwable exception)
  {
	  ObservableServer.listeningException(exception);
  }

  /**
   * Hook method called when the server stops accepting
   * connections.
   */
  final protected void serverStopped()
  {
	  ObservableServer.serverStopped();
  }

  /**
   * Hook method called when the server starts listening for
   * connections.
   */
  final protected void serverStarted()
  {
	  ObservableServer.serverStarted();
  }

  /**
   * Hook method called when the server is closed.
   */
  final protected void serverClosed()
  {
	  ObservableServer.serverClosed();
  }

  /**
   * Handles a command sent from the client to the server.
   *
   * @param msg   the message sent.
   * @param client the connection connected to the client that
   *  sent the message.
   */
  final protected void handleMessageFromClient(Object msg,
                                         ConnectionToClient client)
  {
	  ObservableServer.handleMessageFromClient(msg, client);
  }
}
