
function Checkout(total) {
  this.total = total || 0;
  this.bill = [];
}

Checkout.prototype.on_price_received = function(code, price) {
  this.total += price;
  this.bill.push({code: code, price: price});
}

