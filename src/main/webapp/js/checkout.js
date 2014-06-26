
function Checkout(total) {
  this.total = total || 0;
  this.bill = [];
  this.last_price = undefined;
  this.observers = [];
}

Checkout.prototype.on_price_received = function(code, price) {
  this.total += price;
  this.bill.push({code: code, price: price});
  this.last_price = price;

  this.notify_all_observers();
}

Checkout.prototype.add_observer = function(o) {
  this.observers.push(o);
}

Checkout.prototype.notify_all_observers = function() {
  for (var i=0; i < this.observers.length; i++) {
    this.observers[i].notify(this);
  };
}