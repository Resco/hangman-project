
function CheckoutDisplayView(template, target) {
  this.template = template;
  this.target = target;
}

CheckoutDisplayView.prototype.notify = function(checkout) {
  var template = $(this.template).html();
  var rendered = Mustache.render(template, {
    price: checkout.last_price,
    total: checkout.total,
  });
  $(this.target).html(rendered);
}

function CheckoutBillView(template, target) {
  this.template = template;
  this.target = target;
}

CheckoutBillView.prototype.notify = function(checkout) {
  var template = $(this.template).html();
  var rendered = Mustache.render(template, {
    items: checkout.bill,
  });
  console.log(rendered);
  $(this.target).html(rendered);
}