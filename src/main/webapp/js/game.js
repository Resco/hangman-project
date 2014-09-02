function Game() {
  this.moves = [];
  this.observers = [];
}

Game.prototype.on_move = function(move_id, move, result) {
  this.moves.push({move_id: move_id, move: move, result: result});

  this.notify_all_observers();
}

Game.prototype.add_observer = function(o) {
  this.observers.push(o);
}

Game.prototype.notify_all_observers = function() {
  for (var i=0; i < this.observers.length; i++) {
    this.observers[i].notify(this);
  };
}