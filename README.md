# Supermarket Checkout

Two things:

  * We are going to improve the organization on code on the client side. For this, we will use client-side MVC as a structuring mechanism.
  * We need to improve how we write HTML.  We need a templating mechanism.  We will use the mustache library for this.

An example of the use of Mustache is in the file src/main/webapp/demo-mustache.html

An example of client-side mvc is in the file src/main/webapp/demo-counter.html.


## Exercise 0: make it work

Make sure that the application works, as usual.

## Exercise 1: two views

We want two different views on the same model.  The first view shows

<blockquote>
  Price: 123 <br/>
  Total: 456
</blockquote>

The second view shows a list of everything that was scanned:

<blockquote>
  <ul>
    <li>A: 111</li>
    <li>B: 222</li>
  </ul>
</blockquote>

Both views are updated automatically at every successful "scan".

Note that we need to store the history of the "bill" somewhere.  We need to construct a **model** object.  Let's call it Checkout.  The Checkout object implements the *Observer* pattern.  The Checkout receives the on_scan call, then it calls the server to obtain the price.  When the server responds, the Checkout model updates its state and notifies its observers (the views).

