

function Counter() {
  this.current_value = 0;
  this.observers = [];
}

Counter.prototype.increase = function() {
  this.current_value++;
  this.notify();
}

Counter.prototype.decrease = function() {
  this.current_value--;
  this.notify();
}

Counter.prototype.notify = function() {
  for (var i=0; i<this.observers.length; i++) {
    this.observers[i].notify(this);
  }
}

Counter.prototype.value = function() {
  return this.current_value;
}

Counter.prototype.add_observer = function(observer) {
  this.observers.push(observer);
}
