

function CounterView(template, target) {
  this.template = template;
  this.target = target;
}

CounterView.prototype.notify = function(counter) {
  var rendered = Mustache.render($(this.template).html(), {
    value: counter.value(),
  });
  $(this.target).html(rendered);
  $(this.target + " button.inc").click(function() {
    counter.increase();
  })
  $(this.target + " button.dec").click(function() {
    counter.decrease();
  })
}